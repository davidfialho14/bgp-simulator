package wrappers;

import core.topology.ConnectedNode;
import core.Path;

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
    static Path path(int... nodeIds) {
        ConnectedNode[] nodes = new ConnectedNode[nodeIds.length];
        for (int i = 0; i < nodeIds.length; i++) {
            nodes[i] = new ConnectedNode(nodeIds[i]);
        }

        return new Path(nodes);
    }
}
