package core;

import core.topology.Link;
import core.topology.Node;
import dnl.utils.text.table.TextTable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static core.Route.invalidRoute;

/**
 * Stores the routes learned from each out-link to reach one destination.
 */
public class RouteTable {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Node destination;
    private final Map<Node, Route> routes;

    private Route selectedRoute;
    private Node selectedNeighbour;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a new empty route table for one destination and with no out-links.
     *
     * @param destination destination of the routes to be stored.
     */
    public RouteTable(Node destination) {
        this.destination = destination;
        this.routes = new HashMap<>();
        this.selectedRoute = invalidRoute(destination);
        this.selectedNeighbour = null;
    }

    /**
     * Constructs a new empty route table for one destination. The table is pre-initialized with the collection
     * of out-links given.
     *
     * @param destination destination of the routes to be stored.
     * @param outLinks out out-links of the route table.
     */
    public RouteTable(Node destination, Collection<Link> outLinks) {
        this.destination = destination;
        this.routes = new HashMap<>(outLinks.size());
        this.selectedRoute = invalidRoute(destination);
        this.selectedNeighbour = null;

        outLinks.forEach(this::addOutLink);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Removes the given out-link from the table.
     *
     * @param outLink out-link to be removed.
     */
    public void removeOutLink(Link outLink) {
        routes.remove(outLink.getDestination());
    }

    /**
     * Adds a new out-link to the route table.
     *
     * @param outLink out-link to add to the table.
     */
    public void addOutLink(Link outLink) {
        routes.putIfAbsent(outLink.getDestination(), invalidRoute(destination));
    }

    /**
     * Returns the destination node associated with the route table.
     *
     * @return the destination node associated with the route table.
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Sets the route for an out-link. If the out-link does not exist in the table, it will be added and assigned to
     * the new route. Calling this method may update the selected route.
     *
     * @param outLink out-link to associate route with.
     * @param route route to be set.
     */
    public void setRoute(Link outLink, Route route) {
        routes.put(outLink.getDestination(), route);

        if (outLink.getDestination().equals(selectedNeighbour)) {
            // the selected route is no longer valid

            // re-select the best route
            selectedRoute = null;
            for (Node neighbour : routes.keySet()) {
                Route neighbourRoute = getRoute(neighbour);

                if ((selectedRoute == null || selectedRoute.compareTo(neighbourRoute) > 0)) {
                    selectedRoute = neighbourRoute;
                    selectedNeighbour = neighbour;
                }
            }

        } else if (route.compareTo(selectedRoute) < 0) {
            selectedRoute = route;
            selectedNeighbour = outLink.getDestination();
        }
    }

    /**
     * Returns the route associated with the given destination and out-link pair. If the out-link does not exist in
     * the table it will be returned null.
     *
     * @param outLink out-link to get route.
     * @return route associated with the given pair or null if the out-link does not exist.
     */
    public Route getRoute(Link outLink) {
        Route route = routes.get(outLink.getDestination());

        if (route == null) {
            return invalidRoute(destination);
        } else {
            return route;
        }

    }

    public Route getRoute(Node neighbour) {
        Route route = routes.get(neighbour);

        if (route == null) {
            return invalidRoute(destination);
        } else {
            return route;
        }

    }

    /**
     * Clears all the routes and destinations from the table. It keeps the out-links.
     */
    public void clear() {
        routes.replaceAll((neighbour, route) -> invalidRoute(destination));
    }

    /**
     * Returns the currently selected route. If ignoredOutLink is not null it will select the best route associated
     * with any out-link exception the ignoredOutLink.
     *
     * @param ignoredOutLink out-link to be ignored.
     * @return currently selected route for the destination.
     */
    public Route getAlternativeRoute(Link ignoredOutLink) {

        if (ignoredOutLink == null || !ignoredOutLink.getDestination().equals(selectedNeighbour)) {
            return selectedRoute;
        } else {
            return getBestRoute(ignoredOutLink.getDestination());
        }
    }

    /**
     * Returns the currently selected route.
     *
     * @return the currently selected route.
     */
    public Route getSelectedRoute() {
        return selectedRoute;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RouteTable)) return false;

        RouteTable that = (RouteTable) o;

        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        return routes != null ? routes.equals(that.routes) : that.routes == null;

    }

    @Override
    public int hashCode() {
        int result = destination != null ? destination.hashCode() : 0;
        result = 31 * result + (routes != null ? routes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        getPrintableTable().printTable(new PrintStream(os), 0);

        return os.toString();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


    private TextTable getPrintableTable() {
        String[] columns = routes.keySet().stream()
                .map(Node::toString)
                .toArray(String[]::new);

        Object[] routes = this.routes.values().toArray();
        Object[][] table = new Route[1][routes.length];
        System.arraycopy(routes, 0, table[0], 0, routes.length);

        return new TextTable(columns, table);
    }

    /**
     * Returns the currently best route. If ignoredOutLink is not null it will return the best route associated
     * with any out-link exception the ignoredOutLink.
     *
     * @param ignoredNeighbour out-link to be ignored.
     * @return currently best route for the destination.
     */
    private Route getBestRoute(Node ignoredNeighbour) {
        Route bestRoute = null;

        for (Node neighbour : routes.keySet()) {
            Route route = getRoute(neighbour);

            if (!neighbour.equals(ignoredNeighbour) && (bestRoute == null || bestRoute.compareTo(route) > 0)) {
                bestRoute = route;
            }
        }

        return bestRoute;
    }

}
