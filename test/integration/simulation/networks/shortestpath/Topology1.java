package simulation.networks.shortestpath;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import simulation.implementations.policies.shortestpath.ShortestPathLabel;
import simulation.networks.Topology;

import java.util.Map;

public class Topology1 extends Topology {

    /**
     * Creates the network1.
     *  digraph network1 {
     *      0 -> 1 -> 2 [label=1];
     *      0 -> 2 [label=0];
     *  }
     */
    public Topology1() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.link(0, 1, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(0, 2, new ShortestPathLabel(0));
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTables(Integer destId) {
        ShortestPathRouteTablesGenerator generator = new ShortestPathRouteTablesGenerator(network, destId);

        /* node 0 route table
            |   |     1     |    2   |
            |:-:|:---------:|:------:|
            | 1 |   1, [1]  |    •   |
            | 2 | 2, [1, 2] | 0, [2] |
        */
        generator.setRoute(0, 1, 1, 1, new int[]{1});
        generator.setInvalidRoute(0, 1, 2);
        generator.setRoute(0, 2, 1, 2, new int[]{1, 2});
        generator.setRoute(0, 2, 2, 0, new int[]{2});

        /* node 1 route table
            |   |    2   |
            |:-:|:------:|
            | 2 | 1, [2] |
         */
        generator.setRoute(1, 2, 2, 1, new int[]{2});

        /* node 2 route table
            |   |
            |:-:|
         */

        return generator.getTables();
    }
}
