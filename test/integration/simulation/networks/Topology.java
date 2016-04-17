package simulation.networks;

import network.Network;
import network.Node;
import simulation.RouteTable;

import java.util.Map;

/**
 * Models any topology.
 */
public abstract class Topology {

    protected Network network = new Network();

    /**
     * Returns the network of the topology.
     * @return network of the topology.
     */
    public Network getNetwork() {
        return network;
    }

    /**
     * Returns the expected route tables for all nodes when the topology is simulated using the BGP protocol.
     * @return expected route tables for all nodes when the topology is simulated using the BGP protocol.
     */
    public Map<Node, RouteTable> getExpectedRouteTablesForBGP() {
        return getExpectedRouteTablesForBGP(null);
    }

    /**
     * Returns the expected route tables only for one destination when the topology is simulated using the BGP protocol.
     * @param destId id of the only destination node to get the route tables for.
     * @return expected route tables only for one destination when the topology is simulated using the BGP protocol.
     */
    abstract public Map<Node, RouteTable> getExpectedRouteTablesForBGP(Integer destId);

    public Map<Node, RouteTable> getExpectedRouteTablesForD1R1() {
        return getExpectedRouteTablesForD1R1(null);
    }

    public Map<Node, RouteTable> getExpectedRouteTablesForD1R1(Integer destId) {
        // this operation is optional
        throw  new UnsupportedOperationException();
    }

}
