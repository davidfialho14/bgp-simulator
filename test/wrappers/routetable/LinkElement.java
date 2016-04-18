package wrappers.routetable;

import network.Link;
import network.Node;
import policies.Label;

/**
 * Represents a node element.
 */
public class LinkElement implements RouteTableElement {

    private Link link;

    private LinkElement(Link link) {
        this.link = link;
    }

    /**
     * Adds the link as an out-link to the table
     *
     * @param tableWrapper table to insert element in.
     */
    @Override
    public void insert(RouteTableWrapper tableWrapper) {
        tableWrapper.addOutLink(link);
    }

    /**
     * Wrapper around the link element constructor to improve readability.
     *
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @param label label of the link.
     * @return new link element instance.
     */
    public static LinkElement link(int srcId, int destId, Label label) {
        return new LinkElement(new Link(new Node(srcId), new Node(destId), label));
    }
}
