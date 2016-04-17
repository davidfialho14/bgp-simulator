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
 *  digraph network2 {
 *      0 -> 1 -> 2 [label=1];
 *      2 -> 0 [label=1];
 *  }
 */
public class Topology2 extends ShortestPathTopology {

    /**
     * Constructs the network topology according to the topology description
     */
    public Topology2() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.link(0, 1, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(2, 0, new ShortestPathLabel(1));
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTablesForBGP(Integer destId) {
        RouteTablesGenerator generator = new RouteTablesGenerator(network.getNodes(), destId);

        /* node 0 route table
            |   |
            |:-:|
        */

        /* node 1 route table
            |   |    2, 1    |
            |:-:|:----------:|
            | 0 |  2, [2, 0] |
         */
        generator.setRoute(1, 2, label(1), 0, attribute(2), new int[]{2, 0});

        /* node 2 route table
            |   |   0, 1  |
            |:-:|:-------:|
            | 0 |  1, [0] |
         */
        generator.setRoute(2, 0, label(1), 0, attribute(1), new int[]{0});

        return generator.getTables();
    }
}
