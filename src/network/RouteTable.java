package network;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RouteTable {

    /*
        The first map represents the neighbours. A neighbour maps to a map with the destination as the key and the
        associated route as the value. The neighbour must be the initial map keys in order to be possible to create
        the maps in the constructor.
     */
    private Map<Node, Map<Node, Route>> routes;
    private Collection<Node> outNeighbours;
    private AttributeFactory attributeFactory;  // used to create invalid routes

    /**
     * Constructs a new empty route table. Defines the neighbours included in the route table.
     * @param outNeighbours out neighbours of the route table.
     * @param attributeFactory factory used to generate invalid attributes.
     */
    public RouteTable(Collection<Node> outNeighbours, AttributeFactory attributeFactory) {
        this.outNeighbours = outNeighbours;
        routes = new HashMap<>(outNeighbours.size());

        // create maps for each neighbour
        for (Node outNeighbour : outNeighbours) {
            routes.put(outNeighbour, new HashMap<>());
        }

        this.attributeFactory = attributeFactory;
    }

    /**
     * Sets a the given attribute to the given destination and neighbour pair. If the neighbour does not exists in the
     * table calling this method will not change anything.
     * @param destination destination node to be assigned the attribute.
     * @param neighbour neighbour node to be assigned the attribute.
     * @param attribute attribute to be set.
     */
    public void setAttribute(Node destination, Node neighbour, Attribute attribute) {

        try {
            set(destination, neighbour, (route, p) -> route.setAttribute(attribute), attribute);
        } catch (NullPointerException e1) {
            // destination was not added yet
            addDestination(destination);
            routes.get(neighbour).get(destination).setAttribute(attribute);
        }
    }

    /**
     * Sets a the given path to the given destination and neighbour pair. If the neighbour does not exists in the
     * table calling this method will not change anything.
     * @param destination destination node to be assigned the path.
     * @param neighbour neighbour node to be assigned the path.
     * @param path attribute to be set.
     */
    public void setPath(Node destination, Node neighbour, PathAttribute path) {
        try {
            set(destination, neighbour, (route, p) -> route.setPath(path), path);
        } catch (NullPointerException e1) {
            // destination was not added yet
            addDestination(destination);
            routes.get(neighbour).get(destination).setPath(path);
        }
    }

    /**
     * @throws NullPointerException if the destination node does not exist.
     */
    private void set(Node destination, Node neighbour,
                     BiConsumer<Route, Attribute> setter, Attribute attributeToset) {
        Map<Node, Route> row = routes.get(neighbour);
        if (row != null) {
            // neighbour exists
            setter.accept(row.get(destination), attributeToset); // set the attribute
        }
    }

    /**
     * Adds a new destination to the table by assigning invalid routes for all neighbours for that destination.
     * @param destination destination to be added.
     */
    private void addDestination(Node destination) {
        // add an invalid route for each neighbour
        for (Map.Entry<Node, Map<Node, Route>> entry : routes.entrySet()) {
            entry.getValue().put(destination,
                    new Route(destination, attributeFactory.createInvalid(), PathAttribute.createInvalid()));
        }
    }

    /**
     * Returns the attribute associated with the given destination and neighbour pair. If the destination or the
     * neighbour do not exist in the table it will be returned null.
     * @param destination destination to get attribute.
     * @param neighbour neighbour to get attribute.
     * @return attribute associated with the given pair or null if one of them does not exist.
     */
    public Attribute getAttribute(Node destination, Node neighbour) {
        return get(destination, neighbour, (Route::getAttribute));
    }

    /**
     * Returns the path associated with the given destination and neighbour pair. If the destination or the
     * neighbour do not exist in the table it will be returned null.
     * @param destination destination to get path.
     * @param neighbour neighbour to get path.
     * @return path associated with the given pair or null if one of them does not exist.
     */
    public PathAttribute getPath(Node destination, Node neighbour) {
        return (PathAttribute) get(destination, neighbour, (Route::getPath));
    }

    private Attribute get(Node destination, Node neighbour, Function<Route, Attribute> getter) {
        try {
            return getter.apply(routes.get(neighbour).get(destination));
        } catch (NullPointerException e) {
            // neighbour or destination do not exist
            return null;
        }
    }

    /**
     * Clears all the routes and destinations from the table. It keeps the neighbours.
     */
    public void clear() {
        routes.values().forEach(Map<Node, Route>::clear);
    }

    public Route getSelectedRoute(Node destination, Node ignoredNeighbour) {
        // TODO - implement RouteTable.getSelectedRoute
        throw new UnsupportedOperationException();
    }

}
