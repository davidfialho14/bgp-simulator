package simulation;

import network.Link;
import network.Network;
import network.Node;
import network.SelfLink;
import policies.Attribute;
import policies.Policy;
import protocols.Protocol;
import simulation.implementations.handlers.NullEventHandler;
import simulation.implementations.linkbreakers.DummyLinkBreaker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static simulation.Route.invalidRoute;

/**
 * Engine implements the hardcore simulation logic.
 */
public class Engine {

    private final Policy policy;
    private final Scheduler scheduler;
    private EventHandler eventHandler;
    private LinkBreaker linkBreaker;
    private LinkInserter linkInserter;

    // state of the nodes during and after simulation
    private Map<Node, NodeState> nodesStates = new HashMap<>();

    /**
     * Class responsible for building engine instances. (Builder pattern)
     */
    public static class Builder {

        // Required dependencies
        private final Policy policy;
        private final Scheduler scheduler;

        // Optional dependencies (initialized to the defaults)
        private EventHandler eventHandler = new NullEventHandler();
        private LinkBreaker linkBreaker = new DummyLinkBreaker();
        private LinkInserter linkInserter= new DummyLinkInserter();

        // Constructor with the required dependencies only

        public Builder(Policy policy, Scheduler scheduler) {
            this.policy = policy;
            this.scheduler = scheduler;
        }

        // Set methods for optional dependencies

        public Builder eventHandler(EventHandler eventHandler) {
            this.eventHandler = eventHandler;
            return this;
        }

        public Builder linkBreaker(LinkBreaker linkBreaker) {
            this.linkBreaker = linkBreaker;
            return this;
        }

        public Builder linkInserter(LinkInserter linkInserter) {
            this.linkInserter = linkInserter;
            return this;
        }

        // method to build the engine

        public Engine build() {
            return new Engine(this);
        }

    }

    /**
     * Constructs an engine form a pre-configured builder.
     */
    private Engine(Builder builder) {
        this.policy = builder.policy;
        this.scheduler = builder.scheduler;
        this.eventHandler = builder.eventHandler;
        this.linkBreaker = builder.linkBreaker;
        this.linkInserter = builder.linkInserter;
    }

    // Setters to change optional dependencies only
    // TODO after refactoring the integration tests, remove this setters

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setLinkBreaker(LinkBreaker linkBreaker) {
        this.linkBreaker = linkBreaker;
    }

    /**
     * Simulates the BGP protocol according to the specifications of the engine for the given network.
     * During simulation the slot methods of the event handler are called in the appropriate time.
     *
     * @param network network to be simulated.
     * @param initialProtocol initial protocol to be used by all the nodes.
     */
    public void simulate(Network network, Protocol initialProtocol) {
        initNodesStates(network.getNodes(), initialProtocol);

        eventHandler.onBeforeSimulate();
        exportSelfRoute(network.getNodes());
        simulationLoop(network);
        eventHandler.onAfterSimulate();
    }

    /**
     * Executes the simulation but just for one destination node. @see {@link Engine#simulate(Network, Protocol)}
     *  @param network network being simulated.
     * @param initialProtocol initial protocol to be used by all the nodes.
     * @param destinationId id of the destination node to simulate for.
     */
    public void simulate(Network network, Protocol initialProtocol, int destinationId) {
        initNodesStates(network.getNodes(), initialProtocol);

        eventHandler.onBeforeSimulate();
        exportSelfRoute(network.getNode(destinationId));
        simulationLoop(network);
        eventHandler.onAfterSimulate();
    }

    /**
     * Returns a map containing the nodes associated with their respective route tables. The route tables will only
     * be filled after a simulation takes place.
     *
     * @return map containing the nodes associated with their respective route tables.
     */
    public Map<Node, RouteTable> getRouteTables() {
        return nodesStates.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getTable()
                ));
    }

    /**
     * Returns the route table of the given node. The route table will only be filled after a simulation takes place.
     *
     * @return map containing the nodes associated with their respective route tables.
     */
    public RouteTable getRouteTable(Node node) {
        return nodesStates.get(node).getTable();
    }

    /**
     * Returns the route selected by the node to reach the destination.
     *
     * @param node node to get selected route for.
     * @param destination destination to get selected route for.
     * @return the route selected by the node to reach the destination.
     */
    public Route getSelectedRoute(Node node, Node destination) {
        return nodesStates.get(node).getSelectedRoute(destination, null);
    }

    /**
     * Returns the event handler associated with the engine.
     *
     * @return event handler associated with the engine.
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void reset() {
        nodesStates.values().forEach(nodeState -> nodeState.getProtocol().reset());
        scheduler.reset();
        nodesStates.clear();
    }

    //------------- PACKAGE METHODS -----------------------------------------------------------------------------------

    /**
     * Processes a scheduled route by updating the state of the learning node.
     *
     * @param nodeState state of the learning node.
     * @param scheduledRoute scheduled route to process.
     */
    void process(NodeState nodeState, ScheduledRoute scheduledRoute) {
        // unpack the link, exported route, and destination node
        Link link = scheduledRoute.getLink();
        Route exportedRoute = scheduledRoute.getRoute();

        eventHandler.onBeforeLearn(link, exportedRoute);
        Route learnedRoute = learn(nodeState, link, exportedRoute);
        eventHandler.onAfterLearn(link, exportedRoute, learnedRoute);

        processSelection(nodeState, link, exportedRoute, learnedRoute, scheduledRoute);
    }

    /**
     * Learns a new exported route, returning the route after the attribute has been exported and included the
     * out-neighbour in the path.
     *
     * @param nodeState current state of the learning node.
     * @param link link through which the route was exported.
     * @param route exported route.
     * @return route after the attribute has been exported and included the out-neighbour in the path.
     */
    Route learn(NodeState nodeState, Link link, Route route) {
        Attribute attribute = nodeState.getProtocol().extend(route.getDestination(), link, route.getAttribute());

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
     *
     * @param nodeState state of the node who learned the route.
     * @param link link from which the route was learned.
     * @param exportedRoute route when it was exported.
     * @param learnedRoute route after being learned.
     * @return route currently being selected by the learning node to reach the route's destination.
     */
    Route select(NodeState nodeState, Link link, Route exportedRoute, Route learnedRoute) {
        // unpacking some variables to easier reading of the code
        Node destination = learnedRoute.getDestination();
        Node learningNode = link.getSource();

        // select the best route learned from all out-neighbours except the exporting out-link
        Route exclRoute = nodeState.getSelectedRoute(destination, link);

        if (learnedRoute.getPath().contains(learningNode)) {  // check for a loop in the path
            // there is a loop

            if (nodeState.getProtocol().isOscillation(link, exportedRoute,
                    learnedRoute.getAttribute(), learnedRoute.getPath(), exclRoute)) {
                // detected oscillation
                eventHandler.onOscillationDetection(link, exportedRoute, learnedRoute, exclRoute);

                nodeState.getProtocol().setParameters(link, exportedRoute,
                        learnedRoute.getAttribute(), learnedRoute.getPath(), exclRoute);
            }

            learnedRoute = invalidRoute(destination);
        }

        Route selectedRoute;
        if (exclRoute == null || learnedRoute.compareTo(exclRoute) < 0) {
            selectedRoute = new Route(learnedRoute);
        } else {
            selectedRoute = new Route(exclRoute);
        }

        // update the node state
        nodeState.setSelected(destination, selectedRoute);
        nodeState.updateRoute(destination, link, learnedRoute.getAttribute(), learnedRoute.getPath());

        return selectedRoute;
    }

    /**
     * Exports the given route to all of the in-neighbours of the exporting node except to node indicated as
     * not to export.
     *
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
     *
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

    /**
     * Selects the preferred route for the destination and if the selected route is different from the previously
     * selected route it exports all the new selected route to all of the in-neighbours.
     *  @param nodeState state of the learning node.
     * @param link link from which the route was learned.
     * @param exportedRoute route that was exported through the link.
     * @param learnedRoute route that was learned by the node.
     * @param prevScheduledRoute previously scheduled route.
     */
    void processSelection(NodeState nodeState, Link link, Route exportedRoute, Route learnedRoute,
                          ScheduledRoute prevScheduledRoute) {

        Node destination = learnedRoute.getDestination();

        // store the currently selected attribute and path
        Attribute prevSelectedAttribute = nodeState.getSelectedAttribute(destination);
        PathAttribute prevSelectedPath = nodeState.getSelectedPath(destination);

        eventHandler.onBeforeSelect(nodeState, link, exportedRoute, learnedRoute,
                prevSelectedAttribute, prevSelectedPath);
        Route selectedRoute = select(nodeState, link, exportedRoute, learnedRoute);
        eventHandler.onAfterSelect(nodeState, link, exportedRoute, learnedRoute,
                prevSelectedAttribute, prevSelectedPath, selectedRoute);

        if (prevSelectedAttribute == null || !prevSelectedAttribute.equals(selectedRoute.getAttribute()) ||
                !prevSelectedPath.equals(selectedRoute.getPath())) {
            /*
                must export the new route to all of the learning node's in-links except to the node
                from which the route was learned.
             */
            exportToInNeighbours(link.getSource(), selectedRoute, link.getDestination(), prevScheduledRoute);
        }
    }

    /**
     * Processes a broken link. It re-selects the preferred route and exports it if it is a different route than the
     * previously preferred. It also removes the link from the route table and it remvoes all routes being exported
     * through that link from the scheduler.
     *  @param nodeState state of the source node of the broken link.
     * @param brokenLink link that was broken.
     * @param prevScheduledRoute previously scheduled route.
     */
    void processBrokenLink(NodeState nodeState, Link brokenLink, ScheduledRoute prevScheduledRoute) {
        // breaking a link is the same thing that learning invalid routes for all destinations known through that link

        for (Node destination : nodeState.getTable().getDestinationsLearnFrom(brokenLink)) {
            processSelection(nodeState, brokenLink,
                    invalidRoute(destination), invalidRoute(destination), prevScheduledRoute);
        }

        // remove the link from the sour node route table and remove all routes being exported through this link
        nodeState.getTable().removeOutLink(brokenLink);
        scheduler.removeRoutes(brokenLink);
    }

    /**
     * Processes an inserted link. Exports all selected routes through the new link and adds the link to the route
     * table of the source node of the link.
     *
     * @param insertedLink link that was inserted.
     * @param prevScheduledRoute previously scheduled route.
     */
    void processInsertedLink(Link insertedLink, ScheduledRoute prevScheduledRoute) {
        NodeState sourceState = nodesStates.get(insertedLink.getSource());
        NodeState destinationState = nodesStates.get(insertedLink.getDestination());

        // when inserting a new link the destination must export all of its selected routes through the new link
        destinationState.getSelectedRoutes().values()
                .forEach(route -> export(insertedLink, route, prevScheduledRoute));

        sourceState.getTable().addOutLink(insertedLink);
    }

    //------------- PRIVATE METHODS -----------------------------------------------------------------------------------

    /**
     * Executes the simulation loop for the given network.
     *
     * @param network network being simulated.
     */
    private void simulationLoop(Network network) {
        ScheduledRoute scheduledRoute;
        while ((scheduledRoute = scheduler.get()) != null) {
            Node learningNode = scheduledRoute.getLink().getSource();
            process(nodesStates.get(learningNode), scheduledRoute);

            Link brokenLink = linkBreaker.breakAnyLink(network, scheduledRoute.getTimestamp());

            if (brokenLink != null) {
                eventHandler.onBrokenLink(brokenLink);
                processBrokenLink(nodesStates.get(brokenLink.getSource()), brokenLink, scheduledRoute);
            }

            Link insertedLink = linkInserter.insertAnyLink(network, scheduledRoute.getTimestamp());

            if (insertedLink != null) {
                // TODO eventHandler.onInsertedLink(insertedLink);
                processInsertedLink(insertedLink, scheduledRoute);
            }
        }
    }

    /**
     * Initializes state of for the given collection of nodes. All current node state is cleared after calling
     * this method.
     *
     * @param nodes nodes to initialize the state for.
     * @param protocol protocol to be used by all nodes.
     */
    private void initNodesStates(Collection<Node> nodes, Protocol protocol) {
        nodes.forEach(node -> nodesStates.put(node, new NodeState(node, protocol)));
    }

    /**
     * Exports the self routes of each one of the given nodes.
     *
     * @param nodes nodes which the self routes are to be exported.
     */
    private void exportSelfRoute(Collection<Node> nodes) {
        nodes.forEach(this::exportSelfRoute);
    }

    /**
     * Exports the self route of the given node.
     *
     * @param node node which the self route is to be exported.
     */
    private void exportSelfRoute(Node node) {
        NodeState nodeState = nodesStates.get(node);
        Route selfRoute = Route.createSelf(node, policy);

        // add the self route to the node's route table
        nodeState.updateRoute(node, new SelfLink(node), selfRoute.getAttribute(), selfRoute.getPath());
        nodeState.setSelected(node, selfRoute);

        node.getInLinks().forEach(link -> export(link, selfRoute, null));
    }
}
