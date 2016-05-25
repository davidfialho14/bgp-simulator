package simulation;

import network.Network;
import network.Node;
import protocols.Protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Responsible to hold all the current state of the simulation. This includes the current currentNetwork state, the current
 * state of the routing tables, and the protocols being used by the nodes.
 */
public class State {

    private Network originalNetwork;
    private Network currentNetwork;
    private final Node destination;
    private Protocol defaultProtocol;
    private Map<Node, NodeState> nodesStates;   // state of each node

    /**
     * This constructor is PRIVATE: use the static create methods
     */
    private State(Network network, Node destination, Protocol defaultProtocol,
                  Map<Node, NodeState> nodesStates) {
        this.originalNetwork = network;
        this.currentNetwork = new Network(network); // ensure the current network is a copy of the original
        this.destination = destination;
        this.defaultProtocol = defaultProtocol;
        this.nodesStates = nodesStates;
    }

    /**
     * Creates an empty state for the given network. It initializes all nodes with the given protocol.
     * @param network currentNetwork to create state for.
     * @param destId id of the destination node to simulate for.
     * @param protocol protocol to be used by all nodes by default.
     */
    public static State create(Network network, int destId, Protocol protocol) {
        // associate each node with an empty state
        Map<Node, NodeState> nodesStates = new HashMap<>(network.getNodeCount());
        Node destination = network.getNode(destId);
        network.getNodes().forEach(node -> {
            nodesStates.put(node, new NodeState(node, destination, protocol));
        });


        return new State(network, destination, protocol, nodesStates);
    }

    /**
     * Resets all the state. This clears all the routing tables, reverts all nodes to the default protocol and
     * returns the network to its original state.
     */
    public void reset() {
        this.nodesStates.clear();
        this.defaultProtocol.reset();
        originalNetwork.getNodes()
                .forEach(node -> nodesStates.put(node, new NodeState(node, destination, this.defaultProtocol)));

        // ensure the current network must be a copy of the original
        this.currentNetwork = new Network(originalNetwork);
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
        return nodesStates.get(node);
    }

    /**
     * Returns the state of a node from its id.
     *
     * @param nodeId id of the node to get state for.
     * @return state of the node.
     */
    public NodeState get(int nodeId) {
        return nodesStates.get(new Node(nodeId));
    }

    /**
     * Returns the destination node for this state.
     *
     * @return the destination node for this state.
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Returns the current route tables for each node.
     *
     * @return a map associating each node with its current route table.
     */
    public Map<Node, RouteTable> getRouteTables() {
        return nodesStates.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getTable()
                ));
    }

    /**
     * Returns the current selected routes for each node to reach the destination.
     *
     * @return a map associating each node with its current selected route to reach the destination.
     */
    public Map<Node, Route> getSelectedRoutes() {
        return nodesStates.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getSelectedRoute()
                ));
    }

    /**
     * Adds a new link the current network. This alters the networks state.
     */
    public void addLink() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Removes a link the current network. This alters the networks state.
     */
    public void removeLink() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces the current protocol being used by the node with the given protocol.
     *
     * @param node node to update protocol.
     * @param protocol protocol to replace current one.
     */
    public void updateProtocol(Node node, Protocol protocol) {
        get(node).setProtocol(protocol);
    }
}
