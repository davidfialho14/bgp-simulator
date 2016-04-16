package simulation.networks.shortestpath;

import network.Network;
import simulation.PathAttribute;
import simulation.RouteTable;
import policies.implementations.shortestpath.ShortestPathAttribute;
import simulation.networks.RouteTablesGenerator;

import static policies.InvalidAttribute.invalid;

public class ShortestPathRouteTablesGenerator extends RouteTablesGenerator {

    public ShortestPathRouteTablesGenerator(Network network, Integer onlyValidDestId) {
        super(network, onlyValidDestId);
        this.onlyValidDestId = onlyValidDestId;
    }

    /**
     * Sets a route on the route table but only for the valid destination node. If the destination id is not equal to
     * the onlyValidDestId then the route is not set.
     * @param nodeId id of the node with the route table to set route for.
     * @param destId  id of the destination node to associated with the route.
     * @param neighbourId id of the neighbour node to associated with the route.
     * @param length length attribute to be set.
     * @param path path attribute to be set. array containing the ids of hte nodes in the path.
     */
    public void setRoute(int nodeId, int destId, int neighbourId, int length, int[] path) {
        if (isValidDestination(destId)) {    // set route only for valid destination node
            RouteTable routeTable = routeTables.get(network.getNode(nodeId));

            routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId),
                    new ShortestPathAttribute(length));
            routeTable.setPath(network.getNode(destId), network.getNode(neighbourId),
                    new PathAttribute(pathNodes(path)));
        }
    }

    /**
     * Sets an invalid route for the given route table in the pair (destination, neighbour) given.
     * @param nodeId id of the node with the route table to set invalid route for.
     * @param destId id of the destination node to associated with invalid route.
     * @param neighbourId id of the neighbour node to associated with invalid route.
     */
    protected void setInvalidRoute(int nodeId, int destId, int neighbourId) {
        if (isValidDestination(destId)) {    // set route only for valid destination node
            RouteTable routeTable = routeTables.get(network.getNode(nodeId));

            routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId), invalid());

            routeTable.setPath(network.getNode(destId), network.getNode(neighbourId),
                    PathAttribute.createInvalidPath());
        }
    }
}
