package wrappers;


import core.Label;
import core.Link;
import core.Node;
import core.Router;

import static core.protocols.DummyDetection.dummyDetection;

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

    /**
     * More readable way to create a link between two routers with a custom label.
     *
     * @param sourceID  ID of the link's source router.
     * @param targetID  ID of the link's target router.
     * @param label     label to assign the link.
     * @return new instance of a node with the given ID.
     */
    static Link link(int sourceID, int targetID, Label label) {
        return new Link(router(sourceID), router(targetID), label);
    }

}
