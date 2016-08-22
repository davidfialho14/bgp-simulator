package wrappers.network;

import core.network.Link;
import core.network.Network;
import core.network.exceptions.NodeNotFoundException;
import core.Label;

/**
 * Represents a node element.
 */
public class LinkElement {

    private Link link;

    private LinkElement(Link link) {
        this.link = link;
    }

    /**
     * Wrapper around the link element constructor to improve readability.
     */
    public static LinkElement link(FromNodeElement from, ToNodeElement to, Label label) {
        return new LinkElement(new Link(from.getNode(), to.getNode(), label));
    }

    /**
     * Adds the link to the core.network.
     *
     * @param network core.network to add link to.
     */
    void addTo(Network network) throws NodeNotFoundException {
        network.addNode(link.getSource());
        network.addNode(link.getDestination());
        network.addLink(link);
    }
}
