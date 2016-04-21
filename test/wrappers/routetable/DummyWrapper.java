package wrappers.routetable;

import dummies.DummyLabel;
import network.Link;

/**
 * Implements wrapper methods to create attributes, routes or labels for the Dummy policy.
 */
public class DummyWrapper {

    /**
     * Creates a new link instance with a dummy label, connection the source node (srcId) to the
     * destination node (destId).
     *
     * @param srcId id of the source node of the link.
     * @param destId id of the destination node of the link.
     * @return a new link instance with a dummy label.
     */
    public static Link dummyLink(int srcId, int destId) {
        return new Link(srcId, destId, new DummyLabel());
    }
}
