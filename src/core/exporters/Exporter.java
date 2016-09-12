package core.exporters;

import core.EngineOperator;
import core.Route;
import core.State;
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
     * Exports the initial route(s) from the destination. The simulation engine calls this method to start the
     * simulation process.
     *
     * @param initialState  initial state to start the simulation with
     */
    void exportDestination(State initialState);

}
