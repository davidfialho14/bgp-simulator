package wrappers;

import network.Node;
import simulation.PathAttribute;

/**
 * Implements wrapper methods to create paths in a more readable way.
 */
public interface PathWrapper {

    /**
     * Builds a path of nodes given a sequence of ids.
     *
     * @param nodeIds sequence of node ids to add to the path.
     * @return new initialized path instance.
     */
    static PathAttribute path(int... nodeIds) {
        Node[] nodes = new Node[nodeIds.length];
        for (int i = 0; i < nodeIds.length; i++) {
            nodes[i] = new Node(nodeIds[i]);
        }

        return new PathAttribute(nodes);
    }
}
