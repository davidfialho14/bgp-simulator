package simulation;

import network.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Aggregates all the state information of one node in one unique class
 */
class NodeStateInfo {

    private RouteTable table;
    private Map<Node, Attribute> selectedAttributes;
    private Map<Node, PathAttribute> selectedPaths;

    public NodeStateInfo(Node node, AttributeFactory attributeFactory) {
        this.table = new RouteTable(node.getOutNeighbours(), attributeFactory);
        this.selectedAttributes = new HashMap<>();
        this.selectedPaths = new HashMap<>();
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
}
