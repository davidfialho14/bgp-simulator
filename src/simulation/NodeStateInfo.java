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

    public Attribute getSelectedAttribute(Node destination) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    public PathAttribute getSelectedPath(Node destination) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    public Route getSelectedRoute(Node destination, Node ignoredNeighbour) {
        return table.getSelectedRoute(destination, ignoredNeighbour);
    }

    public void setSelectedAttribute(Node destination, Attribute attribute) {
        selectedAttributes.put(destination, attribute);
    }

    public void setSelectedPath(Node destination, PathAttribute path) {
        selectedPaths.put(destination, path);
    }

    public void updateRoute(Node destination, Node neighbour, Attribute attribute, PathAttribute path) {
        table.setAttribute(destination, neighbour, attribute);
        table.setPath(destination, neighbour, path);
    }
}
