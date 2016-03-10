package network;

import protocols.DummyProtocolFactory;

/**
 * Factory class to create instances necessary for abstract and interface classes necessary to build a network.
 * The created elements are guaranteed to be able to work together.
 */
class Factory {

    /**
     * Creates a link between two nodes of a network with dummy label. It creates the nodes from the ids.
     * To be used when a link is needed but it label its not used.
     * @param network network where node are to be linked.
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @return link instance.
     */
    static Link createLink(Network network, int srcId, int destId) {
        ProtocolFactory factory = new DummyProtocolFactory();

        // do not care about the link length
        return new Link(new Node(network, srcId, factory.createProtocol(srcId)),
                new Node(network, destId, factory.createProtocol(destId)), null);
    }

}
