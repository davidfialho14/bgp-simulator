package core.topology;

/**
 * Implements a self link. A self link is a special link that represents the imaginary connection between
 * a node and itself. This means that the source and the destination of this link are the same node. A self
 * link has no label, which means that can no be used two link two nodes and can not extend attributes.
 */
public class SelfLink extends Link {

    /**
     * Creates a self link.
     * @param node node to create self link for
     */
    public SelfLink(ConnectedNode node) {
        super(node, node, null);
    }

    /**
     * Creates a self link.
     * @param nodeId  id of the node to create self link for.
     */
    public SelfLink(int nodeId) {
        super(nodeId, nodeId, null);
    }
}
