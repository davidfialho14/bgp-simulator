package simulation.implementations.linkbreakers;

import network.Network;
import network.Node;
import simulation.NodeStateInfo;
import simulation.Scheduler;

import java.util.Map;

/**
 * A dummy link breaker does not break any link ever. Used when the purpose is not to break links.
 */
public class DummyLinkBreaker extends AbstractLinkBreaker {

    @Override
    public boolean breakAnyLink(Network network, Map<Node, NodeStateInfo> nodesStateInfo, Scheduler scheduler) {
        return false;
    }
}
