package simulation;

import network.Link;
import network.Network;

/**
 * Defines the interface for a link breaker. A link breaker is able to break links of a network. When a link
 * is broken from the network, all routes being exported through that link are lost.
 */
public interface LinkBreaker {

    /**
     * Breaks any link from the network. If a link was broken it is removed completely from the network and the broken
     * link is returned. If no link was broken null is returned.
     *
     * @param network network to break link from.
     * @param currentTime current of the simulation.
     * @return the broken link or null if no link was broken.
     */
    Link breakAnyLink(Network network, long currentTime);

}
