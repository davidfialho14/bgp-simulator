package core;

import dnl.utils.text.table.TextTable;
import core.topology.Link;
import core.topology.Node;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Stores the routes learned from each out-link to reach one destination.
 */
public class RouteTable {

    private Node destination;
    private Map<Link, Route> routes;

    /**
     * Constructs a new empty route table for one destination and with no out-links.
     *
     * @param destination destination of the routes to be stored.
     */
    public RouteTable(Node destination) {
        this.destination = destination;
        this.routes = new HashMap<>();
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

        outLinks.forEach(this::addOutLink);
    }

    /**
     * Removes the given out-link from the table.
     *
     * @param outLink out-link to be removed.
     */
    public void removeOutLink(Link outLink) {
        routes.remove(outLink);
    }

    /**
     * Adds a new out-link to the route table.
     *
     * @param outLink out-link to add to the table.
     */
    public void addOutLink(Link outLink) {
        routes.putIfAbsent(outLink, Route.invalidRoute(destination));
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
     * Associates a route with the given out-link. If the out-link does not exist this method will have no effect
     * on the table. If the destination is unknown it will be added to the table.
     *
     * @param outLink out-link to associate route with.
     * @param route route to be set.
     */
    public void setRoute(Link outLink, Route route) {
        routes.put(outLink, new Route(route));  // store a copy of the route
    }

    /**
     * Returns the route associated with the given destination and out-link pair. If the out-link does not exist in
     * the table it will be returned null.
     *
     * @param outLink out-link to get route.
     * @return route associated with the given pair or null if the out-link does not exist.
     */
    public Route getRoute(Link outLink) {
        return routes.get(outLink);
    }

    /**
     * Clears all the routes and destinations from the table. It keeps the out-links.
     */
    public void clear() {
        routes.replaceAll((link, route) -> Route.invalidRoute(destination));
    }

    /**
     * Returns the currently selected route. If ignoredOutLink is not null it will select the best route associated
     * with any out-link exception the ignoredOutLink.
     *
     * @param ignoredOutLink out-link to be ignored.
     * @return currently selected route for the destination.
     */
    public Route getSelectedRoute(Link ignoredOutLink) {
        Route preferredRoute = null;

        for (Link outLink : routes.keySet()) {
            Route route = getRoute(outLink);

            if (!outLink.equals(ignoredOutLink) && (preferredRoute == null || preferredRoute.compareTo(route) > 0)) {
                preferredRoute = route;
            }
        }

        return preferredRoute;
    }

    Route getSelectedRoute() {
        return getSelectedRoute(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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

    private TextTable getPrintableTable() {
        String[] columns = routes.keySet().stream()
                .map(Link::toString)
                .toArray(String[]::new);

        Object[] routes = this.routes.values().toArray();
        Object[][] table = new Route[1][routes.length];
        System.arraycopy(routes, 0, table[0], 0, routes.length);

        return new TextTable(columns, table);
    }
}
