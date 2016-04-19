package simulation;

import network.Link;
import network.Node;
import policies.Attribute;
import policies.Policy;

import java.util.HashMap;
import java.util.Map;

/**
 * Aggregates all the state information of one node in one unique class
 */
public class NodeStateInfo {

    private RouteTable table;
    private Map<Node, Attribute> selectedAttributes = new HashMap<>();
    private Map<Node, PathAttribute> selectedPaths = new HashMap<>();

    public NodeStateInfo(Node node, Policy policy) {
        this.table = new RouteTable(node.getOutLinks());
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

    public Route getSelectedRoute(Node destination, Link ignoredLink) {
        return table.getSelectedRoute(destination, ignoredLink);
    }

    public void setSelected(Node destination, Route route) {
        selectedAttributes.put(destination, route.getAttribute());
        selectedPaths.put(destination, route.getPath());
    }

    public void updateRoute(Node destination, Link outLink, Attribute attribute, PathAttribute path) {
        table.setAttribute(destination, outLink, attribute);
        table.setPath(destination, outLink, path);
    }

    public void updateRoute(Link outLink, Route route) {
        table.setAttribute(route.getDestination(), outLink, route.getAttribute());
        table.setPath(route.getDestination(), outLink, route.getPath());
    }
}
