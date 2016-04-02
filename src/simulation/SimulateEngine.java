package simulation;

import network.Link;
import network.Network;
import network.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Engine implements the hardcore simulation logic.
 */
public class SimulateEngine {

    private Protocol protocol;
    private AttributeFactory attributeFactory;
    private Scheduler scheduler;
    private EventHandler eventHandler;

    // state information of the nodes during and after simulation
    private Map<Node, NodeStateInfo> nodesStateInfo = new HashMap<>();

    /**
     * Initializes a new SimulateEngine.
     * @param protocol routing protocol to be used.
     * @param attributeFactory factory used to create attributes.
     * @param scheduler scheduler used to schedule exported routes.
     * @param eventHandler event handler called on any new event.
     */
    public SimulateEngine(Protocol protocol, AttributeFactory attributeFactory, Scheduler scheduler, EventHandler eventHandler) {
        this.protocol = protocol;
        this.attributeFactory = attributeFactory;
        this.scheduler = scheduler;
        this.eventHandler = eventHandler;
    }

    /**
     * Simulates the BGP protocol according to the specifications of the engine for the given network.
     * During simulation the slot methods of the event handler are called in the appropriate time.
     * @param network network to be simulated.
     */
    public void simulate(Network network) {
        initNodesStateInfo(network);
        exportNodesSelfRoutes(network);

        // simulation loop
        ScheduledRoute scheduledRoute;
        while ((scheduledRoute = scheduler.get()) != null) {
            Node learningNode = scheduledRoute.getLink().getSource();
            processScheduledRoute(scheduledRoute, nodesStateInfo.get(learningNode));
        }
    }

    //------------- PACKAGE METHODS -----------------------------------------------------------------------------------

    void processScheduledRoute(ScheduledRoute scheduledRoute, NodeStateInfo nodeStateInfo) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Learns a new exported route, returning the route after the attribute has been exported and included the
     * out-neighbour in the path.
     * @param link link through which the route was exported.
     * @param route exported route.
     * @return route after the attribute has been exported and included the out-neighbour in the path.
     */
    Route learn(Link link, Route route) {
        Attribute attribute = protocol.extend(link, route.getAttribute());

        PathAttribute path;
        if (!attribute.isInvalid()) {
            path = route.getPath();  // FIXME copy the path!
            path.add(link.getDestination());    // add exporter to the path
        } else {
            path = PathAttribute.createInvalidPath();
        }

        return new Route(route.getDestination(), attribute, path);
    }

    /**
     * Selects the best route taking into account the new learned route. It also updates the route table while
     * selecting the best route with the new learned route.
     * @param nodeStateInfo state information of the node who learned the route.
     * @param link link from which the route was learned.
     * @param exportedRoute route when it was exported.
     * @param learnedRoute route after being learned.
     * @return route currently being selected by the learning node to reach the route's destination.
     */
    Route select(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute) {
        // unpacking some variables to easier reading of the code
        Node destination = learnedRoute.getDestination();
        Node exportingNeighbour = link.getDestination();
        Node learningNode = link.getSource();
        Attribute attribute = learnedRoute.getAttribute();
        PathAttribute path = learnedRoute.getPath();

        // select the best route learned from all out-neighbours except the exporting neighbour
        Route exclRoute = nodeStateInfo.getSelectedRoute(destination, exportingNeighbour);

        if (path.contains(learningNode)) {  // check for a loop in the path
            if (protocol.isOscillation(link, exportedRoute, attribute, path, exclRoute)) {
                // detected oscillation
                protocol.setParameters(link, exportedRoute, attribute, path, exclRoute);
            }

            // there is a loop
            learnedRoute = Route.createInvalid(destination, attributeFactory);
        }

        Route selectedRoute;
        if (exclRoute == null || learnedRoute.compareTo(exclRoute) < 0) {
            selectedRoute = new Route(learnedRoute);
        } else {
            selectedRoute = new Route(exclRoute);
        }

        // update the node state information
        nodeStateInfo.setSelected(destination, selectedRoute);
        nodeStateInfo.updateRoute(destination, exportingNeighbour, attribute, path);

        return selectedRoute;
    }

    /**
     * Exports a route through the given link. The route is put in the network's scheduler.
     * @param link link to export the route to.
     * @param route route to be exported.
     * @param prevScheduledRoute scheduled route previously got from the scheduler.
     */
    void export(Link link, Route route, ScheduledRoute prevScheduledRoute) {
        long timestamp;
        if (prevScheduledRoute == null) {
            // exporting self route
            timestamp = 0;
        } else {
            timestamp = prevScheduledRoute.getTimestamp();
        }

        scheduler.put(new ScheduledRoute(route, link, timestamp));
    }

    //------------- PRIVATE METHODS -----------------------------------------------------------------------------------

    /**
     * Initializes the network's nodes state info with the default state information for each node.
     * @param network network being simulated.
     */
    private void initNodesStateInfo(Network network) {
        nodesStateInfo.clear();
        for (Node node : network.getNodes()) {
            nodesStateInfo.put(node, new NodeStateInfo(node, attributeFactory));
        }
    }

    /**
     * Exports all self routes from all nodes in the given network.
     * @param network network being simulated.
     */
    private void exportNodesSelfRoutes(Network network) {
        for (Node node : network.getNodes()) {
            for (Link inLink : node.getInLinks()) {
                export(inLink, Route.createSelf(node, attributeFactory), null);
            }
        }
    }
}
