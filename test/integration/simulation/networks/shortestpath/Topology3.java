package simulation.networks.shortestpath;

import network.Node;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.RouteTable;
import simulation.implementations.policies.shortestpath.ShortestPathLabel;
import simulation.networks.Topology;

import java.util.Map;

public class Topology3 extends Topology {

    /**
     * Creates the network1.
     *  digraph network3 {
     *      1 -> 0 [label=0];
     *      2 -> 0 [label=0];
     *      3 -> 0 [label=0];
     *      1 -> 2 [label=-1];
     *      2 -> 3 [label=1];
     *      3 -> 1 [label=-2];
     *  }
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
    public Map<Node, RouteTable> getExpectedRouteTables(Integer destId) {
        // this topology does not converge so it can not have an expected route tables
        throw new UnsupportedOperationException();
    }
}
