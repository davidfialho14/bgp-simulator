package network;

import implementations.policies.ShortestPathLabel;
import implementations.protocols.BGPProtocolFactory;

/**
 * Factory class to create instances of network components.
 * The created components are guaranteed to be able to work together.
 */
public class ComponentFactory {

    static ProtocolFactory createProtocolFactory() {
        return new BGPProtocolFactory();
    }

    /**
     * Creates any label. To be used when the specific value of the label does not matter.
     * @return a label instance.
     */
    static Label createLabel() {
        return new ShortestPathLabel(0);
    }

    /**
     * Creates a link between two nodes of a network with any label. To be used when a link is needed but its
     * label can be anything.
     * @param network network where node are to be linked.
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @return link instance.
     */
    static Link createLink(Network network, int srcId, int destId) {
        ProtocolFactory factory = createProtocolFactory();

        // do not care about the link length
        return new Link(new Node(network, srcId, factory.createProtocol(srcId)),
                new Node(network, destId, factory.createProtocol(destId)), createLabel());
    }
}
