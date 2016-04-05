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

    public Map<Node, RouteTable> getExpectedRouteTablesForD1R1() {
        return getExpectedRouteTablesForD1R1(null);
    }

    public Map<Node, RouteTable> getExpectedRouteTablesForD1R1(Integer destId) {
        // this operation is optional
        throw  new UnsupportedOperationException();
    }

}
