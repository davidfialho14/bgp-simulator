package wrappers.network;

import core.network.Network;
import core.network.Node;
import core.network.exceptions.NodeNotFoundException;
import core.Policy;

/**
 * Implements a set of static method wrappers to improve generating a core.network statically in a more
 * readable way.
 */
public class NetworkWrapper {

    private NetworkWrapper() {}  // can not be instantiated outside of the class

    // ----- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    /**
     * Creates a core.network from the link elements.
     *
     * @param policy policy of the core.network.
     * @param links links of the core.network.
     * @return core.network instance initialized.
     */
    public static Network network(Policy policy, LinkElement... links) {
        Network network = new Network(policy);
        try {
            for (LinkElement link : links)
                link.addTo(network);

        } catch (NodeNotFoundException e) {
            e.printStackTrace();
        }

        return network;
    }

    /**
     * Creates a node with any id. To be used in a context when the id of the node is not important.
     */
    public static Node anyNode() {
        return new Node(Integer.MAX_VALUE);
    }

}
