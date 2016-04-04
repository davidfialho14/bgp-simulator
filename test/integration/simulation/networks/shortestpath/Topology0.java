package simulation.networks.shortestpath;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import policies.implementations.shortestpath.ShortestPathLabel;
import simulation.networks.Topology;

import java.util.Map;

public class Topology0 extends Topology {

    /**
     * Creates the network1.
     *  digraph network0 {
     *      0 -> 1 [label=1];
     *  }
     */
    public Topology0() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.link(0, 1, new ShortestPathLabel(1));
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTables(Integer destId) {
        ShortestPathRouteTablesGenerator routeTablesGenerator = new ShortestPathRouteTablesGenerator(network, destId);

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
