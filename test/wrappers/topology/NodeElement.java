package wrappers.topology;

import core.topology.ConnectedNode;

/**
 * Represents a any node of a link.
 */
public abstract class NodeElement {

    private ConnectedNode node;

    protected NodeElement(ConnectedNode node) {
        this.node = node;
    }

    ConnectedNode getNode() {
        return node;
    }
}
