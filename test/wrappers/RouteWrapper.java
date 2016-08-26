package wrappers;

import core.topology.ConnectedNode;
import stubs.StubAttribute;
import core.Attribute;
import core.Path;
import core.Route;

import static wrappers.PathWrapper.path;

/**
 * Implements wrapper methods to create routes with syntactic meaning.
 */
public interface RouteWrapper {

    /**
     * More readable way to create a route instance.
     */
    static Route route(ConnectedNode destination, Attribute attribute, Path path) {
        return new Route(destination, attribute, path);
    }

    /**
     * More readable way to create a route instance.
     */
    static Route route(int destId, Attribute attribute, Path path) {
        return route(new ConnectedNode(destId), attribute, path);
    }

    /**
     * Creates a route instance with the given destination. To be used to indicate that any route can be used
     * in some context. It uses a dummy attribute.
     *
     * @param destination destination node of the route.
     * @return new route instance for the given destination.
     */
    static Route anyRoute(ConnectedNode destination) {
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
        return anyRoute(new ConnectedNode(destId));
    }
}
