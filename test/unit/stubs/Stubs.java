package stubs;

import core.topology.Link;

/**
 * Provides set of static methods to create stub components.
 */
public interface Stubs {

    /**
     * Creates a stub attribute in a more readable way.
     *
     * @param value value of the attribute.
     * @return new stub attribute instance with the given value.
     */
    static StubAttribute stubAttr(int value) {
        return new StubAttribute(value);
    }

    /**
     * Creates a new link instance with a stub label, connecting the source node (srcId) to the
     * destination node (destId).
     *
     * @param srcId id of the source node of the link.
     * @param destId id of the destination node of the link.
     * @return a new link instance with a stub label connecting nodes src and dest.
     */
    static Link stubLink(int srcId, int destId) {
        return new Link(srcId, destId, new StubLabel());
    }

}
