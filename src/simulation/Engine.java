package simulation;

import network.Link;
import network.Node;
import network.SelfLink;
import policies.Attribute;
import simulation.events.*;

import static simulation.Route.invalidRoute;

/**
 * Engine implements the hardcore simulation logic.
 */
public class Engine {

    private TimeProperty timeProperty = new TimeProperty();
    private Scheduler scheduler;
    private SimulationEventGenerator eventGenerator = new SimulationEventGenerator();

    /**
     * Constructs an engine form a pre-configured builder.
     */
    public Engine(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Returns the event generator used by the engine. Listeners can then be added to this generator.
     *
     * @return event generator used by the engine.
     */
    public SimulationEventGenerator getEventGenerator() {
        return eventGenerator;
    }

    /**
     * Simulates according to the given initial state. Simulates with all nodes as destinations.
     *
     * @param initialState initial state to start simulation.
     */
    public void simulate(State initialState) {
        scheduler.reset();
        exportSelfRoute(initialState);
        simulationLoop(initialState);
    }

    /**
     * Simulates according to the given initial state. Simulates only for one destination.
     *
     * @param initialState initial state to start simulation.
     */
    public void simulate(State initialState, int destinationId) {
        scheduler.reset();
        exportSelfRoute(initialState.getNetwork().getNode(destinationId), initialState);
        simulationLoop(initialState);
    }

    //------------- PROPERTIES ----------------------------------------------------------------------------------------

    public TimeProperty timeProperty() {
        return timeProperty;
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

        Route learnedRoute = learn(nodeState, link, exportedRoute);

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
        eventGenerator.fireImportEvent(new ImportEvent(route, link));

        Attribute attribute = nodeState.getProtocol().extend(route.getDestination(), link, route.getAttribute());

        PathAttribute path;
        if (!attribute.isInvalid()) {
            path = new PathAttribute(route.getPath());
            path.add(link.getDestination());    // add exporter to the path
        } else {
            path = PathAttribute.invalidPath();
        }

        Route learnedRoute = new Route(route.getDestination(), attribute, path);
        eventGenerator.fireLearnEvent(new LearnEvent(link, learnedRoute));

        return learnedRoute;
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
                eventGenerator.fireDetectEvent(new DetectEvent(link, learnedRoute, exclRoute));

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
        long timestamp;
        if (prevScheduledRoute == null) {
            // exporting self route
            timestamp = 0;
        } else {
            timestamp = prevScheduledRoute.getTimestamp();
        }

        ScheduledRoute scheduledRoute = new ScheduledRoute(route, link, timestamp);
        scheduler.put(scheduledRoute);

        eventGenerator.fireExportEvent(new ExportEvent(link, route));
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

        // store the currently selected route
        Route prevSelectedRoute = nodeState.getSelectedRoute(destination);

        Route selectedRoute = select(nodeState, link, exportedRoute, learnedRoute);

        eventGenerator.fireSelectEvent(new SelectEvent(prevSelectedRoute, selectedRoute));

        if (prevSelectedRoute == null || !prevSelectedRoute.equals(selectedRoute)) {
            /*
                must export the new route to all of the learning node's in-links except to the node
                from which the route was learned.
             */
            exportToInNeighbours(link.getSource(), selectedRoute, link.getDestination(), prevScheduledRoute);
        }
    }

    //------------- PRIVATE METHODS -----------------------------------------------------------------------------------

    /**
     * Executes the simulation loop for the given state.
     *
     * @param state current state.
     */
    private void simulationLoop(State state) {

        ScheduledRoute scheduledRoute;
        timeProperty.setTime(0);    // starting time must be set here to notify the listeners
        while ((scheduledRoute = scheduler.get()) != null) {
            Node learningNode = scheduledRoute.getLink().getSource();
            process(state.get(learningNode), scheduledRoute);

            timeProperty.setTime(scheduledRoute.getTimestamp());
        }
    }

    /**
     * Exports the self route of one node. It updates that state of the node to selected the self route.
     *
     * @param node node to export self route.
     * @param state current state.
     */
    private void exportSelfRoute(Node node, State state) {
        NodeState nodeState = state.get(node);
        Route selfRoute = Route.createSelf(node, state.getNetwork().getPolicy());

        // add the self route to the node's route table
        nodeState.updateRoute(node, new SelfLink(node), selfRoute.getAttribute(), selfRoute.getPath());
        nodeState.setSelected(node, selfRoute);

        node.getInLinks().forEach(link -> export(link, selfRoute, null));
    }

    /**
     * Exports the self route of all nodes in the network. It updates that state of the nodes to selected
     * the self routes.
     *
     * @param state current state.
     */
    private void exportSelfRoute(State state) {
        state.getNetwork().getNodes().forEach(node -> exportSelfRoute(node, state));
    }
}
