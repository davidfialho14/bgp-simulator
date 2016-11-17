package wrappers.topology;

import core.topology.ConnectedNode;

/**
 * Represents a source node of a link.
 */
public class ToNodeElement extends NodeElement {

    private ToNodeElement(ConnectedNode node) {
        super(node);
    }

    public static ToNodeElement to(int id) {
        return new ToNodeElement(new ConnectedNode(id));
    }
}
