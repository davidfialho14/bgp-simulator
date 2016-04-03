package simulation.networks.gaorexford;

import network.Network;
import network.Node;
import simulation.PathAttribute;
import simulation.RouteTable;
import simulation.implementations.policies.gaorexford.GaoRexfordAttribute;
import simulation.implementations.policies.gaorexford.GaoRexfordAttributeFactory;
import simulation.networks.RouteTablesGenerator;

public class GaoRexfordRouteTablesGenerator extends RouteTablesGenerator {

    private Integer onlyValidDestId = null;

    public GaoRexfordRouteTablesGenerator(Network network, Integer onlyValidDestId) {
        super(network, new GaoRexfordAttributeFactory());
        this.onlyValidDestId = onlyValidDestId;
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

            // create array of nodes for the path
            Node[] pathNodes = new Node[path.length];
            for (int i = 0; i < pathNodes.length; i++) {
                pathNodes[i] = new Node(network, path[i]);
            }

            routeTable.setPath(network.getNode(destId), network.getNode(neighbourId), new PathAttribute(pathNodes));
        }
    }

    private boolean isValidDestination(int destId) {
        return onlyValidDestId == null || destId == onlyValidDestId;
    }
}
