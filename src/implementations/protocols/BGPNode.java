package implementations.protocols;

import network.Network;
import network.Node;

public class BGPNode extends Node {

    /**
     * @see network.Node#Node(network.Network, int)
     */
    BGPNode(Network network, int id) {
        super(network, id);
    }
}
