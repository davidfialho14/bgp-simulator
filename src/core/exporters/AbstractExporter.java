package core.exporters;

import core.Engine;
import core.Route;
import core.State;
import core.events.AdvertisementEvent;
import core.events.ExportEvent;
import core.schedulers.RouteReference;
import core.schedulers.ScheduledRoute;
import core.topology.ConnectedNode;
import core.topology.Link;

import static core.InvalidAttribute.invalidAttr;

/**
 * This class provides a skeleton implementation of the Exporter interface. Implements the association of an
 * exporter with an engine and provides a base implementation of the export() method.
 */
public abstract class AbstractExporter implements Exporter {

    private Engine engine;  // engine using the exporter
    private final MinimumRouteAdvertisementTimer mraTimer = new MinimumRouteAdvertisementTimer();

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
        export(link, new RouteReference(route), engine.timeProperty().getTime());
    }

    public void export(Link link, RouteReference routeReference, long exportTime) {
        // add the route to the scheduler with the given export time
        engine.getScheduler().put(new ScheduledRoute(routeReference, link, exportTime));

        engine.getEventGenerator().fireExportEvent(
                new ExportEvent(exportTime, link, routeReference.getRoute()));
    }

    /**
     * Exports the route to all of the exporting node's in-neighbors.
     *
     * @param exportingNode node exporting the route
     * @param route         route to be exported
     */
    @Override
    public void exportToNeighbors(ConnectedNode exportingNode, Route route) {
        long currentTime = engine.timeProperty().getTime();

        if (mraTimer.hasExpired(exportingNode, currentTime)) {
            mraTimer.resetNodeTimer(exportingNode, currentTime, route);

            long expirationTime = mraTimer.getExpirationTime(exportingNode);
            boolean advertisedUpdate = false;   // flags if a route other than an withdrawal was advertised

            // export the route to each in-neighbor
            for (Link link : exportingNode.getInLinks()) {

                long exportTime;
                if (link.extend(route.getAttribute()) == invalidAttr()) {
                    // withdrawals are exported immediately
                    exportTime = currentTime;

                    engine.getEventGenerator().fireAdvertisementEvent(
                            new AdvertisementEvent(currentTime, exportingNode, route));
                } else {
                    // advertisements that are not withdrawals must wait for the timer to expire
                    exportTime = expirationTime;
                    advertisedUpdate = true;
                }

                export(link, mraTimer.getExportRouteReference(exportingNode), exportTime);
            }

            if (advertisedUpdate) {
                // only fire this event if there was an advertisement that was not an withdrawal

                // fire an Advertisement Event with the time corresponding to the expiration time
                // the expiration time is the time when the event actually occurs
                engine.getEventGenerator().fireAdvertisementEvent(
                        new AdvertisementEvent(expirationTime, exportingNode, route));
            }

        } else {
            // update the export route for the current timer
            mraTimer.updateExportRoute(exportingNode, route);
        }

    }

    /**
     * Exports the initial route(s) from the destination. The simulation engine calls this method to start the
     * simulation process.
     *
     * @param initialState  initial state to start the simulation with
     */
    @Override
    public abstract void exportDestination(State initialState);

    /**
     * Clears any state that an exporter might be storing. Should be called before each simulation.
     */
    @Override
    public void reset() {
        // clear all timers for all nodes
        mraTimer.reset();
    }

}
