package simulation.networks.shortestpath;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import policies.implementations.shortestpath.ShortestPathLabel;
import simulation.networks.Topology;

import java.util.Map;

public class Topology4 extends Topology {

    /**
     * Creates the network4.
     *  digraph network4 {
     *      1 -> 0 [label=0];
     *      1 -> 2 -> 3 -> 1 [label=1];
     *  }
     */
    public Topology4() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.addNode(3);
        network.link(1, 0, new ShortestPathLabel(0));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(2, 3, new ShortestPathLabel(1));
        network.link(3, 1, new ShortestPathLabel(1));
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTables(Integer destId) {
        ShortestPathRouteTablesGenerator generator = new ShortestPathRouteTablesGenerator(network, destId);

        /* node 0 route table
            |   |
            |:-:|
        */

        /* node 1 route table
            |   |    0   | 2 |
            |:-:|:------:|:-:|
            | 0 | 0, [0] | • |
         */
        generator.setRoute(1, 0, 0, 0, new int[]{0});
        generator.setInvalidRoute(1, 0, 2);

        /* node 2 route table
            |   |       3      |
            |:-:|:------------:|
            | 0 | 2, [3, 1, 0] |
         */
        generator.setRoute(2, 0, 3, 2, new int[]{3, 1, 0});

        /* node 3 route table
            |   |     1     |
            |:-:|:---------:|
            | 0 | 1, [1, 0] |
         */
        generator.setRoute(3, 0, 1, 1, new int[]{1, 0});

        return generator.getTables();
    }
}
