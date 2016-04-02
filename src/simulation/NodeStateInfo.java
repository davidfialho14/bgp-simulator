package simulation;

import network.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Aggregates all the state information of one node in one unique class
 */
class NodeStateInfo {

    private RouteTable table;
    private Map<Node, Attribute> selectedAttributes = new HashMap<>();
    private Map<Node, PathAttribute> selectedPaths = new HashMap<>();

    public NodeStateInfo(Node node, AttributeFactory attributeFactory) {
        this.table = new RouteTable(node.getOutNeighbours(), attributeFactory);
    }

    public RouteTable getTable() {
        return table;
    }

    public Map<Node, Attribute> getSelectedAttributes() {
        return selectedAttributes;
    }

    public Map<Node, PathAttribute> getSelectedPaths() {
        return selectedPaths;
    }

    /**
     * Returns the currently selected attribute for the given destination. If the destination was not yet known it
     * returns null.
     * @param destination destination to get the attribute for.
     * @return currently selected attribute or null if the destination was not known.
     */
    public Attribute getSelectedAttribute(Node destination) {
        return selectedAttributes.get(destination);
    }

    /**
     * Returns the currently selected path for the given destination. If the destination was not yet known it
     * returns null.
     * @param destination destination to get the path for.
     * @return currently selected path or null if the destination was not known.
     */
    public PathAttribute getSelectedPath(Node destination) {
        return selectedPaths.get(destination);
    }

    public Route getSelectedRoute(Node destination, Node ignoredNeighbour) {
        return table.getSelectedRoute(destination, ignoredNeighbour);
    }

    public void setSelected(Node destination, Route route) {
        selectedAttributes.put(destination, route.getAttribute());
        selectedPaths.put(destination, route.getPath());
    }

    public void updateRoute(Node destination, Node neighbour, Attribute attribute, PathAttribute path) {
        table.setAttribute(destination, neighbour, attribute);
        table.setPath(destination, neighbour, path);
    }
}
