package simulation.networks.shortestpath;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import policies.implementations.shortestpath.ShortestPathLabel;
import simulation.RouteTable;
import simulation.networks.RouteTablesGenerator;

import java.util.Map;

/**
 * Topology description:
 *
 *  digraph network0 {
 *      0 -> 1 [label=1];
 *  }
 */
public class Topology0 extends ShortestPathTopology {

    /**
     * Constructs the network topology according to the topology description
     */
    public Topology0() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.link(0, 1, new ShortestPathLabel(1));
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTablesForBGP(Integer destId) {
        RouteTablesGenerator generator = new RouteTablesGenerator(network.getNodes(), destId);

        /* node 0 route table
            |   |     1    |
            |:-:|:--------:|
            | 1 | (1, [1]) |
         */
        generator.setRoute(0, 1, label(1), 1, attribute(1), new int[]{1});

        /* node 1 route table
            |   |
            |:-:|
         */

        return generator.getTables();
    }

}
