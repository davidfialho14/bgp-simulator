package wrappers.routetable;

import network.Node;

/**
 * Represents a node element.
 */
public class NodeElement implements RouteTableElement {

    private Node node;

    private NodeElement(Node node) {
        this.node = node;
    }

    /**
     * Adds the node as a destination to the route table.
     *
     * @param tableWrapper table to insert element in.
     */
    @Override
    public void insert(RouteTableWrapper tableWrapper) {
        tableWrapper.addDestination(node);
    }

    /**
     * Wrapper around the node element constructor to improve readability.
     *
     * @param id id of the node.
     * @return new node element instance with the given id.
     */
    public static NodeElement node(int id) {
        return new NodeElement(new Node(id));
    }
}
