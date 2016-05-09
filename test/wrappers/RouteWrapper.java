package wrappers;

import stubs.StubAttribute;
import network.Node;
import policies.Attribute;
import policies.PathAttribute;
import simulation.Route;

import static wrappers.PathWrapper.path;

/**
 * Implements wrapper methods to create routes with syntactic meaning.
 */
public interface RouteWrapper {

    /**
     * More readable way to create a route instance.
     */
    static Route route(Node destination, Attribute attribute, PathAttribute path) {
        return new Route(destination, attribute, path);
    }

    /**
     * More readable way to create a route instance.
     */
    static Route route(int destId, Attribute attribute, PathAttribute path) {
        return route(new Node(destId), attribute, path);
    }

    /**
     * Creates a route instance with the given destination. To be used to indicate that any route can be used
     * in some context. It uses a dummy attribute.
     *
     * @param destination destination node of the route.
     * @return new route instance for the given destination.
     */
    static Route anyRoute(Node destination) {
        return new Route(destination, new StubAttribute(), path());
    }

    /**
     * Creates a route instance with the given destination. To be used to indicate that any route can be used
     * in some context. It uses a dummy attribute.
     *
     * @param destId id of the destination node.
     * @return new route instance for the given destination.
     */
    static Route anyRoute(int destId) {
        return anyRoute(new Node(destId));
    }
}
