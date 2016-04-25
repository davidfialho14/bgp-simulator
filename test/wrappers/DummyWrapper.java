package wrappers;

import dummies.DummyAttribute;
import dummies.DummyLabel;
import network.Link;
import network.Node;
import simulation.PathAttribute;
import simulation.Route;
import wrappers.routetable.OutLinkElement;
import wrappers.routetable.RouteElement;

import static wrappers.routetable.OutLinkElement.outLink;
import static wrappers.routetable.RouteElement.route;

/**
 * Implements wrapper methods to create attributes, routes or labels for the Dummy policy.
 */
public interface DummyWrapper {

    /**
     * More readable way to create a dummy attribute.
     *
     * @param value value of the attribute.
     * @return new dummy attribute instance with the given value.
     */
    static DummyAttribute dummyAttr(int value) {
        return new DummyAttribute(value);
    }

    /**
     * Wrapper around @see{@link #dummyAttr(int)} to specify that in certain context any attribute can be used.
     */
    static DummyAttribute anyDummyAttr() {
        return dummyAttr(Integer.MAX_VALUE);
    }

    /**
     * Creates a new link instance with a dummy label, connection the source node (srcId) to the
     * destination node (destId).
     *
     * @param srcId id of the source node of the link.
     * @param destId id of the destination node of the link.
     * @return a new link instance with a dummy label.
     */
    static Link dummyLink(int srcId, int destId) {
        return new Link(srcId, destId, new DummyLabel());
    }

    /**
     * Creates a new link instance with a dummy label and between any two nodes.
     */
    static Link anyDummyLink() {
        return new Link(Integer.MIN_VALUE, Integer.MAX_VALUE, new DummyLabel());
    }

    /**
     * Wrapper around the outLink() method to create dummy out-link elements.
     */
    static OutLinkElement dummyOutLink(int srdId, int destId) {
        return outLink(srdId, destId, new DummyLabel());
    }

    /**
     * Wrapper around the usual route wrapper for dummy attributes.
     */
    static RouteElement dummyRoute(int value, PathAttribute path) {
        return route(new DummyAttribute(value), path);
    }

    /**
     * Wrapper around the route constructor to create a route with a dummy attribute
     */
    static Route dummyRoute(int destination, int value, PathAttribute path) {
        return new Route(new Node(destination), new DummyAttribute(value), path);
    }
}
