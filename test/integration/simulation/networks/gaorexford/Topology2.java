package simulation.networks.gaorexford;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import policies.implementations.gaorexford.CustomerLabel;
import simulation.RouteTable;
import simulation.networks.RouteTablesGenerator;
import simulation.networks.Topology;

import java.util.Map;

import static policies.implementations.gaorexford.CustomerAttribute.customer;

/**
 * Topology description:
 *
 *  digraph topology2 {
 *      0 -> 1 -> 2 -> 0 [label=c];
 *  }
 */
public class Topology2 extends Topology {

    /**
     * Constructs the network topology according to the topology description
     */
    public Topology2() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.link(0, 1, new CustomerLabel());
        network.link(1, 2, new CustomerLabel());
        network.link(2, 0, new CustomerLabel());
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTablesForBGP(Integer destId) {
        RouteTablesGenerator generator = new RouteTablesGenerator(network.getNodes(), destId);

        /* node 0 route table
            |   |   1, C    |
            |:-:|:---------:|
            | 1 |   c, [1]  |
            | 2 | c, [2, 1] |
         */
        generator.setRoute(0, 1, new CustomerLabel(), 1, customer(), new int[]{1});
        generator.setRoute(0, 1, new CustomerLabel(), 2, customer(), new int[]{2, 1});

        /* node 1 route table
            |   |   2, C    |
            |:-:|:---------:|
            | 0 | c, [0, 2] |
            | 2 |   c, [2]  |
         */
        generator.setRoute(1, 2, new CustomerLabel(), 0, customer(), new int[]{0, 2});
        generator.setRoute(1, 2, new CustomerLabel(), 2, customer(), new int[]{2});

        /* node 2 route table
            |   |   0, C    |
            |:-:|:---------:|
            | 0 |   c, [0]  |
            | 1 | c, [1, 0] |
         */
        generator.setRoute(2, 0, new CustomerLabel(), 0, customer(), new int[]{0});
        generator.setRoute(2, 0, new CustomerLabel(), 1, customer(), new int[]{1, 0});

        return generator.getTables();
    }
}
