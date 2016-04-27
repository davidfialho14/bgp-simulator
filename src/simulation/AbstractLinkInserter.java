package simulation;

import network.Link;
import network.Network;
import network.exceptions.NodeNotFoundException;

/**
 * Implements the insertion of one link in the network.
 */
public abstract class AbstractLinkInserter implements LinkInserter {

    /**
     * Inserts one link into the given network. If one of the nodes of the link does not exist in the network
     * the node is added and the link as well.
     *
     * @param link link to be inserted.
     * @param network network to insert link on.
     */
    protected void insert(Link link, Network network) {
        try {
            network.addNode(link.getSource());
            network.addNode(link.getDestination());
            network.addLink(link);
        } catch (NodeNotFoundException e) {
            // ignore exception since it is expected to be thrown
        }
    }

}
