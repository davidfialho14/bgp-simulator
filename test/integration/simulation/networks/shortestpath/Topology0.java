package simulation.networks.shortestpath;

import network.Network;
import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import simulation.networks.Topology;
import simulation.implementations.policies.shortestpath.ShortestPathLabel;

import java.util.HashMap;
import java.util.Map;

public class Topology0 extends Topology {

    /**
     * Initiates a network with topology 0.
     * @throws NodeExistsException
     * @throws NodeNotFoundException
     */
    public Topology0() throws NodeExistsException, NodeNotFoundException {
        network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.link(0, 1, new ShortestPathLabel(1));
    }

    /**
     * Returns a network instance initialized as network 0. Returns always the same instance.
     *  digraph network0 {
     *      0 -> 1 [label=1];
     *  }
     */
    @Override
    public Network getNetwork() {
        return network;
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTables() {
        Map<Node, RouteTable> expectedTables = new HashMap<>();
        RouteTable routeTable;

        /* node 0 route table
            |   |     1    |
            |:-:|:--------:|
            | 1 | (1, [1]) |
         */
        routeTable = createRouteTableForNode(network, 0);
        setRoute(routeTable, network, 1, 1, 1, new int[]{1});
        expectedTables.put(network.getNode(0), routeTable);

        /* node 1 route table
            |   |
            |:-:|
         */
        routeTable = createRouteTableForNode(network, 1);
        expectedTables.put(network.getNode(1), routeTable);

        return expectedTables;
    }
}
