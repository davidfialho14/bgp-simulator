package simulation.networks.gaorexford;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import policies.implementations.gaorexford.CustomerLabel;
import policies.implementations.gaorexford.ProviderLabel;
import simulation.networks.Topology;

import java.util.Map;

import static policies.implementations.gaorexford.CustomerAttribute.customer;
import static policies.implementations.gaorexford.ProviderAttribute.provider;

public class Topology0 extends Topology {

    /**
     * Creates the topology 0.
     *  digraph topology0 {
     *      0 -> 1 [label=c];
     *      1 -> 0 [label=p];
     *  }
     */
    public Topology0() throws NodeExistsException, NodeNotFoundException {
        network.addNode(0);
        network.addNode(1);
        network.link(0, 1, new CustomerLabel());
        network.link(1, 0, new ProviderLabel());
    }

    @Override
    public Map<Node, RouteTable> getExpectedRouteTablesForBGP(Integer destId) {
        GaoRexfordRouteTablesGenerator generator = new GaoRexfordRouteTablesGenerator(network, destId);

        /* node 0 route table
            |   |     1    |
            |:-:|:--------:|
            | 1 | (c, [1]) |
         */
        generator.setRoute(0, 1, 1, customer(), new int[]{1});

        /* node 1 route table
            |   |     0    |
            |:-:|:--------:|
            | 0 | (p, [0]) |
         */
        generator.setRoute(1, 0, 0, provider(), new int[]{0});

        return generator.getTables();
    }
}
