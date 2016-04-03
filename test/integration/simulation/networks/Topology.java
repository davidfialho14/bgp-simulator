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

}
