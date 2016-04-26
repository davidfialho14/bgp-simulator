package simulation;

import network.Network;
import network.Node;

import java.util.Map;

/**
 * Defines the interface for a link breaker. A link breaker is able to break links of a network. When a link
 * is broken from the network, all routes being exported through that link are lost.
 */
public interface LinkBreaker {

    /**
     * Breaks any link from the network. All routes in the scheduler exported through the broken link are
     * removed from the scheduler. Calling this method might not remove any link from the network, in that
     * case it returns false. If a link is removed the respective out-link is removed from the source node's
     * route table.
     *
     * @param network network to break link from.
     * @param nodesStateInfo state information of the nodes during simulation.
     * @param scheduler scheduler used during the simulation of the given network.
     * @return true if any link was broken or false otherwise.
     */
    boolean breakAnyLink(Network network, Map<Node, NodeStateInfo> nodesStateInfo, Scheduler scheduler);

}
