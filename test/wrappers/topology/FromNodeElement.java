package wrappers.topology;

import core.topology.ConnectedNode;

/**
 * Represents a source node of a link.
 */
public class FromNodeElement extends NodeElement {

    private FromNodeElement(ConnectedNode node) {
        super(node);
    }

    public static FromNodeElement from(int id) {
        return new FromNodeElement(new ConnectedNode(id));
    }
}
