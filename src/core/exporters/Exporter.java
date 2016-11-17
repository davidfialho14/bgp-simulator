package core.exporters;

import core.EngineOperator;
import core.Route;
import core.State;
import core.topology.ConnectedNode;
import core.topology.Link;

/**
 * An exporter is responsible for the export operation of the simulation engine. Performs two different operations:
 * the generic exporting of one route through a link and the exporting of the destination.
 */
public interface Exporter extends EngineOperator {

    /**
     * Exports the given route through the given link.
     *
     * @param link  link to export through.
     * @param route route to export.
     */
    void export(Link link, Route route);

    /**
     * Exports the route to all of the exporting node's in-neighbors.
     *
     * @param exportingNode node exporting the route
     * @param route route to be exported
     */
    void exportToNeighbors(ConnectedNode exportingNode, Route route);

    /**
     * Exports the initial route(s) from the destination. The simulation engine calls this method to start the
     * simulation process.
     *
     * @param initialState  initial state to start the simulation with
     */
    void exportDestination(State initialState);

    /**
     * Clears any state that an exporter might be storing. Should be called before each simulation.
     */
    void reset();

}
