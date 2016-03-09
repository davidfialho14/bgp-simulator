package implementations.protocols;

import network.Network;
import network.Node;
import network.NodeFactory;

public class BGPNodeFactory implements NodeFactory {

    /**
     * Creates new instances of BGPNode.
     * @return new instance og BGPNode.
     * @see network.NodeFactory#createNode(Network, int)
     */
    @Override
    public Node createNode(Network network, int id) {
        return new BGPNode(network, id);
    }

}
