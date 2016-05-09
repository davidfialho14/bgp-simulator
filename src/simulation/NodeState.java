package simulation;

import network.Link;
import network.Node;
import policies.Attribute;
import policies.PathAttribute;
import protocols.Protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * Aggregates all the state of one node in one unique class
 */
public class NodeState {

    private RouteTable table;
    private Map<Node, Route> selectedRoutes = new HashMap<>();
    private Protocol protocol;

    /**
     * Initializes the state for the given node with an empty table and associates it with the given protocol.
     *
     * @param node node to create state for.
     * @param protocol protocol to be used by the node.
     */
    public NodeState(Node node, Protocol protocol) {
        this.table = new RouteTable(node.getOutLinks());
        this.protocol = protocol;
    }

    /**
     * Returns the current state of the route table.
     *
     * @return current state of the route table.
     */
    public RouteTable getTable() {
        return table;
    }

    /**
     * Returns the current protocol being used by the node.
     *
     * @return current protocol being used by the node.
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * Changes the protocol being used by the node to the given protocol.
     *
     * @param protocol protocol to be set.
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    /**
     * Returns a map between the destinations and the respective selected routes
     * @return a map between the destinations and the respective selected routes.
     */
    public Map<Node, Route> getSelectedRoutes() {
        return selectedRoutes;
    }

    /**
     * Returns the currently selected attribute for the given destination. If the destination was not yet known it
     * returns null.
     * @param destination destination to get the attribute for.
     * @return currently selected attribute or null if the destination was not known.
     */
    public Attribute getSelectedAttribute(Node destination) {
        Route selectedRoute = selectedRoutes.get(destination);
        return selectedRoute != null ? selectedRoute.getAttribute() : null;
    }

    /**
     * Returns the currently selected path for the given destination. If the destination was not yet known it
     * returns null.
     * @param destination destination to get the path for.
     * @return currently selected path or null if the destination was not known.
     */
    public PathAttribute getSelectedPath(Node destination) {
        Route selectedRoute = selectedRoutes.get(destination);
        return selectedRoute != null ? selectedRoute.getPath() : null;
    }

    /**
     * Returns the currently selected route for the given destination. If the ignored link is specified it selects
     * the preferred route excluding the route learned from that out-link.
     *
     * @param destination destination to get selected route for.
     * @param ignoredLink out-link to be ignored.
     * @return currently selected route.
     */
    public Route getSelectedRoute(Node destination, Link ignoredLink) {
        return table.getSelectedRoute(destination, ignoredLink);
    }

    /**
     * Returns the currently selected route for the given destination.
     *
     * @param destination destination to get selected route for.
     * @return currently selected route.
     */
    public Route getSelectedRoute(Node destination) {
        return table.getSelectedRoute(destination);
    }

    /**
     * Sets the given route as the currently selected route for the destination.
     *
     * @param destination destination to set route for.
     * @param route route to set
     * @return currently selected route.
     */
    public void setSelected(Node destination, Route route) {
        selectedRoutes.put(destination, route);
    }

    /**
     * Updates the attribute and/or path of the route for the given destination and out-link.
     *
     * @param destination destination to update.
     * @param outLink out-link to update.
     * @param attribute attribute to update route to.
     * @param path path to update to.
     */
    public void updateRoute(Node destination, Link outLink, Attribute attribute, PathAttribute path) {
        table.setRoute(outLink, new Route(destination, attribute, path));
    }

}
