package wrappers;

import stubs.StubAttribute;
import stubs.StubLabel;
import network.Link;
import network.Node;
import policies.PathAttribute;
import simulation.Route;
import wrappers.routetable.OutLinkElement;
import wrappers.routetable.RouteElement;

import static wrappers.routetable.OutLinkElement.outLink;
import static wrappers.routetable.RouteElement.route;

/**
 * Implements wrapper methods to create attributes, routes or labels for the Stub policy.
 */
public interface StubWrapper {

    /**
     * More readable way to create a stub attribute.
     *
     * @param value value of the attribute.
     * @return new stub attribute instance with the given value.
     */
    static StubAttribute stubAttr(int value) {
        return new StubAttribute(value);
    }

    /**
     * Creates a new link instance with a stub label, connection the source node (srcId) to the
     * destination node (destId).
     *
     * @param srcId id of the source node of the link.
     * @param destId id of the destination node of the link.
     * @return a new link instance with a stub label.
     */
    static Link stubLink(int srcId, int destId) {
        return new Link(srcId, destId, new StubLabel());
    }

    /**
     * Creates a new link instance with a stub label and between any two nodes.
     */
    static Link anyStubLink() {
        return new Link(Integer.MIN_VALUE, Integer.MAX_VALUE, new StubLabel());
    }

    /**
     * Wrapper around the outLink() method to create stub out-link elements.
     */
    static OutLinkElement stubOutLink(int srdId, int destId) {
        return outLink(srdId, destId, new StubLabel());
    }

    /**
     * Wrapper around the usual route wrapper for stub attributes.
     */
    static RouteElement stubRoute(int value, PathAttribute path) {
        return route(new StubAttribute(value), path);
    }

    /**
     * Wrapper around the route constructor to create a route with a stub attribute
     */
    static Route stubRoute(int destination, int value, PathAttribute path) {
        return new Route(new Node(destination), new StubAttribute(value), path);
    }
}
