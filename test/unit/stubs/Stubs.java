package stubs;

import core.Link;
import core.Path;
import core.Route;
import core.Router;

import static wrappers.RouteWrapper.route;

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
     * Creates a stub label in a more readable way.
     *
     * @return new stub label instance.
     */
    static StubLabel stubLabel() {
        return new StubLabel();
    }

    /**
     * Creates a new link connecting the given source and target routers and associates the link with a stub
     * label. This would be the same as calling: '''new Link(source, target, stubLabel())'''
     *
     * @param source source router.
     * @param target target router.
     * @return a new link instance with a stub label connecting nodes src and dest.
     */
    static Link stubLink(Router source, Router target) {
        return new Link(source, target, stubLabel());
    }

    /**
     * Creates a route with a stub attribute assigned the given value.
     *
     * @param attrValue value for the stub attribute.
     * @param path      path to assign to the route.
     * @return route with the given path and a stub attribute with the given value.
     */
    static Route stubRoute(int attrValue, Path path) {
        return route(stubAttr(attrValue), path);
    }

}
