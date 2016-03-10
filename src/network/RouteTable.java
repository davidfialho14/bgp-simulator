package network;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteTable {

    /*
        The first map represents the neighbours. A neighbour maps to a map with the destination as the key and the
        associated route as the value. The neighbour must be the initial map keys in order to be possible to create
        the maps in the constructor.
     */
    private Map<Node, Map<Node, Route>> routes;
    private AttributeFactory attributeFactory;  // used to create invalid routes

    public RouteTable(Collection<Node> outNeighbours, AttributeFactory attributeFactory) {
        routes = new HashMap<>(outNeighbours.size());

        // create maps for each neighbour
        for (Node outNeighbour : outNeighbours) {
            routes.put(outNeighbour, new HashMap<>());
        }

        this.attributeFactory = attributeFactory;
    }

    public void setAttribute(Node destination, Node neighbour, Attribute attribute) {
        try {
            routes.get(neighbour).get(destination).setAttribute(attribute);
        } catch (NullPointerException e1) {
            // destination if not in the route table yet
            try {
                routes.get(neighbour).put(destination,
                        new Route(destination, attribute, new PathAttribute(destination)));
            } catch (NullPointerException e2) {
                // the neighbour does not exist in the table
                // ignore set
            }
        }
    }

    public void setPath(Node destination, Node neighbour, PathAttribute path) {
        // TODO - implement RouteTable.setPath
        throw new UnsupportedOperationException();
    }

    public Attribute getAttribute(Node destination, Node neighbour) {
        try {
            return routes.get(neighbour).get(destination).getAttribute();
        } catch (NullPointerException e) {
            // neighbour or destination do not exist
            return null;
        }
    }

    public PathAttribute getPath(Node destination, Node neighbour) {
        // TODO - implement RouteTable.getPath
        throw new UnsupportedOperationException();
    }

    public void clear() {
        // TODO - implement RouteTable.clear
        throw new UnsupportedOperationException();
    }

    public Route getSelectedRoute(Node destination, Node ignoredNeighbour) {
        // TODO - implement RouteTable.getSelectedRoute
        throw new UnsupportedOperationException();
    }

}
