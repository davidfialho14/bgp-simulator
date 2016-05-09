package addons.linkbreakers;

import network.Link;
import network.Network;

/**
 * Implements the breaking of a specific link on a protected method that can be used by future implementations.
 */
public abstract class AbstractLinkBreaker implements LinkBreaker {

    @Override
    abstract public Link breakAnyLink(Network network, long currentTime);

    /**
     * Removes the given link form the network and removes all routes exported through that link from the scheduler.
     * If the link does not exist in the network it raises a LinkNotFoundException and the scheduler is not altered.
     *
     * @param link link to be removed.
     * @param network network to remove link from.N
     * @throws LinkNotFoundException thrown when the given link does not exist in the network.
     */
    protected void breakLink(Link link, Network network)
            throws LinkNotFoundException {

        if (!network.removeLink(link)) {
            throw new LinkNotFoundException("could not break '" + link + "'");
        }
    }
}
