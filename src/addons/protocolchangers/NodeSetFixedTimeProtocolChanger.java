package addons.protocolchangers;

import network.Node;
import protocols.Protocol;
import simulation.Engine;
import simulation.State;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Fixed time protocol changer based on a set of nodes that are changed when it is time to change the protocol.
 */
public class NodeSetFixedTimeProtocolChanger extends FixedTimeProtocolChanger {

    private Protocol protocolToChangeTo;
    private Set<Node> nodesToChange;

    /**
     * Creates a fixed time protocol changer with the protocol to change to and the sequence of nodes
     * to be changed.
     *
     * @param changeTime time to change protocol.
     * @param protocol protocol to change to.
     * @param nodes nodes to change protocol for.
     */
    public NodeSetFixedTimeProtocolChanger(long changeTime, Protocol protocol, Node... nodes) {
        super(changeTime);
        this.protocolToChangeTo = protocol;
        Collections.addAll(nodesToChange, nodes);
    }

    /**
     * Creates a fixed time protocol changer by assigning it an engine and a state and associating it
     * with a time instant to change protocol and with the protocol to change to and the sequence of
     * nodes to be changed.
     *
     * @param engine engine to assign to.
     * @param state state to assign to.
     * @param changeTime time to change protocol.
     * @param protocol protocol to change to.
     * @param nodes nodes to change protocol for.
     */
    public NodeSetFixedTimeProtocolChanger(Engine engine, State state, long changeTime, Protocol protocol, Node... nodes) {
        super(engine, state, changeTime);
        this.protocolToChangeTo = protocol;
        this.nodesToChange =  new HashSet<>(nodes.length);
        Collections.addAll(nodesToChange, nodes);
    }

    @Override
    public void onTimeChange(long newTime) {

        if (isTimeToChange(newTime)) {
            nodesToChange.forEach(node -> changeProtocol(node, protocolToChangeTo));
        }
    }
}
