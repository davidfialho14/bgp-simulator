package statemodifiers;

import core.Protocol;
import core.State;
import core.topology.Node;

/**
 * The state modifiers interface contains a set of static methods to perform modifications to the state.
 */
public interface StateModifiers {

    /**
     * Deploys a new protocol for one node of the state. This is accomplished by changing the node's current protocol.
     *
     * @param state     state to modify.
     * @param node      node to deploy the new protocol.
     * @param protocol  protocol to deploy.
     */
    static void deployProtocol(State state, Node node, Protocol protocol) {
        state.updateProtocol(node, protocol);
    }

    /**
     * Deploys a new protocol for all nodes in the state.
     *
     * @param state     state to modify.
     * @param protocol  protocol to deploy for all nodes.
     */
    static void deployProtocolToAll(State state, Protocol protocol) {
        state.getTopology().getNetwork().getNodes().forEach(node -> deployProtocol(state, node, protocol));
    }

}
