package simulation.networks.gaorexford;

import network.Network;
import simulation.PathAttribute;
import simulation.RouteTable;
import policies.implementations.gaorexford.GaoRexfordAttribute;
import policies.implementations.gaorexford.GaoRexfordAttributeFactory;
import simulation.networks.RouteTablesGenerator;

import static policies.implementations.gaorexford.GaoRexfordAttribute.Type.INVALID;

public class GaoRexfordRouteTablesGenerator extends RouteTablesGenerator {

    public GaoRexfordRouteTablesGenerator(Network network, Integer onlyValidDestId) {
        super(network, onlyValidDestId, new GaoRexfordAttributeFactory());
    }

    /**
     * Sets a route on the route table but only for the valid destination node. If the destination id is not equal to
     * the onlyValidDestId then the route is not set.
     * @param nodeId id of the node with the route table to set route for.
     * @param destId  id of the destination node to associated with the route.
     * @param neighbourId id of the neighbour node to associated with the route.
     * @param type type of attribute to be set.
     * @param path path attribute to be set. array containing the ids of hte nodes in the path.
     */
    public void setRoute(int nodeId, int destId, int neighbourId, GaoRexfordAttribute.Type type, int[] path) {
        if (isValidDestination(destId)) {    // set route only for valid destination node
            RouteTable routeTable = routeTables.get(network.getNode(nodeId));

            routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId),
                    new GaoRexfordAttribute(type));
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

            GaoRexfordAttribute invalidAttribute = new GaoRexfordAttribute(INVALID);
            routeTable.setAttribute(network.getNode(destId), network.getNode(neighbourId), invalidAttribute);

            routeTable.setPath(network.getNode(destId), network.getNode(neighbourId),
                    PathAttribute.createInvalidPath());
        }
    }
}
