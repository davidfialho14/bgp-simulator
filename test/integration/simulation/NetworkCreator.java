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

    static Network createNetwork0() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.link(0, 1, new ShortestPathLabel(1));
        return network;
    }

    static Map<Node, RouteTable> expectedRouteTableForNetwork0(Network network) {
        Map<Node, RouteTable> expectedTables = new HashMap<>();

        // node 0 route table
        RouteTable routeTable = new RouteTable(network.getNode(0).getOutNeighbours(),
                new ShortestPathAttributeFactory());
        routeTable.setAttribute(network.getNode(1), network.getNode(1), new ShortestPathAttribute(1));
        routeTable.setPath(network.getNode(1), network.getNode(1), new PathAttribute(network.getNode(1)));
        expectedTables.put(network.getNode(0), routeTable);

        // node 1 route table
        routeTable = new RouteTable(network.getNode(1).getOutNeighbours(), new ShortestPathAttributeFactory());
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

    static Network createNetwork2() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.addNode(3);
        network.addNode(4);
        network.addNode(5);
        network.link(0, 1, new ShortestPathLabel(1));
        network.link(0, 2, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(2, 3, new ShortestPathLabel(0));
        network.link(2, 4, new ShortestPathLabel(-1));
        network.link(3, 5, new ShortestPathLabel(0));
        network.link(4, 5, new ShortestPathLabel(3));
        return network;
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
