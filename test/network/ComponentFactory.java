package network;

import implementations.policies.shortestpath.ShortestPathAttribute;
import implementations.policies.shortestpath.ShortestPathLabel;
import implementations.policies.shortestpath.ShortestPathAttributeFactory;
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

    /**
     * Creates an arbitrary attribute. To be used when there is the need for an attribute instance but its value
     * does not matter.
     * @return new attribute instance.
     */
    static Attribute createAttribute() {
        return new ShortestPathAttribute(0);
    }

    /**
     * Creates an attribute instance. To be used to create specific attributes in order to be able to compare them.
     * Two attributes created with the same id will be equal and two attributes created with different ids will be
     * not equal.
     * @return new attribute instance.
     */
    static Attribute createAttribute(int id) {
        return new ShortestPathAttribute(id);
    }

    static AttributeFactory createAttributeFactory() {
        return new ShortestPathAttributeFactory();
    }

    /**
     * Creates nodes with random id. The ids will never repeat between calls.
     * @return new node instance.
     */
    private static int nodeId = 0;
    static Node createRandomNode() {
        return new Node(null, nodeId++, null);
    }

    /**
     * Creates n nodes with random ids. The ids will never repeat between calls.
     * @return array with the created nodes.
     */
    static Node[] createRandomNode(int n) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(null, nodeId++, null);
        }

        return nodes;
    }

}
