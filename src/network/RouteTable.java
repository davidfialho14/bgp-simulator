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
    private Collection<Node> outNeighbours;
    private AttributeFactory attributeFactory;  // used to create invalid routes

    public RouteTable(Collection<Node> outNeighbours, AttributeFactory attributeFactory) {
        this.outNeighbours = outNeighbours;
        routes = new HashMap<>(outNeighbours.size());

        // create maps for each neighbour
        for (Node outNeighbour : outNeighbours) {
            routes.put(outNeighbour, new HashMap<>());
        }

        this.attributeFactory = attributeFactory;
    }

    public void setAttribute(Node destination, Node neighbour, Attribute attribute) {

        Map<Node, Route> row = routes.get(neighbour);
        if (row != null) {
            // neighbour exists
            try {
                row.get(destination).setAttribute(attribute);
            } catch (NullPointerException e1) {
                // destination does not added yet
                // add an invalid route for each neighbour
                for (Map.Entry<Node, Map<Node, Route>> entry : routes.entrySet()) {
                    if (entry.getKey().equals(neighbour)) {
                        entry.getValue().put(destination,
                                new Route(destination, attribute, PathAttribute.createInvalid()));
                    } else {
                        entry.getValue().put(destination,
                                new Route(destination,
                                        attributeFactory.createInvalid(), PathAttribute.createInvalid()));
                    }
                }
            }
        }
    }

    public void setPath(Node destination, Node neighbour, PathAttribute path) {
        try {
            routes.get(neighbour).get(destination).setPath(path);
        } catch (NullPointerException e1) {
            // destination if not in the route table yet
            try {
                routes.get(neighbour).put(destination,
                        new Route(destination, attributeFactory.createInvalid(), path));
            } catch (NullPointerException e2) {
                // the neighbour does not exist in the table
                // ignore set
            }
        }
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
        try {
            return routes.get(neighbour).get(destination).getPath();
        } catch (NullPointerException e) {
            // neighbour or destination do not exist
            return null;
        }
    }

    public void clear() {
        routes.clear();
    }

    public Route getSelectedRoute(Node destination, Node ignoredNeighbour) {
        // TODO - implement RouteTable.getSelectedRoute
        throw new UnsupportedOperationException();
    }

}
