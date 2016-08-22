package wrappers.network;

import core.topology.Node;

/**
 * Represents a any node of a link.
 */
public abstract class NodeElement {

    private Node node;

    protected NodeElement(Node node) {
        this.node = node;
    }

    Node getNode() {
        return node;
    }
}
