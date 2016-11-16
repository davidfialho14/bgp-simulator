package core.exporters;

import core.Engine;
import core.Route;
import core.State;
import core.events.ExportEvent;
import core.schedulers.RouteReference;
import core.schedulers.ScheduledRoute;
import core.topology.Link;

/**
 * This class provides a skeletal implementation of the Exporter interface. Implements the association of an exporter
 * with an engine and provides a base implementation of the export() method.
 */
public abstract class AbstractExporter implements Exporter {

    private Engine engine;  // engine using the exporter

    /**
     * Associates an engine with the operator.
     *
     * @param engine engine to associate.
     */
    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /**
     * Exports the given route through the given link and fires an export event using the engine's event generator.
     *
     * @param link  link to export through.
     * @param route route to export.
     */
    @Override
    public void export(Link link, Route route) {
        engine.getScheduler().put(new ScheduledRoute(
                new RouteReference(route), link, engine.timeProperty().getTime()));

        engine.getEventGenerator().fireExportEvent(new ExportEvent(link, route));
    }

    /**
     * Exports the initial route(s) from the destination. The simulation engine calls this method to start the
     * simulation process.
     *
     * @param initialState  initial state to start the simulation with
     */
    @Override
    public abstract void exportDestination(State initialState);
}
