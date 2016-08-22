package wrappers.routetable;

import core.topology.Node;

/**
 * Represents a node element.
 */
public class DestinationElement implements RouteTableElement {

    private Node node;

    private DestinationElement(Node node) {
        this.node = node;
    }

    /**
     * Wrapper around the node element constructor to improve readability.
     *
     * @param id id of the node.
     * @return new node element instance with the given id.
     */
    public static DestinationElement destination(int id) {
        return new DestinationElement(new Node(id));
    }

    /**
     * Adds the node as a destination to the route table.
     *
     * @param tableWrapper table to insert element in.
     */
    @Override
    public void insert(RouteTableWrapper tableWrapper) {
        tableWrapper.setDestination(node);
    }
}
