package simulation.networks;

import network.Network;
import network.Node;
import simulation.PathAttribute;
import simulation.RouteTable;
import simulation.implementations.policies.shortestpath.ShortestPathAttribute;
import simulation.implementations.policies.shortestpath.ShortestPathAttributeFactory;

import java.util.Map;

public abstract class Topology {

    protected Network network;

    abstract public Network getNetwork();

    abstract public Map<Node, RouteTable> getExpectedRouteTables();

    // ------- HELPER METHODS ------------------------------------------------------------------------------------------
    /**
     * Creates the route table for the node with the given integer id.
     * @param network network holding the node.
     * @param nodeId id of the node.
     * @return route table for the node with the given integer id.
     */
    protected RouteTable createRouteTableForNode(Network network, int nodeId) {
        return new RouteTable(network.getNode(nodeId).getOutNeighbours(), new ShortestPathAttributeFactory());
    }

    /**
     * Sets a route on the route table.
     * @param routeTable route table to set route for.
     * @param network network holding hte nodes.
     * @param destId  id of the destination node to associated with the route.
     * @param neighbourId id of the neighbour node to associated with the route.
     * @param length length attribute to be set.
     * @param path path attribute to be set. array containing the ids of hte nodes in the path.
     */
    protected void setRoute(RouteTable routeTable, Network network, int destId, int neighbourId,
                            int length, int[] path) {
        routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId),
                new ShortestPathAttribute(length));

        // create array of nodes for the path
        Node[] pathNodes = new Node[path.length];
        for (int i = 0; i < pathNodes.length; i++) {
            pathNodes[i] = new Node(network, path[i]);
        }

        routeTable.setPath(network.getNode(destId), network.getNode(neighbourId), new PathAttribute(pathNodes));
    }

    /**
     * Sets an invalid route for the given route table in the pair (destination, neighbour) given.
     * @param routeTable route table to set invalid route for.
     * @param network network golding the nodes.
     * @param destId id of the destination node to associated with invalid route.
     * @param neighbourId id of the neighbour node to associated with invalid route.
     */
    protected void setInvalidRoute(RouteTable routeTable, Network network, int destId, int neighbourId) {
        ShortestPathAttribute invalidAttribute = ShortestPathAttribute.createInvalidShortestPath();
        routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId), invalidAttribute);
        routeTable.setPath(network.getNode(destId), network.getNode(neighbourId), PathAttribute.createInvalidPath());
    }

//    /**
//     * Creates the network1.
//     *  digraph network1 {
//     *      0 -> 1 -> 2 [label=1];
//     *      0 -> 2 [label=0];
//     *  }
//     */
//    private Network createNetwork1() throws NodeExistsException, NodeNotFoundException {
//        Network network = new Network();
//        network.addNode(0);
//        network.addNode(1);
//        network.addNode(2);
//        network.link(0, 1, new ShortestPathLabel(1));
//        network.link(1, 2, new ShortestPathLabel(1));
//        network.link(0, 2, new ShortestPathLabel(0));
//        return network;
//    }
//
//    private Map<Node, RouteTable> expectedRouteTableForNetwork1(Network network) {
//        Map<Node, RouteTable> expectedTables = new HashMap<>();
//        RouteTable routeTable;
//
//        /* node 0 route table
//            |   |     1     |    2   |
//            |:-:|:---------:|:------:|
//            | 1 |   1, [1]  |    •   |
//            | 2 | 2, [1, 2] | 0, [2] |
//        */
//        routeTable = createRouteTableForNode(network, 0);
//        setRoute(routeTable, network, 1, 1, 1, new int[]{1});
//        setInvalidRoute(routeTable, network, 1, 2);
//        setRoute(routeTable, network, 2, 1, 2, new int[]{1, 2});
//        setRoute(routeTable, network, 2, 2, 0, new int[]{2});
//        expectedTables.put(network.getNode(0), routeTable);
//
//        /* node 1 route table
//            |   |    2   |
//            |:-:|:------:|
//            | 2 | 1, [2] |
//         */
//        routeTable = createRouteTableForNode(network, 1);
//        setRoute(routeTable, network, 2, 2, 1, new int[]{2});
//        expectedTables.put(network.getNode(1), routeTable);
//
//        /* node 2 route table
//            |   |
//            |:-:|
//         */
//        routeTable = createRouteTableForNode(network, 2);
//        expectedTables.put(network.getNode(2), routeTable);
//
//        return expectedTables;
//    }
//
//    /**
//     * Creates the network2.
//     *  digraph network2 {
//     *      0 -> 1 -> 2 [label=1];
//     *      2 -> 0 [label=1];
//     *  }
//     */
//    private Network createNetwork2() throws NodeExistsException, NodeNotFoundException {
//        Network network = new Network();
//        network.addNode(0);
//        network.addNode(1);
//        network.addNode(2);
//        network.link(0, 1, new ShortestPathLabel(1));
//        network.link(1, 2, new ShortestPathLabel(1));
//        network.link(2, 0, new ShortestPathLabel(1));
//        return network;
//    }
//
//    private Map<Node, RouteTable> expectedRouteTableForNetwork2ForDestination0(Network network) {
//        Map<Node, RouteTable> expectedTables = new HashMap<>();
//        RouteTable routeTable;
//
//        /* node 0 route table
//            |   |
//            |:-:|
//        */
//        routeTable = createRouteTableForNode(network, 0);
//        expectedTables.put(network.getNode(0), routeTable);
//
//        /* node 1 route table
//            |   |     2     |
//            |:-:|:---------:|
//            | 0 | 2, [2, 0] |
//         */
//        routeTable = createRouteTableForNode(network, 1);
//        setRoute(routeTable, network, 0, 2, 2, new int[]{2, 0});
//        expectedTables.put(network.getNode(1), routeTable);
//
//        /* node 2 route table
//            |   |    0   |
//            |:-:|:------:|
//            | 0 | 1, [0] |
//         */
//        routeTable = createRouteTableForNode(network, 2);
//        setRoute(routeTable, network, 0, 0, 1, new int[]{0});
//        expectedTables.put(network.getNode(2), routeTable);
//
//        return expectedTables;
//    }
//
//    /**
//     * Creates the network1.
//     *  digraph network3 {
//     *      1 -> 0 [label=0];
//     *      2 -> 0 [label=0];
//     *      3 -> 0 [label=0];
//     *      1 -> 2 [label=-1];
//     *      2 -> 3 [label=1];
//     *      3 -> 1 [label=-2];
//     *  }
//     */
//    private Network createNetwork3() throws NodeExistsException, NodeNotFoundException {
//        Network network = new Network();
//        network.addNode(0);
//        network.addNode(1);
//        network.addNode(2);
//        network.addNode(3);
//        network.link(1, 0, new ShortestPathLabel(0));
//        network.link(2, 0, new ShortestPathLabel(0));
//        network.link(3, 0, new ShortestPathLabel(0));
//        network.link(1, 2, new ShortestPathLabel(-1));
//        network.link(2, 3, new ShortestPathLabel(1));
//        network.link(3, 1, new ShortestPathLabel(-2));
//        return network;
//    }
//
//    /**
//     * Creates the network4.
//     *  digraph network4 {
//     *      1 -> 0 [label=0];
//     *      1 -> 2 -> 3 -> 1 [label=1];
//     *  }
//     */
//    private Network createNetwork4() throws NodeExistsException, NodeNotFoundException {
//        Network network = new Network();
//        network.addNode(0);
//        network.addNode(1);
//        network.addNode(2);
//        network.addNode(3);
//        network.link(1, 0, new ShortestPathLabel(0));
//        network.link(1, 2, new ShortestPathLabel(1));
//        network.link(2, 3, new ShortestPathLabel(1));
//        network.link(3, 1, new ShortestPathLabel(1));
//        return network;
//    }
//
//    private Map<Node, RouteTable> expectedRouteTableForNetwork4ForDestination0(Network network) {
//        Map<Node, RouteTable> expectedTables = new HashMap<>();
//        RouteTable routeTable;
//
//        /* node 0 route table
//            |   |
//            |:-:|
//        */
//        routeTable = createRouteTableForNode(network, 0);
//        expectedTables.put(network.getNode(0), routeTable);
//
//        /* node 1 route table
//            |   |    0   | 2 |
//            |:-:|:------:|:-:|
//            | 0 | 0, [0] | • |
//         */
//        routeTable = createRouteTableForNode(network, 1);
//        setRoute(routeTable, network, 0, 0, 0, new int[]{0});
//        setInvalidRoute(routeTable, network, 0, 2);
//        expectedTables.put(network.getNode(1), routeTable);
//
//        /* node 2 route table
//            |   |       3      |
//            |:-:|:------------:|
//            | 0 | 2, [3, 1, 0] |
//         */
//        routeTable = createRouteTableForNode(network, 2);
//        setRoute(routeTable, network, 0, 3, 2, new int[]{3, 1, 0});
//        expectedTables.put(network.getNode(2), routeTable);
//
//        /* node 3 route table
//            |   |     1     |
//            |:-:|:---------:|
//            | 0 | 1, [1, 0] |
//         */
//        routeTable = createRouteTableForNode(network, 3);
//        setRoute(routeTable, network, 0, 1, 1, new int[]{1, 0});
//        expectedTables.put(network.getNode(3), routeTable);
//
//        return expectedTables;
//    }
}
