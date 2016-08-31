package core;


import core.events.*;
import core.exporters.Exporter;
import core.exporters.UnicastExporter;
import core.schedulers.ScheduledRoute;
import core.schedulers.Scheduler;
import core.topology.ConnectedNode;
import core.topology.Link;
import core.topology.Node;

import static core.InvalidAttribute.invalidAttr;
import static core.InvalidPath.invalidPath;
import static core.Route.invalidRoute;
import static core.Route.newRouteFrom;

/**
 * Engine implements the hard simulation simulation logic.
 */
public class Engine {

    private TimeProperty timeProperty = new TimeProperty();
    private SimulationEventGenerator eventGenerator = new SimulationEventGenerator();
    private Scheduler scheduler;
    private State currentState; // current state on the simulation - null if not simulating

    // Engine operators
    private Exporter exporter = new UnicastExporter();

    /**
     * Constructs new engine assigning it with the necessary scheduler.
     */
    public Engine(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.exporter.setEngine(this);
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
     * Returns the scheduler being used by the engine.
     *
     * @return the scheduler being used by the engine.
     */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * Simulates according to the given initial state. Simulates only for one destination.
     *
     * @param initialState initial state to start simulation.
     */
    public void simulate(State initialState) {
        currentState = initialState;
        scheduler.reset();

        eventGenerator.fireStartEvent(new StartEvent());

        exporter.exportDestination(initialState);
        simulationLoop(initialState);
        resetTime();

        currentState = null; // no longer simulating

        eventGenerator.fireEndEvent(new EndEvent());
    }

    //------------- PROPERTIES ----------------------------------------------------------------------------------------

    public TimeProperty timeProperty() {
        return timeProperty;
    }

    //------------- TRIGGERS ------------------------------------------------------------------------------------------

    /**
     * Should be called when a link is broken to trigger the correct behaviour from the simulation when a link is
     * broken. The source node updates the routes learned from the broken link to invalid routes. If the source node
     * ends up selecting a new route it exports it to all of its in-neighbours. It also discards all routes being
     * exported through the broken link.
     *
     * @param brokenLink link that was broken.
     */
    public void onBrokenLink(Link brokenLink) {
        NodeState sourceNodeState = currentState.get(brokenLink.getSource());
        Node destination = sourceNodeState.getTable().getDestination();
        processSelection(sourceNodeState, brokenLink, invalidRoute(destination));

        // remove all routes being exported through this link
        scheduler.removeRoutes(brokenLink);
    }

    //------------- PACKAGE METHODS -----------------------------------------------------------------------------------

    /**
     * Processes an imported route by updating the state of the learning node.
     *
     * @param nodeState state of the learning node.
     * @param link link from which the route was imported.
     * @param importedRoute route imported.
     */
    void process(NodeState nodeState, Link link, Route importedRoute) {
        Route learnedRoute = learn(nodeState, link, importedRoute);
        processSelection(nodeState, link, learnedRoute);
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

        Path path;
        if (attribute != invalidAttr()) {
            path = Path.copy(route.getPath());
            path.add(link.getDestination());    // add exporter to the path
        } else {
            path = invalidPath();
        }

        Node neighbour = attribute != invalidAttr() ? link.getDestination() : null;

        Route learnedRoute = newRouteFrom(route)
                .withAttribute(attribute)
                .withPath(path)
                .andNewSelectedAttribute(neighbour, route.getAttribute())
                .build();

        eventGenerator.fireLearnEvent(new LearnEvent(link, learnedRoute));

        return learnedRoute;
    }

    /**
     * Selects the best route taking into account the new learned route. It also updates the route table while
     * selecting the best route with the new learned route.
     *
     * @param nodeState state of the node who learned the route.
     * @param link link from which the route was learned.
     * @param learnedRoute route after being learned.
     * @return route currently being selected by the learning node to reach the route's destination.
     */
    Route select(NodeState nodeState, Link link, Route learnedRoute) {
        // unpacking some variables to easier reading of the code
        Node destination = learnedRoute.getDestination();
        ConnectedNode learningNode = link.getSource();
        RouteTable table = nodeState.getTable();

        // select the best route learned from all out-neighbours except the exporting out-link
        Route exclRoute = table.getExclRoute(link);

        if (learnedRoute.getPath().contains(learningNode)) {  // check for a loop in the path
            // there is a loop

            if (nodeState.getProtocol().isOscillation(link, learnedRoute, exclRoute)) {
                // detected oscillation
                eventGenerator.fireDetectEvent(new DetectEvent(link, learnedRoute, exclRoute));

                nodeState.getProtocol().setParameters(link, learnedRoute, exclRoute);
            }

            learnedRoute = invalidRoute(destination);
        }

        table.setRoute(link, learnedRoute);

        return table.getSelectedRoute();
    }

    /**
     * Exports a route to all in-neighbours of the exporting node
     *
     * @param exportingNode node which is exporting the route.
     * @param route route to be exported.
     */
    void exportToInNeighbours(ConnectedNode exportingNode, Route route) {
        exportingNode.getInLinks()
                .forEach(inLink -> exporter.export(inLink, route));
    }

    /**
     * Selects the preferred route for the destination and if the selected route is different from the previously
     * selected route it exports all the new selected route to all of the in-neighbours.
     *
     * @param nodeState state of the learning node.
     * @param link link from which the route was learned.
     * @param learnedRoute route that was learned by the node.
     */
    void processSelection(NodeState nodeState, Link link, Route learnedRoute) {
        // store the currently selected route
        Route prevSelectedRoute = nodeState.getTable().getSelectedRoute();

        Route selectedRoute = select(nodeState, link, learnedRoute);
        eventGenerator.fireSelectEvent(new SelectEvent(link.getSource(), prevSelectedRoute, selectedRoute));

        if (!prevSelectedRoute.equals(selectedRoute)) {
            exportToInNeighbours(link.getSource(), selectedRoute);
        }
    }

    //------------- PRIVATE METHODS -----------------------------------------------------------------------------------

    /**
     * Executes the simulation loop for the given state.
     *
     * @param state current state.
     */
    private void simulationLoop(State state) {

        timeProperty.setTime(0);    // starting time must be set here to notify the listeners
        while (scheduler.hasRoute()) {
            // update the current time
            timeProperty.setTime(scheduler.getCurrentTime());

            ScheduledRoute scheduledRoute = scheduler.get();

            // it is not guaranteed the scheduler has routes still since they can be removed by a link breaker
            // when updating the time
            if (scheduledRoute != null) {
                ConnectedNode learningNode = scheduledRoute.getLink().getSource();
                process(state.get(learningNode), scheduledRoute.getLink(), scheduledRoute.getRoute());
            }
        }
    }

    /**
     * Resets the time property by removing all listeners and setting the time to -1 in order to generate
     * a time change when the simulation starts.
     */
    private void resetTime() {
        timeProperty.removeAll();
        timeProperty.setTime(-1);
    }
}
