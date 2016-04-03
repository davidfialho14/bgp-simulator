package simulation.networks.shortestpath;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import simulation.implementations.policies.shortestpath.ShortestPathLabel;
import simulation.networks.Topology;

import java.util.Map;

public class Topology2 extends Topology {

    /**
     * Creates the network2.
     *  digraph network2 {
     *      0 -> 1 -> 2 [label=1];
     *      2 -> 0 [label=1];
     *  }
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
    public Map<Node, RouteTable> getExpectedRouteTables(Integer destId) {
        ShortestPathRouteTablesGenerator generator = new ShortestPathRouteTablesGenerator(network, destId);

        /* node 0 route table
            |   |
            |:-:|
        */

        /* node 1 route table
            |   |     2     |
            |:-:|:---------:|
            | 0 | 2, [2, 0] |
         */
        generator.setRoute(1, 0, 2, 2, new int[]{2, 0});

        /* node 2 route table
            |   |    0   |
            |:-:|:------:|
            | 0 | 1, [0] |
         */
        generator.setRoute(2, 0, 0, 1, new int[]{0});

        return generator.getTables();
    }
}
