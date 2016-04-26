package simulation.implementations.linkbreakers;


import network.Link;
import network.Network;
import network.Node;
import simulation.NodeStateInfo;
import simulation.Scheduler;

import java.util.Map;

/**
 * A fixed breaker will break a given link in a fixed instant of time T.
 */
public class FixedLinkBreaker extends AbstractLinkBreaker {

    private Link linkToBreak;
    private long timeToBreak;

    /**
     * Constructs a fixed link breaker with the link to break and the instant of time to do it.
     *
     * @param link link to be broken.
     * @param timeInstant instant of time to break the link.
     */
    public FixedLinkBreaker(Link link, long timeInstant) {
        this.linkToBreak = link;
        this.timeToBreak = timeInstant;
    }

    @Override
    public boolean breakAnyLink(Network network, Map<Node, NodeStateInfo> nodesStateInfo, Scheduler scheduler) {

        if (scheduler.getCurrentTime() == timeToBreak) {
            try {
                breakLink(linkToBreak, network, nodesStateInfo, scheduler);
                return true;
            } catch (LinkNotFoundException e) {
                // the link does not exist on the given network
            }
        }

        return false;
    }
}
