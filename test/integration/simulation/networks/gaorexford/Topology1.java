package simulation.networks.gaorexford;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import policies.implementations.gaorexford.CustomerLabel;
import policies.implementations.gaorexford.ProviderLabel;
import simulation.RouteTable;
import simulation.networks.RouteTablesGenerator;
import simulation.networks.Topology;

import java.util.Map;

import static policies.implementations.gaorexford.CustomerAttribute.customer;
import static policies.implementations.gaorexford.ProviderAttribute.provider;

/**
 * Topology description:
 *
 *  digraph topology1 {
 *      0 -> 1 [label=c];
 *      1 -> 0 [label=p];
 *      2 -> 1 [label=c];
 *      1 -> 2 [label=p];
 *  }
 */
public class Topology1 extends Topology {

    /**
     * Constructs the network topology according to the topology description
     */
    public Topology1() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.link(0, 1, new CustomerLabel());
        network.link(1, 0, new ProviderLabel());
        network.link(2, 1, new CustomerLabel());
        network.link(1, 2, new ProviderLabel());
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTablesForBGP(Integer destId) {
        RouteTablesGenerator generator = new RouteTablesGenerator(network.getNodes(), destId);

        /* node 0 route table
            |   |  1, C  |
            |:-:|:------:|
            | 1 | c, [1] |
            | 2 |    •   |
         */
        generator.setRoute(0, 1, new CustomerLabel(), 1, customer(), new int[]{1});
        generator.setInvalidRoute(0, 1, new CustomerLabel(), 2);

        /* node 1 route table
            |   |  0, P  |  2, P  |
            |:-:|:------:|:------:|
            | 0 | p, [0] |    •   |
            | 2 |    •   | p, [2] |
         */
        generator.setRoute(1, 0, new ProviderLabel(), 0, provider(), new int[]{0});
        generator.setInvalidRoute(1, 0, new ProviderLabel(), 2);
        generator.setInvalidRoute(1, 2, new ProviderLabel(), 0);
        generator.setRoute(1, 2, new ProviderLabel(), 2, provider(), new int[]{2});

        /* node 2 route table
            |   |  1, C  |
            |:-:|:------:|
            | 0 |    •   |
            | 1 | c, [1] |
         */
        generator.setInvalidRoute(2, 1, new CustomerLabel(), 0);
        generator.setRoute(2, 1, new CustomerLabel(), 1, customer(), new int[]{1});

        return generator.getTables();
    }
}
