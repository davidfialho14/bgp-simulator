package wrappers;

import policies.implementations.shortestpath.ShortestPathAttribute;
import policies.implementations.shortestpath.ShortestPathLabel;
import simulation.PathAttribute;
import wrappers.routetable.OutLinkElement;
import wrappers.routetable.RouteElement;

import static wrappers.routetable.OutLinkElement.outLink;
import static wrappers.routetable.RouteElement.route;

/**
 * Implements wrapper methods to create attributes, routes or labels for the Shortest Path policy.
 */
public interface ShortestPathWrapper {

    /**
     * Wrapper around the ShortestPathLabel constructor to improve readability.
     *
     * @param length length of the label.
     * @return a new ShortestPathLabel instance assign the given length.
     */
    static ShortestPathLabel label(int length) {
        return new ShortestPathLabel(length);
    }

    /**
     * Wrapper around the usual link wrapper for shortest path labels.
     *
     * @return a new link instance with a label with the given length.
     */
    static OutLinkElement splink(int srcId, int destId, int length) {
        return outLink(srcId, destId, new ShortestPathLabel(length));
    }

    /**
     * Wrapper around the usual route wrapper for shortest path attributes.
     *
     * @return a new route instance with a attribute and path.
     */
    static RouteElement sproute(int length, PathAttribute path) {
        return route(new ShortestPathAttribute(length), path);
    }

}
