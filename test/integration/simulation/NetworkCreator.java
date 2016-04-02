package simulation;

import network.Network;
import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.implementations.policies.shortestpath.ShortestPathAttribute;
import simulation.implementations.policies.shortestpath.ShortestPathAttributeFactory;
import simulation.implementations.policies.shortestpath.ShortestPathLabel;

import java.util.HashMap;
import java.util.Map;

public class NetworkCreator {

    private NetworkCreator() {} // should not be instantiated

    private static RouteTable createRouteTableForNode(Network network, int nodeId) {
        return new RouteTable(network.getNode(nodeId).getOutNeighbours(), new ShortestPathAttributeFactory());
    }

    private static void setRoute(RouteTable routeTable, Network network, int destId, int neighbourId,
                                 int cost, int[] path) {
        routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId),
                new ShortestPathAttribute(cost));

        // create array of nodes for the path
        Node[] pathNodes = new Node[path.length];
        for (int i = 0; i < pathNodes.length; i++) {
            pathNodes[i] = new Node(network, path[i]);
        }

        routeTable.setPath(network.getNode(destId), network.getNode(neighbourId), new PathAttribute(pathNodes));
    }

    private static void setInvalidRoute(RouteTable routeTable, Network network, int destId, int neighbourId) {
        ShortestPathAttribute invalidAttribute = ShortestPathAttribute.createInvalidShortestPath();
        routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId), invalidAttribute);
        routeTable.setPath(network.getNode(destId), network.getNode(neighbourId), PathAttribute.createInvalidPath());
    }

    static Network createNetwork0() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.link(0, 1, new ShortestPathLabel(1));
        return network;
    }

    static Map<Node, RouteTable> expectedRouteTableForNetwork0(Network network) {
        Map<Node, RouteTable> expectedTables = new HashMap<>();
        RouteTable routeTable;

        /* node 0 route table
            --------------
           |   | 1        |
           |---|----------|
           | 1 | (1, [1]) |
            --------------
        */
        routeTable = createRouteTableForNode(network, 0);
        setRoute(routeTable, network, 1, 1, 1, new int[]{1});
        expectedTables.put(network.getNode(0), routeTable);

        /* node 1 route table
            -
           | |
            -
         */
        routeTable = createRouteTableForNode(network, 1);
        expectedTables.put(network.getNode(1), routeTable);

        return expectedTables;
    }

    static Network createNetwork1() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.link(0, 1, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(0, 2, new ShortestPathLabel(0));
        return network;
    }

    static Map<Node, RouteTable> expectedRouteTableForNetwork1(Network network) {
        Map<Node, RouteTable> expectedTables = new HashMap<>();
        RouteTable routeTable;

        /* node 0 route table
            ----------------------------
           |   | 1           | 2        |
           |---|-------------|----------|
           | 1 | (1, [1])    |  •       |
           | 2 | (2, [1, 2]) | (0, [2]) |
            ----------------------------
        */
        routeTable = createRouteTableForNode(network, 0);
        setRoute(routeTable, network, 1, 1, 1, new int[]{1});
        setInvalidRoute(routeTable, network, 1, 2);
        setRoute(routeTable, network, 2, 1, 2, new int[]{1, 2});
        setRoute(routeTable, network, 2, 2, 0, new int[]{2});
        expectedTables.put(network.getNode(0), routeTable);

        /* node 1 route table
            --------------
           |   | 2        |
           |---|----------|
           | 2 | (1, [2]) |
            --------------
         */
        routeTable = createRouteTableForNode(network, 1);
        setRoute(routeTable, network, 2, 2, 1, new int[]{2});
        expectedTables.put(network.getNode(1), routeTable);

        /* node 2 route table
            -
           | |
            -
         */
        routeTable = createRouteTableForNode(network, 2);
        expectedTables.put(network.getNode(2), routeTable);

        return expectedTables;
    }

    static Network createNetwork2() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.link(0, 1, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(2, 0, new ShortestPathLabel(1));
        return network;
    }

    static Map<Node, RouteTable> expectedRouteTableForNetwork2ForDestination0(Network network) {
        Map<Node, RouteTable> expectedTables = new HashMap<>();
        RouteTable routeTable;

        /* node 0 route table
            -------
           |   | 1 |
           |---|---|
            -------
        */
        routeTable = createRouteTableForNode(network, 0);
        expectedTables.put(network.getNode(0), routeTable);

        /* node 1 route table
            -----------------
           |   | 2           |
           |---|-------------|
           | 0 | (2, [2, 0]) |
            -----------------
         */
        routeTable = createRouteTableForNode(network, 1);
        setRoute(routeTable, network, 0, 2, 2, new int[]{2, 0});
        expectedTables.put(network.getNode(1), routeTable);

        /* node 1 route table
            --------------
           |   | 0        |
           |---|----------|
           | 0 | (1, [0]) |
            --------------
         */
        routeTable = createRouteTableForNode(network, 2);
        setRoute(routeTable, network, 0, 0, 1, new int[]{0});
        expectedTables.put(network.getNode(2), routeTable);

        return expectedTables;
    }

    static Network createNetwork3() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.addNode(3);
        network.link(1, 0, new ShortestPathLabel(0));
        network.link(2, 0, new ShortestPathLabel(0));
        network.link(3, 0, new ShortestPathLabel(0));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(-1));
        network.link(2, 3, new ShortestPathLabel(1));
        network.link(3, 1, new ShortestPathLabel(-2));
        return network;
    }
}
