package wrappers;

import core.Attribute;
import core.Path;
import core.Route;

/**
 * Implements wrapper methods to create routes with syntactic meaning.
 */
public interface RouteWrapper {

    /**
     * More readable way to create a route instance.
     */
    static Route route(Attribute attribute, Path path) {
        return new Route(attribute, path);
    }

}
