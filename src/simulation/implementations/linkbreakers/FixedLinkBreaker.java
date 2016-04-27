package simulation.implementations.linkbreakers;


import network.Link;
import network.Network;

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
    public Link breakAnyLink(Network network, long currentTime) {

        if (currentTime == timeToBreak) {
            try {
                breakLink(linkToBreak, network);
                return linkToBreak;
            } catch (LinkNotFoundException e) {
                // the link does not exist on the given network
            }
        }

        return null;
    }
}
