package wrappers.topology;

import core.topology.Network;
import core.topology.Node;
import core.topology.Policy;
import core.topology.Topology;
import core.topology.exceptions.NodeNotFoundException;

/**
 * Implements a set of static method wrappers to improve generating a topology statically in a more
 * readable way.
 */
public class TopologyWrapper {

    private TopologyWrapper() {
    }  // can not be instantiated outside of the class

    // ----- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    /**
     * Creates a topology from the link elements.
     *
     * @param policy policy of the topology.
     * @param links links of the topology.
     * @return topology instance initialized.
     */
    public static Topology topology(Policy policy, LinkElement... links) {
        Network network = new Network();
        try {
            for (LinkElement link : links)
                link.addTo(network);

        } catch (NodeNotFoundException e) {
            e.printStackTrace();
        }

        return new Topology(network, policy);
    }

    /**
     * Creates a node with any id. To be used in a context when the id of the node is not important.
     */
    public static Node anyNode() {
        return new Node(Integer.MAX_VALUE);
    }

}
