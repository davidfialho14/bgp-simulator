package core;

import core.topology.Link;
import core.topology.Node;

import static core.Route.invalidRoute;

/**
 * Aggregates all the state of one node in one unique class
 */
public class NodeState {

    private RouteTable table;
    private Route selectedRoute;
    private Protocol protocol;

    /**
     * Initializes the state for the given node with an empty table and associates it with the given protocol.
     *
     * @param node node to create state for.
     * @param destination destination simulating for.
     * @param protocol protocol to be used by the node.
     */
    public NodeState(Node node, Node destination, Protocol protocol) {
        this.table = new RouteTable(destination, node.getOutLinks());
        this.selectedRoute = invalidRoute(destination);
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
     * Returns the current selected route.
     *
     * @return current selected route.
     */
    public Route getSelectedRoute() {
        return selectedRoute;
    }

    /**
     * Sets the given route as the currently selected route for the destination.
     *
     * @param route route to set as selected.
     */
    public void setSelectedRoute(Route route) {
        selectedRoute = route;
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
     * Returns the currently selected route for the given destination. If the ignored link is specified it selects
     * the preferred route excluding the route learned from that out-link.
     *
     * @param ignoredLink out-link to be ignored.
     * @return currently selected route.
     */
    public Route getSelectedRoute(Link ignoredLink) {
        return table.getSelectedRoute(ignoredLink);
    }

    /**
     * Updates the attribute and/or path of the route for the given destination and out-link.
     *
     * @param outLink out-link to update.
     * @param attribute attribute to update route to.
     * @param path path to update to.
     */
    public void updateRoute(Link outLink, Attribute attribute, Path path) {
        table.setRoute(outLink, new Route(table.getDestination(), attribute, path));
    }

}
