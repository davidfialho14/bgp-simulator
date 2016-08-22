package wrappers.network;

import core.topology.Network;
import core.topology.Node;
import core.topology.Policy;
import core.topology.exceptions.NodeNotFoundException;

/**
 * Implements a set of static method wrappers to improve generating a core.topology statically in a more
 * readable way.
 */
public class NetworkWrapper {

    private NetworkWrapper() {}  // can not be instantiated outside of the class

    // ----- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    /**
     * Creates a core.topology from the link elements.
     *
     * @param policy policy of the core.topology.
     * @param links links of the core.topology.
     * @return core.topology instance initialized.
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
