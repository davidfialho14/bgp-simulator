package simulation;

import dnl.utils.text.table.TextTable;
import network.Link;
import network.Node;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class RouteTable {

    /*
        The first map represents the out-links. A out-link maps to a map with the destination as the key and the
        associated route as the value. The out-link must be the initial map keys in order to be possible to create
        the maps in the constructor.
     */
    private Map<Link, Map<Node, Route>> routes;


    /**
     * Constructs a new empty route table. Defines the outLinks included in the route table.
     *
     * @param outLinks out out-links of the route table.
     */
    public RouteTable(Collection<Link> outLinks) {
        this.routes = new HashMap<>(outLinks.size());

        // create empty maps for each out-link
        outLinks.forEach(link -> routes.put(link, new HashMap<>()));
    }

    /**
     * Associates a route with the given out-link. If the out-link does not exist this method will have no effect
     * on the table. If the destination is unknown it will be added to the table.
     *
     * @param outLink out-link to associate route with.
     * @param route route to be set.
     */
    public void setRoute(Link outLink, Route route) {
        Map<Node, Route> destinationToRoute = routes.get(outLink);
        if (destinationToRoute != null) {
            // out link does exist
            destinationToRoute.put(route.getDestination(), new Route(route));
        }
    }

    /**
     * Returns the route associated with the given destination and out-link pair. If the out-link does not exist in
     * the table it will be returned null.
     *
     * @param destination destination to get route.
     * @param outLink out-link to get route.
     * @return route associated with the given pair or null if the out-link does not exist.
     */
    public Route getRoute(Node destination, Link outLink) {
        Route route = null;

        Map<Node, Route> destinationToRoute = routes.get(outLink);
        if (destinationToRoute != null) {
            // out-link does exist
            route = destinationToRoute.getOrDefault(destination, Route.invalidRoute(destination));
        }

        return route;
    }

    /**
     * Clears all the routes and destinations from the table. It keeps the out-links.
     */
    public void clear() {
        routes.forEach((link, destinationToRoute) -> destinationToRoute.clear());
    }

    /**
     * Returns the currently selected route for the given destination. If ignoredOutLink is not null it will select the
     * best route associated with any out-link exception the ignoredOutLink.
     *
     * @param destination destination node to get selected route for.
     * @param ignoredOutLink out-link to be ignored.
     * @return currently selected route for the destination.
     */
    public Route getSelectedRoute(Node destination, Link ignoredOutLink) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the given out-link from the table.
     *
     * @param outLink out-link to be removed.
     */
    public void removeOutLink(Link outLink) {
        routes.remove(outLink);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteTable that = (RouteTable) o;

        return routes != null ? routes.equals(that.routes) : that.routes == null;

    }

    @Override
    public int hashCode() {
        return routes != null ? routes.hashCode() : 0;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        getPrintableTable().printTable(new PrintStream(os), 0);

        return os.toString();
    }

    private TextTable getPrintableTable() {
        Link[] outLinks = routes.keySet().stream().toArray(Link[]::new);
        String[] columns = routes.keySet().stream()
                .map(Link::toString)
                .toArray(String[]::new);

        Set<Node> destinationsSet = new HashSet<>();
        for (Map<Node, Route> nodeRouteMap : routes.values()) {
            destinationsSet.addAll(nodeRouteMap.keySet());
        }

        Node[] destinations = destinationsSet.stream().toArray(Node[]::new);

        Route[][] table = new Route[destinations.length][columns.length];
        for (int i = 0; i < destinations.length; i++) {
            for (int j = 0; j < columns.length; j++) {
                table[i][j] = getRoute(destinations[i], outLinks[j]);
            }
        }

        return new TextTable(columns, table);
    }
}
