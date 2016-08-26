package wrappers;

import core.Attribute;
import core.Path;
import core.Route;
import core.topology.ConnectedNode;
import core.topology.Node;
import stubs.StubAttribute;

import static core.Route.newRouteFrom;
import static wrappers.PathWrapper.path;

/**
 * Implements wrapper methods to create routes with syntactic meaning.
 */
public interface RouteWrapper {

    /**
     * More readable way to create a route instance.
     */
    static Route route(Node destination, Attribute attribute, Path path) {
        return newRouteFrom(Route.invalidRoute(destination))
                .withAttribute(attribute)
                .withPath(path)
                .build();
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
    static Route anyRoute(Node destination) {
        return route(destination, new StubAttribute(), path());
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

    /**
     * Creates an invalid route from an integer ID instead of a node.
     *
     * @param destId id for the destination.
     * @return new invalid route for a destianiton with the given id.
     */
    static Route invalidRoute(int destId) {
        return Route.invalidRoute(Node.newNode(destId));
    }
}
