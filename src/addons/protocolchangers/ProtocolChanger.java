package addons.protocolchangers;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Node;

/**
 * Base class that all protocol changers must extend. It implements methods to change a protocol
 * for a node and for all nodes of the topology. This method can and should be used by subclasses.
 */
public abstract class ProtocolChanger {

    protected Engine engine = null;
    protected State state = null;

    /**
     * Creates a protocol changer without assigning it any engine or state.
     */
    public ProtocolChanger() {
    }

    /**
     * Creates a protocol changer by assigning it an engine and a state.
     *
     * @param engine engine to assign to.
     * @param state state to assign to.
     */
    public ProtocolChanger(Engine engine, State state) {
        assignTo(engine, state);
    }

    /**
     * Assigns the protocol changer tp an engine and state.
     *
     * @param engine engine to assign to.
     * @param state state to assign to.
     */
    public void assignTo(Engine engine, State state) {
        this.engine = engine;
        this.state = state;
    }

    /**
     * Changes the protocol of a node.
     *
     * @param node node to change protocol of.
     * @param protocol protocol to change to.
     */
    protected void changeProtocol(Node node, Protocol protocol) {
        state.updateProtocol(node, protocol);
    }

    /**
     * Changes the protocol of all the nodes in the topology of the state assigned to the changer.
     *
     * @param protocol protocol to change to.
     */
    protected void changeAllProtocols(Protocol protocol) {
        state.getTopology().getNetwork().getNodes().forEach(node -> changeProtocol(node, protocol));
    }

}
