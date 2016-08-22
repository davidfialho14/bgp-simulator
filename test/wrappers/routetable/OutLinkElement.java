package wrappers.routetable;

import core.topology.Link;
import core.topology.Node;
import core.topology.SelfLink;
import core.Label;

/**
 * Represents a node element.
 */
public class OutLinkElement implements RouteTableElement {

    private Link link;

    private OutLinkElement(Link link) {
        this.link = link;
    }

    /**
     * Wrapper around the link element constructor to improve readability.
     *
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @param label label of the link.
     * @return new out-link element instance.
     */
    public static OutLinkElement outLink(int srcId, int destId, Label label) {
        return new OutLinkElement(new Link(new Node(srcId), new Node(destId), label));
    }

    /**
     * Wrapper around the element constructor to create self links in a more readable way.
     *
     * @param id id of the node create self link for.
     * @return new out-link element instance.
     */
    public static OutLinkElement selfLink(int id) {
        return new OutLinkElement(new SelfLink(id));
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
}
