package simulation.networks.gaorexford;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import policies.implementations.gaorexford.CustomerLabel;
import simulation.networks.Topology;

import java.util.Map;

import static policies.implementations.gaorexford.GaoRexfordAttribute.Type.CUSTOMER;

public class Topology2 extends Topology {

    /**
     * Creates the topology 0.
     *  digraph topology0 {
     *      0 -> 1 -> 2 -> 0 [label=c];
     *  }
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
    public Map<Node, RouteTable> getExpectedRouteTables(Integer destId) {
        GaoRexfordRouteTablesGenerator generator = new GaoRexfordRouteTablesGenerator(network, destId);

        /* node 0 route table
            |   |     1     |
            |:-:|:---------:|
            | 1 |   c, [1]  |
            | 2 | c, [2, 1] |
         */
        generator.setRoute(0, 1, 1, CUSTOMER, new int[]{1});
        generator.setRoute(0, 2, 1, CUSTOMER, new int[]{2, 1});

        /* node 1 route table
            |   |     2     |
            |:-:|:---------:|
            | 0 | c, [0, 2] |
            | 2 |   c, [2]  |
         */
        generator.setRoute(1, 0, 2, CUSTOMER, new int[]{0, 2});
        generator.setRoute(1, 2, 2, CUSTOMER, new int[]{2});

        /* node 2 route table
            |   |     0     |
            |:-:|:---------:|
            | 0 |   c, [0]  |
            | 1 | c, [1, 0] |
         */
        generator.setRoute(2, 0, 0, CUSTOMER, new int[]{0});
        generator.setRoute(2, 1, 0, CUSTOMER, new int[]{1, 0});

        return generator.getTables();
    }
}
