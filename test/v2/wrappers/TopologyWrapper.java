package v2.wrappers;


import v2.core.Node;
import v2.core.Router;

import static v2.core.protocols.DummyDetection.dummyDetection;

/**
 * Implements wrapper methods to create topology components in a more readable way.
 */
public interface TopologyWrapper {

    /**
     * More readable way to create a router instance providing only a router ID.
     *
     * @param routerID id to assigning to the new router.
     * @return new instance of a router with the given ID.
     */
    static Router router(int routerID) {
        return new Router(routerID, 0, dummyDetection());
    }

    /**
     * More readable way to create a node instance.
     *
     * @param nodeID id to assigning to the new node.
     * @return new instance of a node with the given ID.
     */
    static Node node(int nodeID) {
        return new Node(nodeID);
    }

}
