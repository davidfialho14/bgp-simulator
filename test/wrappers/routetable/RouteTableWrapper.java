package wrappers.routetable;

import network.Link;
import network.Node;
import network.SelfLink;
import simulation.Route;
import simulation.RouteTable;

import java.util.*;


/**
 * Implements a set of static method wrappers to improve generating a route table statically in a more
 * readable way.
 */
public class RouteTableWrapper {

    private Collection<Link> outLinks = new ArrayList<>();
    private Collection<Node> destinations = new ArrayList<>();
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

    /**
     * Builds an for the node with the given id.
     *
     * @param nodeId id of the node to create empty route table for.
     * @return the built empty route table.
     */
    public static RouteTable emptyTable(int nodeId) {
        return new RouteTable(Collections.singleton(new SelfLink(nodeId)));
    }

    // ----- BUILD METHODS ------------------------------------------------------------------------------------------

    void addOutLink(Link outLink) {
        outLinks.add(outLink);
    }

    void addDestination(Node destination) {
        destinations.add(destination);
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
        RouteTable table = new RouteTable(outLinks);

        // set each route in the list with each destination and out-link pair
        // routes are stored in a list sequentially instead of divided in rows
        Iterator<Route> routeItr = routes.iterator();

        for (Node destination : destinations) {
            for (Link outLink : outLinks) {
                table.setRoute(outLink, routeItr.next());
            }
        }

        return table;
    }
}
