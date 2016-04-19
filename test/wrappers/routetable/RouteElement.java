package wrappers.routetable;

import policies.Attribute;
import simulation.PathAttribute;
import simulation.Route;

/**
 * Represents a route element.
 */
public class RouteElement implements RouteTableElement {

    private Route route;

    private RouteElement(Route route) {
        this.route = route;
    }

    /**
     * Adds the route as a destination to the route table.
     *
     * @param tableWrapper table to insert element in.
     */
    @Override
    public void insert(RouteTableWrapper tableWrapper) {
        tableWrapper.addRoute(route);
    }

    /**
     * Wrapper around the route element constructor to improve readability.
     *
     * @param attribute attribute of the route.
     * @param path path of the route.
     * @return route element corresponding to the route.
     */
    public static RouteElement route(Attribute attribute, PathAttribute path) {
        // the route destination node does not need to be specified since it will be replaced
        // when building the route table
        return new RouteElement(new Route(null, attribute, path));
    }

    /**
     * Wrapper around the route element constructor create invalid routes in a more readable way.
     *
     * @return route element corresponding to an invalid route.
     */
    public static RouteElement invalid() {
        // the route destination node does not need to be specified since it will be replaced
        // when building the route table
        return new RouteElement(Route.createInvalid(null));
    }
}
