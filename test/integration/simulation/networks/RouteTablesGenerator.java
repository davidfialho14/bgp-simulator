package simulation.networks;

import network.Network;
import network.Node;
import simulation.AttributeFactory;
import simulation.RouteTable;

import java.util.HashMap;
import java.util.Map;

public abstract class RouteTablesGenerator {

    protected Map<Node, RouteTable> routeTables = new HashMap<>();
    protected Network network;  // network associated with the nodes

    /**
     * Initiates the route tables for all nodes in the network.
     * @param network network to generate route tables for.
     * @param attributeFactory attribute factory to create the route table.
     */
    public RouteTablesGenerator(Network network, AttributeFactory attributeFactory) {
        for (Node node : network.getNodes()) {
            routeTables.put(node, new RouteTable(node.getOutNeighbours(), attributeFactory));
        }
        this.network = network;
    }

    /**
     * Returns the route tables in the current state.
     * @return route tables in the current state.
     */
    public Map<Node, RouteTable> getTables() {
        return routeTables;
    }
}
