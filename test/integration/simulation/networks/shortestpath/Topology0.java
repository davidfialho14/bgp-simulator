package simulation.networks.shortestpath;

import network.Network;
import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import simulation.implementations.policies.shortestpath.ShortestPathLabel;
import simulation.networks.Topology;

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

        routeTablesGenerator = new ShortestPathRouteTablesGenerator(network);
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
        /* node 0 route table
            |   |     1    |
            |:-:|:--------:|
            | 1 | (1, [1]) |
         */
        routeTablesGenerator.setRoute(0, 1, 1, 1, new int[]{1});

        /* node 1 route table
            |   |
            |:-:|
         */

        return routeTablesGenerator.getTables();
    }
}
