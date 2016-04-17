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
 *  digraph network3 {
 *      1 -> 0 [label=0];
 *      2 -> 0 [label=0];
 *      3 -> 0 [label=0];
 *      1 -> 2 [label=-1];
 *      2 -> 3 [label=1];
 *      3 -> 1 [label=-2];
 *  }
 */
public class Topology3 extends ShortestPathTopology {

    /**
     * Constructs the network topology according to the topology description
     */
    public Topology3() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.addNode(3);
        network.link(1, 0, new ShortestPathLabel(0));
        network.link(2, 0, new ShortestPathLabel(0));
        network.link(3, 0, new ShortestPathLabel(0));
        network.link(1, 2, new ShortestPathLabel(-1));
        network.link(2, 3, new ShortestPathLabel(1));
        network.link(3, 1, new ShortestPathLabel(-2));
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTablesForBGP(Integer destId) {
        // this topology does not converge so it can not have an expected route tables
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTablesForD1R1(Integer destId) {
        RouteTablesGenerator generator = new RouteTablesGenerator(network.getNodes(), destId);

        /* node 0 route table
            |   |
            |:-:|
        */

        /* node 1 route table
            |   |  0, 0  | 2, -1 |
            |:-:|:------:|:-----:|
            | 0 | 0, [0] |   •   |
         */
        generator.setRoute(1, 0, label(0), 0, attribute(0), new int[]{0});
        generator.setInvalidRoute(1, 2, label(-1), 0);

        /* node 2 route table
            |   |  0, 0  | 3, 1 |
            |:-:|:------:|:----:|
            | 0 | 0, [0] |  •   |
         */
        generator.setRoute(2, 0, label(0), 0, attribute(0), new int[]{0});
        generator.setInvalidRoute(2, 3, label(1), 0);

        /* node 3 route table
            |   |  0, 0  |    1, -2   |
            |:-:|:------:|:----------:|
            | 0 | 0, [0] | -2, [1, 0] |
         */
        generator.setRoute(3, 0, label(0), 0, attribute(0), new int[]{0});
        generator.setRoute(3, 1, label(-2), 0, attribute(-2), new int[]{1, 0});

        return generator.getTables();
    }
}
