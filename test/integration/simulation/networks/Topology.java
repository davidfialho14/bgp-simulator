package simulation.networks;

import network.Network;
import network.Node;
import simulation.RouteTable;

import java.util.Map;

public abstract class Topology {

    protected Network network = new Network();

    public Network getNetwork() {
        return network;
    }

    public Map<Node, RouteTable> getExpectedRouteTables() {
        return getExpectedRouteTables(null);
    }

    abstract public Map<Node, RouteTable> getExpectedRouteTables(Integer destId);


//    /**
//     * Creates the network1.
//     *  digraph network3 {
//     *      1 -> 0 [label=0];
//     *      2 -> 0 [label=0];
//     *      3 -> 0 [label=0];
//     *      1 -> 2 [label=-1];
//     *      2 -> 3 [label=1];
//     *      3 -> 1 [label=-2];
//     *  }
//     */
//    private Network createNetwork3() throws NodeExistsException, NodeNotFoundException {
//        Network network = new Network();
//        network.addNode(0);
//        network.addNode(1);
//        network.addNode(2);
//        network.addNode(3);
//        network.link(1, 0, new ShortestPathLabel(0));
//        network.link(2, 0, new ShortestPathLabel(0));
//        network.link(3, 0, new ShortestPathLabel(0));
//        network.link(1, 2, new ShortestPathLabel(-1));
//        network.link(2, 3, new ShortestPathLabel(1));
//        network.link(3, 1, new ShortestPathLabel(-2));
//        return network;
//    }
}
