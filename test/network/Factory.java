package network;

import protocols.DummyProtocolFactory;

/**
 * Factory class to create instances necessary for abstract and interface classes necessary to build a network.
 * The created elements are guaranteed to be able to work together.
 */
public class Factory {

    /**
     * Creates a link between two nodes of a network with dummy label. It creates the nodes from the ids.
     * To be used when a link is needed but it label its not used.
     * @param network network where node are to be linked.
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @return link instance.
     */
    public static Link createLink(Network network, int srcId, int destId) {
        ProtocolFactory factory = new DummyProtocolFactory();

        // do not care about the link length
        return new Link(new Node(network, srcId, factory.createProtocol(srcId)),
                new Node(network, destId, factory.createProtocol(destId)), null);
    }

    /**
     * Creates a node instance with an arbitrary id. The network and stubProtocol are initialized with null.
     * To be used when the id of node is not important and the network and stubProtocol are not used.
     * @return new node instance.
     */
    public static Node createNode() {
        return new Node(null, 0, null);
    }

    /**
     * Creates a node instance with the specified id. The network and stubProtocol are initialized with null.
     * To be used when the id of node is important and the network and stubProtocol are not used.
     * @return new node instance.
     */
    public static Node createNode(int id) {
        return new Node(null, id, null);
    }

    /**
     * Creates n node instances with different ids. The returned nodes are ordered by id from the smaller id to the
     * higher. The network and stubProtocol are initialized with null. To be used when the ids of the nodes is important
     * but the network and stubProtocol are not used.
     * @return array with n node instances.
     */
    public static Node[] createNodes(int n) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(null, i, null);
        }

        return nodes;
    }

    // used to create random nodes
    private static int nodeId = 0;

    /**
     * Creates nodes with random id. The ids will never repeat between calls.
     * @return new node instance.
     */
    public static Node createRandomNode() {
        return new Node(null, nodeId++, null);
    }

    /**
     * Creates n nodes with random ids. The ids will never repeat between calls.
     * @return array with the created nodes.
     */
    public static Node[] createRandomNode(int n) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(null, nodeId++, null);
        }

        return nodes;
    }

    /**
     * Creates n node instances with different random ids. The is no order in the returned nodes. The network and
     * stubProtocol are initialized with null. To be used when the ids of the nodes is important but the network and
     * stubProtocol are not used. This method only exists for clarity in the test code, it indicates explicitly that the
     * nodes created are random.
     * @return array with n node instances.
     */
    public static Node[] createRandomNodes(int n) {
        return createNodes(n);
    }

}
