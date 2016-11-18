package v2.wrappers;


import v2.core.policies.shortestpath.ShortestPathAttribute;
import v2.core.policies.shortestpath.ShortestPathLabel;

/**
 * Implements wrapper methods to create attributes, routes or labels for the Shortest Path policy.
 */
public interface ShortestPathWrapper {

    /**
     * Wrapper around the ShortestPathAttribute constructor. It improves the readability when a SP attribute
     * instance is necessary.
     */
    static ShortestPathAttribute spAttr(int length) {
        return new ShortestPathAttribute(length);
    }

    /**
     * Wrapper around the ShortestPathLabel constructor. It improves the readability when a SP label
     * instance is necessary.
     */
    static ShortestPathLabel spLabel(int length) {
        return new ShortestPathLabel(length);
    }

}
