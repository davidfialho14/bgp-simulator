package simulation;

import network.Link;
import network.Network;
import network.Node;
import policies.Attribute;
import policies.Policy;
import protocols.Protocol;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Engine implements the hardcore simulation logic.
 */
public class SimulateEngine {

    private Protocol protocol;
    private Policy policy;
    private Scheduler scheduler;
    private EventHandler eventHandler;

    // state information of the nodes during and after simulation
    private Map<Node, NodeStateInfo> nodesStateInfo = new HashMap<>();

    /**
     * Initializes a new SimulateEngine.
     * @param protocol routing protocol to be used.
     * @param policy factory used to create attributes.
     * @param scheduler scheduler used to schedule exported routes.
     * @param eventHandler event handler called on any new event.
     */
    public SimulateEngine(Protocol protocol, Policy policy, Scheduler scheduler,
                          EventHandler eventHandler) {
        this.protocol = protocol;
        this.policy = policy;
        this.scheduler = scheduler;
        this.eventHandler = eventHandler;
    }

    /**
     * Simulates the BGP protocol according to the specifications of the engine for the given network.
     * During simulation the slot methods of the event handler are called in the appropriate time.
     * @param network network to be simulated.
     */
    public void simulate(Network network) {
        initNodesStateInfo(network.getNodes());

        eventHandler.onBeforeSimulate();
        exportSelfRoute(network.getNodes());
        simulationLoop();
        eventHandler.onAfterSimulate();
    }

    /**
     * Executes the simulation but just for one destination node. @see {@link SimulateEngine#simulate(Network)}
     * @param network network being simulated.
     * @param destinationId id of the destination node to simulate for.
     */
    public void simulate(Network network, int destinationId) {
        initNodesStateInfo(network.getNodes());

        eventHandler.onBeforeSimulate();
        exportSelfRoute(network.getNode(destinationId));
        simulationLoop();
        eventHandler.onAfterSimulate();
    }

    /**
     * Returns a map containing the nodes associated with their respective route tables. The route tables will only
     * be filled after a simulation takes place.
     * @return map containing the nodes associated with their respective route tables.
     */
    public Map<Node, RouteTable> getRouteTables() {
        return nodesStateInfo.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getTable()
                ));
    }

    /**
     * Returns the route table of the given node. The route table will only be filled after a simulation takes place.
     * @return map containing the nodes associated with their respective route tables.
     */
    public RouteTable getRouteTable(Node node) {
        return nodesStateInfo.get(node).getTable();
    }

    /**
     * Returns the event handler associated with the engine.
     * @return event handler associated with the engine.
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    //------------- PACKAGE METHODS -----------------------------------------------------------------------------------

    /**
     * Processes a scheduled route by updating the state info of the learning node.
     * @param nodeStateInfo state info of the learning node.
     * @param scheduledRoute scheduled route to process.
     */
    void process(NodeStateInfo nodeStateInfo, ScheduledRoute scheduledRoute) {
        // unpack the link, exported route, and destination node
        Link link = scheduledRoute.getLink();
        Route exportedRoute = scheduledRoute.getRoute();
        Node destination = exportedRoute.getDestination();
        Node learningNode = link.getSource();

        if (learningNode.equals(destination)) {
            // discard the route
            eventHandler.onDiscardRoute(link, exportedRoute);
            return;
        }

        eventHandler.onBeforeLearn(link, exportedRoute);
        Route learnedRoute = learn(link, exportedRoute);
        eventHandler.onAfterLearn(link, exportedRoute, learnedRoute);

        // store the currently selected attribute and path
        Attribute prevSelectedAttribute = nodeStateInfo.getSelectedAttribute(destination);
        PathAttribute prevSelectedPath = nodeStateInfo.getSelectedPath(destination);

        eventHandler.onBeforeSelect(nodeStateInfo, link, exportedRoute, learnedRoute,
                prevSelectedAttribute, prevSelectedPath);
        Route selectedRoute = select(nodeStateInfo, link, exportedRoute, learnedRoute);
        eventHandler.onAfterSelect(nodeStateInfo, link, exportedRoute, learnedRoute,
                prevSelectedAttribute, prevSelectedPath, selectedRoute);

        if (prevSelectedAttribute == null || !prevSelectedAttribute.equals(selectedRoute.getAttribute()) ||
                    !prevSelectedPath.equals(selectedRoute.getPath())) {
            /*
                must export the new route to all of the learning node's in-links except to the node
                from which the route was learned.
             */
            exportToInNeighbours(link.getSource(), selectedRoute, link.getDestination(), scheduledRoute);
        }
    }

    /**
     * Learns a new exported route, returning the route after the attribute has been exported and included the
     * out-neighbour in the path.
     * @param link link through which the route was exported.
     * @param route exported route.
     * @return route after the attribute has been exported and included the out-neighbour in the path.
     */
    Route learn(Link link, Route route) {
        Attribute attribute = protocol.extend(route.getDestination(), link, route.getAttribute());

        PathAttribute path;
        if (!attribute.isInvalid()) {
            path = new PathAttribute(route.getPath());
            path.add(link.getDestination());    // add exporter to the path
        } else {
            path = PathAttribute.invalidPath();
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
        Node learningNode = link.getSource();

        // select the best route learned from all out-neighbours except the exporting out-link
        Route exclRoute = nodeStateInfo.getSelectedRoute(destination, link);

        if (learnedRoute.getPath().contains(learningNode)) {  // check for a loop in the path
            // there is a loop

            if (protocol.isOscillation(link, exportedRoute,
                    learnedRoute.getAttribute(), learnedRoute.getPath(), exclRoute)) {
                // detected oscillation
                eventHandler.onOscillationDetection(link, exportedRoute, learnedRoute, exclRoute);

                protocol.setParameters(link, exportedRoute,
                        learnedRoute.getAttribute(), learnedRoute.getPath(), exclRoute);
            }

            learnedRoute = Route.createInvalid(destination);
        }

        Route selectedRoute;
        if (exclRoute == null || learnedRoute.compareTo(exclRoute) < 0) {
            selectedRoute = new Route(learnedRoute);
        } else {
            selectedRoute = new Route(exclRoute);
        }

        // update the node state information
        nodeStateInfo.setSelected(destination, selectedRoute);
        nodeStateInfo.updateRoute(destination, link, learnedRoute.getAttribute(), learnedRoute.getPath());

        return selectedRoute;
    }

    /**
     * Exports the given route to all of the in-neighbours of the exporting node except to node indicated as
     * not to export.
     * @param exportingNode node which is exporting the route.
     * @param route route to be exported.
     * @param nodeNotToExport node to which the route is not to be exported.
     * @param prevScheduledRoute previously scheduled route.
     */
    void exportToInNeighbours(Node exportingNode, Route route, Node nodeNotToExport,
                              ScheduledRoute prevScheduledRoute) {
        exportingNode.getInLinks().stream()
                .filter(inLink -> !inLink.getSource().equals(nodeNotToExport))  // exclude the nodeNotToExport
                .forEach(inLink -> export(inLink, route, prevScheduledRoute));
    }

    /**
     * Exports a route through the given link. The route is put in the network's scheduler.
     * @param link link to export the route to.
     * @param route route to be exported.
     * @param prevScheduledRoute scheduled route previously got from the scheduler.
     */
    void export(Link link, Route route, ScheduledRoute prevScheduledRoute) {
        eventHandler.onBeforeExport(link, route, prevScheduledRoute);

        long timestamp;
        if (prevScheduledRoute == null) {
            // exporting self route
            timestamp = 0;
        } else {
            timestamp = prevScheduledRoute.getTimestamp();
        }

        ScheduledRoute scheduledRoute = new ScheduledRoute(route, link, timestamp);
        scheduler.put(scheduledRoute);

        eventHandler.onAfterExport(link, route, prevScheduledRoute, scheduledRoute);
    }

    //------------- PRIVATE METHODS -----------------------------------------------------------------------------------

    /**
     * Executes the simulation loop for the given network.
     */
    private void simulationLoop() {
        ScheduledRoute scheduledRoute;
        while ((scheduledRoute = scheduler.get()) != null) {
            Node learningNode = scheduledRoute.getLink().getSource();
            process(nodesStateInfo.get(learningNode), scheduledRoute);
        }
    }

    /**
     * Initializes state info of for the given collection of nodes. All current node state information is cleared
     * after calling this method.
     * @param nodes nodes to initialize the state info for.
     */
    private void initNodesStateInfo(Collection<Node> nodes) {
        nodesStateInfo.clear();
        nodes.forEach(node -> nodesStateInfo.put(node, new NodeStateInfo(node, policy)));
    }

    /**
     * Exports the self routes of each one of the given nodes.
     * @param nodes nodes which the self routes are to be exported.
     */
    private void exportSelfRoute(Collection<Node> nodes) {
        nodes.forEach(this::exportSelfRoute);
    }

    /**
     * Exports the self route of the given node.
     * @param node node which the self route is to be exported.
     */
    private void exportSelfRoute(Node node) {
        node.getInLinks().forEach(link -> export(link, Route.createSelf(node, policy), null));
    }
}
