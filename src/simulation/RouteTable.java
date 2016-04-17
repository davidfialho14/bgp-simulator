package simulation;

import dnl.utils.text.table.TextTable;
import network.Link;
import network.Node;
import policies.Attribute;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RouteTable {

    /*
        The first map represents the out-links. A out-link maps to a map with the destination as the key and the
        associated route as the value. The out-link must be the initial map keys in order to be possible to create
        the maps in the constructor.
     */
    private Map<Link, Map<Node, Route>> routes;
    private Collection<Link> outLinks;
    private List<Node> destinations = new ArrayList<>();

    /**
     * Constructs a new empty route table. Defines the outLinks included in the route table.
     * @param outLinks out out-links of the route table.
     */
    public RouteTable(Collection<Link> outLinks) {
        this.outLinks = outLinks;
        this.routes = new HashMap<>(outLinks.size());

        // create empty maps for each out-link
        outLinks.forEach(link -> routes.put(link, new HashMap<>()));
    }

    /**
     * Sets a the given attribute to the given destination and out-link pair. If the out-link does not exists in the
     * table calling this method will not change anything.
     * @param destination destination node to be assigned the attribute.
     * @param outLink out-link node to be assigned the attribute.
     * @param attribute attribute to be set.
     */
    public void setAttribute(Node destination, Link outLink, Attribute attribute) {

        try {
            set(destination, outLink, (route, p) -> route.setAttribute(attribute), attribute);
        } catch (NullPointerException e1) {
            // destination was not added yet
            addDestination(destination);
            routes.get(outLink).get(destination).setAttribute(attribute);
        }
    }

    /**
     * Sets a the given path to the given destination and out-link pair. If the out-link does not exists in the
     * table calling this method will not change anything.
     * @param destination destination node to be assigned the path.
     * @param outLink out-link node to be assigned the path.
     * @param path attribute to be set.
     */
    public void setPath(Node destination, Link outLink, PathAttribute path) {
        try {
            set(destination, outLink, (route, p) -> route.setPath(path), path);
        } catch (NullPointerException e1) {
            // destination was not added yet
            addDestination(destination);
            routes.get(outLink).get(destination).setPath(path);
        }
    }

    /**
     * @throws NullPointerException if the destination node does not exist.
     */
    private void set(Node destination, Link outLink,
                     BiConsumer<Route, Attribute> setter, Attribute attributeToset) {
        Map<Node, Route> row = routes.get(outLink);
        if (row != null) {
            // out-link exists
            setter.accept(row.get(destination), attributeToset); // set the attribute
        }
    }

    /**
     * Adds a new destination to the table by assigning invalid routes for all out-links for that destination.
     * @param destination destination to be added.
     */
    private void addDestination(Node destination) {
        // add an invalid route for each out-link
        for (Map.Entry<Link, Map<Node, Route>> entry : routes.entrySet()) {
            entry.getValue().put(destination, Route.createInvalid(destination));
        }

        destinations.add(destination);
    }

    /**
     * Returns the attribute associated with the given destination and out-link pair. If the destination or the
     * out-link do not exist in the table it will be returned null.
     * @param destination destination to get attribute.
     * @param outLink out-link to get attribute.
     * @return attribute associated with the given pair or null if one of them does not exist.
     */
    public Attribute getAttribute(Node destination, Link outLink) {
        return get(destination, outLink, (Route::getAttribute));
    }

    /**
     * Returns the path associated with the given destination and out-link pair. If the destination or the
     * out-link do not exist in the table it will be returned null.
     * @param destination destination to get path.
     * @param outLink out-link to get path.
     * @return path associated with the given pair or null if one of them does not exist.
     */
    public PathAttribute getPath(Node destination, Link outLink) {
        return (PathAttribute) get(destination, outLink, (Route::getPath));
    }

    private Attribute get(Node destination, Link outLink, Function<Route, Attribute> getter) {
        try {
            return getter.apply(routes.get(outLink).get(destination));
        } catch (NullPointerException e) {
            // out-link or destination do not exist
            return null;
        }
    }

    /**
     * Clears all the routes and destinations from the table. It keeps the out-links.
     */
    public void clear() {
        routes.values().forEach(Map::clear);
    }

    public Route getSelectedRoute(Node destination, Link ignoredOutLink) {
        Route preferredRoute = null;
        for (Map.Entry<Link, Map<Node, Route>> entry : routes.entrySet()) {
            Link outLink = entry.getKey();
            Route route = entry.getValue().get(destination);

            if (!outLink.equals(ignoredOutLink) &&
                    (preferredRoute == null || preferredRoute.compareTo(route) > 0)) {
                preferredRoute = route;
            }
        }

        return preferredRoute;
    }

    public TextTable getPrintableTable() {
        Object[] outLinksArray = outLinks.toArray();
        String[] columns = new String[outLinks.size()];
        for (int i = 0; i < outLinks.size(); i++) {
            columns[i] = outLinksArray[i].toString();
        }

        Route[][] table = new Route[destinations.size()][outLinks.size()];
        for (int i = 0; i < destinations.size(); i++) {
            for (int j = 0; j < outLinks.size(); j++) {
                table[i][j] = routes.get(outLinksArray[j]).get(destinations.get(i));
            }
        }

        return new TextTable(columns, table);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteTable that = (RouteTable) o;

        return routes != null ? routes.equals(that.routes) : that.routes == null &&
                (outLinks != null ? outLinks.equals(that.outLinks) : that.outLinks == null);

    }

    @Override
    public int hashCode() {
        int result = routes != null ? routes.hashCode() : 0;
        result = 31 * result + (outLinks != null ? outLinks.hashCode() : 0);
        return result;
    }
}
