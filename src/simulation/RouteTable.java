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
    private Node node;


    /**
     * Constructs a new empty route table. Defines the outLinks included in the route table.
     *
     * @param outLinks out out-links of the route table.
     */
    public RouteTable(Collection<Link> outLinks) {
        this.routes = new HashMap<>(outLinks.size());

        // create empty maps for each out-link
        outLinks.forEach(this::addOutLink);
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
        Route preferredRoute = null;

        for (Link outLink : routes.keySet()) {
            Route route = getRoute(destination, outLink);

            if (!outLink.equals(ignoredOutLink) && (preferredRoute == null || preferredRoute.compareTo(route) > 0)) {
                preferredRoute = route;
            }
        }

        return preferredRoute;
    }

    Route getSelectedRoute(Node destination) {
        return getSelectedRoute(destination, null);
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
     * Adds a new out-lin to the route table.
     *
     * @param outLink out-link to add to the table.
     */
    public void addOutLink(Link outLink) {
        routes.putIfAbsent(outLink, new HashMap<>());
    }

    /**
     * Returns a collection with all the destinations learned from the given out-link.
     *
     * @param outLink out-link to get destinations from.
     * @return collection with all the destinations learned from the given out-link.
     */
    public Collection<Node> getDestinationsLearnFrom(Link outLink) {
        return routes.get(outLink).keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteTable that = (RouteTable) o;

        if (routes == null && that.routes == null) return true;
        else if (routes == null || that.routes == null) return false;

        if (!routes.keySet().equals(that.routes.keySet())) return false;

        // consider all that destinations from both tables
        Set<Node> destinations = getDestinations();
        destinations.addAll(that.getDestinations());

        for (Link outLink : routes.keySet()) {
            for (Node destination : destinations) {
                if (!this.getRoute(destination, outLink).equals(that.getRoute(destination, outLink))) {
                    return false;
                }
            }
        }

        return true;

    }

    /*
        The hashCode() method does not respect the equals() method. But the equals method is only used for testing
        and the hashCode() is never used. FIX this if the hashCode() becomes relevant
     */
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

        Node[] destinations = getDestinations().stream().toArray(Node[]::new);

        Route[][] table = new Route[destinations.length][columns.length];
        for (int i = 0; i < destinations.length; i++) {
            for (int j = 0; j < columns.length; j++) {
                table[i][j] = getRoute(destinations[i], outLinks[j]);
            }
        }

        return new TextTable(columns, table);
    }

    private Set<Node> getDestinations() {
        Set<Node> destinationsSet = new HashSet<>();
        for (Map<Node, Route> nodeRouteMap : routes.values()) {
            destinationsSet.addAll(nodeRouteMap.keySet());
        }

        return destinationsSet;
    }
}
