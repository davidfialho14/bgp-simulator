package stubs;

import core.Path;
import core.Route;
import core.topology.Link;
import core.topology.Node;

import static core.Route.newRouteFrom;

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

    /**
     * Creates a route with a stub attribute with the given value and the given path.
     *
     * @param destination destination node of the route.
     * @param value value of the attribute.
     * @param path path of the route.
     * @return new route instance for the given destination and with a stub attribute with the given value and
     * associated with the given path,
     */
    static Route stubRoute(Node destination, int value, Path path) {
        return newRouteFrom(Route.invalidRoute(destination))
                .withAttribute(new StubAttribute(value))
                .withPath(path)
                .build();
    }

}
