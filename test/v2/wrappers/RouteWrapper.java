package v2.wrappers;

import v2.core.Attribute;
import v2.core.Path;
import v2.core.Route;

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
