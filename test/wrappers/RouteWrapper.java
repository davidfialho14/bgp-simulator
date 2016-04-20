package wrappers;

import dummies.DummyAttribute;
import network.Node;
import simulation.Route;

import static wrappers.PathWrapper.path;

/**
 * Implements wrapper methods to create routes with syntactic meaning.
 */
public interface RouteWrapper {

    /**
     * Creates a route instance with the given destination. To be used to indicate that any route can be used
     * in some context. It uses a dummy attribute.
     *
     * @param destination destination node of the route.
     * @return new route instance for the given destination.
     */
    static Route anyRoute(Node destination) {
        return new Route(destination, new DummyAttribute(), path());
    }
}
