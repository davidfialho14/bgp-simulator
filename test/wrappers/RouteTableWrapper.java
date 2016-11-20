package wrappers;

import core.Node;
import core.Route;
import core.RouteTable;

import java.util.HashMap;
import java.util.Map;


/**
 * Implements a set of static method wrappers to improve generating a route table statically in a more
 * readable way.
 */
public class RouteTableWrapper {

    private RouteTableWrapper() {}  // can not be instantiated outside of the class

    /**
     * Wrapper around the route table's constructor to create an empty table in a more readable way.
     *
     * @return empty route table.
     */
    public static RouteTable emptyTable() {
        return new RouteTable();
    }

    // ----- Builder For the Route Table --------------------------------------------------------------------

    /**
     * Returns a route table builder.
     *
     * @return a route table builder.
     */
    public static RouteTableBuilder table() {
        return new RouteTableBuilder();
    }

    public static class RouteTableBuilder {

        private final Map<Node, Route> entries = new HashMap<>();

        public RouteTableBuilder entry(Node neighbor, Route route) {
            entries.put(neighbor, route);
            return this;
        }

        public RouteTableBuilder entry(int neighborID, Route route) {
            entries.put(TopologyWrapper.node(neighborID), route);
            return this;
        }

        public RouteTable build() {
            RouteTable routeTable = new RouteTable();
            entries.entrySet().forEach(entry -> routeTable.setRoute(entry.getKey(), entry.getValue()));

            return routeTable;
        }

    }

}
