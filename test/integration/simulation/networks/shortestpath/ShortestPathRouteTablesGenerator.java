package simulation.networks.shortestpath;

import network.Network;
import network.Node;
import simulation.PathAttribute;
import simulation.RouteTable;
import simulation.implementations.policies.shortestpath.ShortestPathAttribute;
import simulation.implementations.policies.shortestpath.ShortestPathAttributeFactory;
import simulation.networks.RouteTablesGenerator;

public class ShortestPathRouteTablesGenerator extends RouteTablesGenerator {

    public ShortestPathRouteTablesGenerator(Network network) {
        super(network, new ShortestPathAttributeFactory());
    }

    /**
     * Sets a route on the route table.
     * @param nodeId id of the node with the route table to set route for.
     * @param destId  id of the destination node to associated with the route.
     * @param neighbourId id of the neighbour node to associated with the route.
     * @param length length attribute to be set.
     * @param path path attribute to be set. array containing the ids of hte nodes in the path.
     */
    public void setRoute(int nodeId, int destId, int neighbourId, int length, int[] path) {
        RouteTable routeTable = routeTables.get(network.getNode(nodeId));

        routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId),
                new ShortestPathAttribute(length));

        // create array of nodes for the path
        Node[] pathNodes = new Node[path.length];
        for (int i = 0; i < pathNodes.length; i++) {
            pathNodes[i] = new Node(network, path[i]);
        }

        routeTable.setPath(network.getNode(destId), network.getNode(neighbourId), new PathAttribute(pathNodes));
    }

    /**
     * Sets an invalid route for the given route table in the pair (destination, neighbour) given.
     * @param nodeId id of the node with the route table to set invalid route for.
     * @param destId id of the destination node to associated with invalid route.
     * @param neighbourId id of the neighbour node to associated with invalid route.
     */
    protected void setInvalidRoute(int nodeId, int destId, int neighbourId) {
        RouteTable routeTable = routeTables.get(network.getNode(nodeId));
        ShortestPathAttribute invalidAttribute = ShortestPathAttribute.createInvalidShortestPath();
        routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId), invalidAttribute);
        routeTable.setPath(network.getNode(destId), network.getNode(neighbourId), PathAttribute.createInvalidPath());
    }
}
