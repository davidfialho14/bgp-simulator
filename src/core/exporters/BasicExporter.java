package core.exporters;

import core.*;
import core.events.AdvertisementEvent;
import core.events.ExportEvent;
import core.schedulers.Scheduler;

import java.util.Collection;

import static core.events.EventNotifier.eventNotifier;


/**
 * Basic implementation of an exporter.
 */
public class BasicExporter implements Exporter {

    private final Scheduler scheduler;

    /**
     * Constructs an exporter given the scheduler where to put exported messages.
     *
     * @param scheduler scheduler where the messages are pushed to.
     */
    public BasicExporter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Returns the scheduler used by the exporter to put the messages.
     *
     * @return the scheduler used by the exporter to put the messages.
     */
    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * Exports the given message. The message arrival time must be the current time.
     *
     * @param exportingRouter   router exporting the route.
     * @param route             route to export.
     * @param currentTime       current simulation time.
     */
    @Override
    public void export(Router exportingRouter, Route route, int currentTime) {
        MRAITimer timer = exportingRouter.getMRAITimer();

        if (timer.isEnabled()) {
            timer.setExportRoute(route);

        } else {
            exportToNeighbors(exportingRouter, route, currentTime);

            // set a timer
            timer.reset(currentTime);
            scheduler.schedule(timer);
        }

    }

    /**
     * Exports the self route for the given destination to all of its in-neighbors.
     *
     * @param destination destination to export self routes for.
     * @param policy      policy used to get the self route.
     */
    @Override
    public void export(Destination destination, Policy policy) {
        Route selfRoute = Route.newSelfRoute(policy);
        destination.setSelfRoute(selfRoute);

        destination.getInLinks().forEach(link -> export(link, selfRoute, 0));
    }

    /**
     * Takes a collection of timers and exports the route associated with the timer to all
     * neighbors of the owner of the timer.
     *
     * @param timers collection with the timers to export.
     */
    @Override
    public void export(Collection<MRAITimer> timers) {

        for (MRAITimer timer : timers) {
            // after expiring, the timer is disabled
            timer.setEnabled(false);

            if (timer.hasExportableRoute()) {
                export(timer.getOwner(), timer.getExportRoute(), timer.getExpirationTime());
            }
        }
    }

    protected void exportToNeighbors(Router exportingRouter, Route route, int exportTime) {

        for (Link link : exportingRouter.getInLinks()) {
            export(link, route, exportTime);
        }

        eventNotifier().notifyAdvertisementEvent(new AdvertisementEvent(exportTime,
                exportingRouter, route));
    }

    protected void export(Link exportLink, Route route, int exportTime) {
        // add the route to the scheduler with the given export time
        scheduler.schedule(new Message(exportTime, exportLink, route));

        eventNotifier().notifyExportEvent(new ExportEvent(exportTime, exportLink, route));
    }

}
