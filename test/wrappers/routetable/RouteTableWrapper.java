package wrappers.routetable;

import network.Link;
import network.Node;
import simulation.Route;
import simulation.RouteTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Implements a set of static method wrappers to improve generating a route table statically in a more
 * readable way.
 */
public class RouteTableWrapper {

    private Collection<Link> outLinks = new ArrayList<>();
    private Node destination;
    private List<Route> routes = new ArrayList<>();

    private RouteTableWrapper() {}  // can not be instantiated outside of the class

    // ----- PUBLIC METHODS -----------------------------------------------------------------------------------------

    /**
     * Builds a route table from route table elements.
     *
     * @param elements elements of the route table.
     * @return the built route table.
     */
    public static RouteTable table(RouteTableElement... elements) {
        RouteTableWrapper wrapper = new RouteTableWrapper();

        for (RouteTableElement element : elements) {
            element.insert(wrapper);
        }

        return wrapper.build();
    }

    // ----- BUILD METHODS ------------------------------------------------------------------------------------------

    void addOutLink(Link outLink) {
        outLinks.add(outLink);
    }

    void setDestination(Node destination) {
        this.destination = destination;
    }

    void addRoute(Route route) {
        routes.add(route);
    }

    /**
     * Builds the route table taking into account the destinations, out-links, and routes that are available.
     *
     * @return built route table.
     */
    private RouteTable build() {
        RouteTable table = new RouteTable(destination, outLinks);

        // set each route in the list with each destination and out-link pair
        // routes are stored in a list sequentially instead of divided in rows
        Iterator<Route> routeItr = routes.iterator();

        for (Link outLink : outLinks) {
            Route route = routeItr.next();
            route.setDestination(destination);
            table.setRoute(outLink, route);
        }

        return table;
    }
}
