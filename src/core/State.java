package core;

import core.topology.ConnectedNode;
import core.topology.Network;
import core.topology.Topology;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The state class stores the state information of a simulation. The simulation state is defined by the topology
 * being simulated and its destination, the nodes' routing tables and selected routes, and the protocols being
 * applied by each node.
 */
public class State {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Topology originalTopology;    // stores the topology
    private final ConnectedNode destination;
    private final Protocol defaultProtocol;
    private final Map<ConnectedNode, NodeState> nodesStates;   // state of each nodeX

    // stores the current
    private Topology currentTopology;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * This constructor is PRIVATE: use the static create methods
     */
    private State(Topology topology, ConnectedNode destination, Protocol defaultProtocol,
                  Map<ConnectedNode, NodeState> nodesStates) {
        this.originalTopology = topology;
        this.currentTopology = topology; // TODO perform a copy if the topology is changed
        this.destination = destination;
        this.defaultProtocol = defaultProtocol;
        this.nodesStates = nodesStates;
    }

    /**
     * Creates an empty state for the given topology. It initializes all nodes with the given protocol.
     *
     * @param topology current topology to create state for.
     * @param destId id of the destination node to simulate for.
     * @param protocol protocol to be used by all nodes by default.
     */
    public static State create(Topology topology, int destId, Protocol protocol) {
        Network network = topology.getNetwork();

        // associate each node with an empty state
        Map<ConnectedNode, NodeState> nodesStates = new HashMap<>(network.getNodeCount());

        ConnectedNode destination = network.getNode(destId);
        network.getNodes().forEach(node -> nodesStates.put(node, new NodeState(node, destination, protocol)));

        return new State(topology, destination, protocol, nodesStates);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Getters
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the current topology state.
     *
     * @return current topology state.
     */
    public Topology getTopology() {
        return currentTopology;
    }

    /**
     * Returns the state of a node.
     *
     * @param node node to get state for.
     * @return state of the node.
     */
    public NodeState get(ConnectedNode node) {
        return nodesStates.get(node);
    }

    /**
     * Returns the state of a node from its id.
     *
     * @param nodeId id of the node to get state for.
     * @return state of the node.
     */
    public NodeState get(int nodeId) {
        return nodesStates.get(new ConnectedNode(nodeId));
    }

    /**
     * Returns the destination node for this state.
     *
     * @return the destination node for this state.
     */
    public ConnectedNode getDestination() {
        return destination;
    }

    /**
     * Returns the current route tables for each node.
     *
     * @return a map associating each node with its current route table.
     */
    public Map<ConnectedNode, RouteTable> getRouteTables() {
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
    public Map<ConnectedNode, Route> getSelectedRoutes() {
        return nodesStates.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getTable().getSelectedRoute()
                ));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Modifier Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Adds a new link the current topology. This alters the topologies state.
     */
    public void addLink() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Removes a link the current topology. This alters the topologies state.
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
    public void updateProtocol(ConnectedNode node, Protocol protocol) {
        get(node).setProtocol(protocol);
    }

    /**
     * Resets all the state. This clears all the routing tables, reverts all nodes to the default protocol and
     * returns the topology to its original state.
     */
    public void reset() {
        this.nodesStates.clear();
        this.defaultProtocol.reset();
        originalTopology.getNetwork().getNodes()
                .forEach(node -> nodesStates.put(node, new NodeState(node, destination, this.defaultProtocol)));

        // ensure the current topology is equal to the original
        this.currentTopology = originalTopology;    // TODO perform a copy if the topology is changed
    }

}
