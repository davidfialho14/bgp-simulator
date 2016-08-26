package core;

import core.topology.ConnectedNode;

/**
 * Aggregates all the state of one node in one unique class
 */
public class NodeState {

    private RouteTable table;
    private Protocol protocol;

    /**
     * Initializes the state for the given node with an empty table and associates it with the given protocol.
     *
     * @param node node to create state for.
     * @param destination destination simulating for.
     * @param protocol protocol to be used by the node.
     */
    public NodeState(ConnectedNode node, ConnectedNode destination, Protocol protocol) {
        this.table = new RouteTable(destination, node.getOutLinks());
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

}
