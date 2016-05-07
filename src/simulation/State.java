package simulation;

import network.Network;
import network.Node;
import protocols.Protocol;

import java.util.Map;

/**
 * Responsible to hold all the current state of the simulation. This includes the current currentNetwork state, the current
 * state of the routing tables, and the protocols being used by the nodes.
 */
public class State {

    private Network originalNetwork;
    private Network currentNetwork;
    private Protocol defaultProtocol;
    private Map<Node, NodeState> nodesStates;   // state of each node

    private State() {}  // use the static create methods

    /**
     * Creates an empty state for the given network. It initializes all nodes with the given protocol.
     *
     * @param network currentNetwork to create state for.
     * @param protocol protocol to be used by all nodes by default.
     */
    public static State create(Network network, Protocol protocol) {
        return null;
    }

    /**
     * Resets all the state. This clears all the routing tables, reverts all nodes to the default protocol and
     * returns the network to its original state.
     */
    public void reset() {

    }

    /**
     * Returns the current network state.
     *
     * @return current network state.
     */
    public Network getNetwork() {
        return currentNetwork;
    }

    /**
     * Returns the state of a node.
     *
     * @param node node to get state for.
     * @return state of the node.
     */
    public NodeState get(Node node) {
        return null;
    }

    /**
     * Returns the current route tables for each node.
     *
     * @return a map associating each node with its current route table.
     */
    public Map<Node, RouteTable> getRouteTables() {
        return null;
    }

    /**
     * Returns the current selected routes for each node to reach the destination.
     *
     * @return a map associating each node with its current selected route to reach the destination.
     */
    public Map<Node, Route> getSelectedRoutes(Node destination) {
        return null;
    }

    /**
     * Adds a new link the current network. This alters the networks state.
     */
    public void addLink() {

    }

    /**
     * Removes a link the current network. This alters the networks state.
     */
    public void removeLink() {

    }

    /**
     * Replaces the current protocol being used by the node with the given protocol.
     *
     * @param node node to update protocol.
     * @param protocol protocol to replace current one.
     */
    public void updateProtocol(Node node, Protocol protocol) {

    }
}
