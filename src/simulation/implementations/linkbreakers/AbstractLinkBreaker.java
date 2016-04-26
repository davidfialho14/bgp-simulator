package simulation.implementations.linkbreakers;

import network.Link;
import network.Network;
import network.Node;
import simulation.LinkBreaker;
import simulation.NodeStateInfo;
import simulation.Scheduler;

import java.util.Map;

/**
 * Implements the breaking of a specific link on a protected method that can be used by future implementations.
 */
public abstract class AbstractLinkBreaker implements LinkBreaker {

    @Override
    abstract public boolean breakAnyLink(Network network, Map<Node, NodeStateInfo> nodesStateInfo, Scheduler scheduler);

    /**
     * Removes the given link form the network and removes all routes exported through that link from the scheduler.
     * If the link does not exist in the network it raises a LinkNotFoundException and the scheduler is not altered.
     *
     * @param link link to be removed.
     * @param network network to remove link from.
     * @param scheduler scheduler used to simulate the given network.
     * @throws LinkNotFoundException thrown when the given link does not exist in the network.
     */
    protected void breakLink(Link link, Network network, Map<Node, NodeStateInfo> nodesStateInfo, Scheduler scheduler)
            throws LinkNotFoundException {

        if (!network.remove(link)) {
            throw new LinkNotFoundException("could not break '" + link + "'");
        }

        scheduler.removeRoutes(link);

        nodesStateInfo.get(link.getSource()).getTable().removeOutLink(link);
    }
}
