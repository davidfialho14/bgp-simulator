package simulation.networks;

import network.Network;
import network.Node;
import simulation.RouteTable;

import java.util.HashMap;
import java.util.Map;

public abstract class RouteTablesGenerator {

    protected Map<Node, RouteTable> routeTables = new HashMap<>();
    protected Network network;  // network associated with the nodes
    protected Integer onlyValidDestId = null;

    /**
     * Initiates the route tables for all nodes in the network.
     * @param network network to generate route tables for.
     */
    public RouteTablesGenerator(Network network, Integer onlyValidDestId) {
        for (Node node : network.getNodes()) {
            routeTables.put(node, new RouteTable(node.getOutNeighbours()));
        }
        this.network = network;
        this.onlyValidDestId = onlyValidDestId;
    }

    /**
     * Returns the route tables in the current state.
     * @return route tables in the current state.
     */
    public Map<Node, RouteTable> getTables() {
        return routeTables;
    }

    protected boolean isValidDestination(int destId) {
        return onlyValidDestId == null || destId == onlyValidDestId;
    }

    protected Node[] pathNodes(int[] path) {
        // create array of nodes for the path
        Node[] pathNodes = new Node[path.length];
        for (int i = 0; i < pathNodes.length; i++) {
            pathNodes[i] = new Node(path[i]);
        }

        return pathNodes;
    }
}
