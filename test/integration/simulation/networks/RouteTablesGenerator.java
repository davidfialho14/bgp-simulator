package simulation.networks;

import network.Link;
import network.Node;
import policies.Attribute;
import policies.Label;
import simulation.PathAttribute;
import simulation.RouteTable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static policies.InvalidAttribute.invalid;

public class RouteTablesGenerator {

    protected Map<Node, RouteTable> routeTables = new HashMap<>();
    protected Integer onlyValidDestId = null;

    // ----- PUBLIC INTERFACE ---------------------------------------------------------------------------------------

    /**
     * Initiates the route tables for all nodes in the network.
     * @param nodes all nodes in the network
     */
    public RouteTablesGenerator(Collection<Node> nodes, Integer onlyValidDestId) {
        nodes.forEach(node -> routeTables.put(node, new RouteTable(node.getOutLinks())));
        this.onlyValidDestId = onlyValidDestId;
    }

    /**
     * Returns the route tables in the current state.
     * @return route tables in the current state.
     */
    public Map<Node, RouteTable> getTables() {
        return routeTables;
    }

    /**
     * Sets a route on the route table but only for the valid destination node. If the destination id is not equal to
     * the onlyValidDestId then the route is not set.
     */
    public void setRoute(int nodeId, int neighbourId, Label label, int destId, Attribute attribute, int[] path) {
        if (isValidDestination(destId)) {    // set route only for valid destination node
            Link outLink = new Link(nodeId, neighbourId, label);
            Node destination = new Node(destId);
            RouteTable routeTable = routeTables.get(new Node(nodeId));

            routeTable.setAttribute(destination, outLink, attribute);
            routeTable.setPath(destination, outLink, toPath(path));
        }
    }

    /**
     * Sets an invalid route for the given route table in the pair (destination, neighbour) given.
     * @param nodeId id of the node with the route table to set invalid route for.
     * @param destId id of the destination node to associated with invalid route.
     * @param neighbourId id of the neighbour node to associated with invalid route.
     */
    public void setInvalidRoute(int nodeId, int neighbourId, Label label, int destId) {
        setRoute(nodeId, neighbourId, label, destId, invalid(), null);
    }

    // ----- END PUBLIC INTERFACE -----------------------------------------------------------------------------------

    protected boolean isValidDestination(int destId) {
        return onlyValidDestId == null || destId == onlyValidDestId;
    }

    protected PathAttribute toPath(int[] path) {
        if (path == null) {
            return PathAttribute.invalidPath();
        }

        // create array of nodes for the path
        Node[] pathNodes = new Node[path.length];
        for (int i = 0; i < pathNodes.length; i++) {
            pathNodes[i] = new Node(path[i]);
        }

        return new PathAttribute(pathNodes);
    }
}
