package wrappers.network;

import core.network.Node;

/**
 * Represents a source node of a link.
 */
public class FromNodeElement extends NodeElement {

    private FromNodeElement(Node node) {
        super(node);
    }

    public static FromNodeElement from(int id) {
        return new FromNodeElement(new Node(id));
    }
}
