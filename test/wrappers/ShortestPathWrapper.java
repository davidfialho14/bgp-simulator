package wrappers;

import network.Node;
import policies.implementations.shortestpath.ShortestPathAttribute;
import policies.implementations.shortestpath.ShortestPathLabel;
import simulation.PathAttribute;
import simulation.Route;
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

    /**
     * Wrapper around the route constructor to create shortest path routes.
     *
     * @return a new route instance with shortest path attribute.
     */
    static Route sproute(int destId, int length, PathAttribute path) {
        return new Route(new Node(destId), new ShortestPathAttribute(length), path);
    }

    /**
     * Wrapper around the ShortestPathAttribute constructor. It improves the readability when a SP attribute
     * instance is necessary.
     */
    static ShortestPathAttribute spattribute(int length) {
        return new ShortestPathAttribute(length);
    }

    /**
     * Wrapper around the ShortestPathLabel constructor. It improves the readability when a SP label
     * instance is necessary.
     */
    static ShortestPathLabel splabel(int length) {
        return new ShortestPathLabel(length);
    }

    /**
     * Wrapper to create a ShortestPathLabel with any length. This should be used to indicate that in the
     * context that is being used the shortest path label can be of any length.
     *
     * @return new instance of ShortestPathLabel with any length.
     */
    static ShortestPathLabel anySPLabel() {
        return new ShortestPathLabel(Integer.MAX_VALUE);
    }

}
