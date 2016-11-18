package v2.core.exporters;

import v2.core.*;
import v2.core.events.AdvertisementEvent;
import v2.core.events.ExportEvent;
import v2.core.schedulers.RouteReference;
import v2.core.schedulers.Scheduler;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.Route.newSelfRoute;
import static v2.core.events.EventNotifier.eventNotifier;


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

        if (!timer.hasExpired(currentTime)) {
            // update the export route for the current timer
            timer.updateExportRoute(route);

        } else {
            timer.reset(currentTime, route);

            int expirationTime = timer.getExpirationTime();
            boolean exportedUpdate = false; // indicates if a message other than an withdrawal was exported

            // export the route to each in-neighbor
            for (Link link : exportingRouter.getInLinks()) {

                int exportTime;
                Attribute extendedAttribute = link.getLabel().extend(link, route.getAttribute());
                if (extendedAttribute == invalidAttr()) {
                    // withdrawals are exported immediately
                    exportTime = currentTime;

                    eventNotifier().notifyAdvertisementEvent(
                            new AdvertisementEvent(exportTime, exportingRouter, route));

                } else {
                    // advertisements that are not withdrawals must wait for the timer to expire
                    exportTime = expirationTime;
                    exportedUpdate = true;
                }

                export(link, timer.getExportRouteReference(), exportTime);
            }

            if (exportedUpdate) {
                // only fire this event if there was an advertisement that was not an withdrawal

                // fire an Advertisement Event with the time corresponding to the expiration time
                // the expiration time is the time when the event actually occurs
                eventNotifier().notifyAdvertisementEvent(
                        new AdvertisementEvent(expirationTime, exportingRouter, route));
            }
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
        Route selfRoute = newSelfRoute(policy);
        destination.setSelfRoute(selfRoute);
        RouteReference selfRouteReference = new RouteReference(selfRoute);

        destination.getInLinks().forEach(link -> export(link, selfRouteReference, 0));
    }

    protected void export(Link exportLink, RouteReference routeReference, int exportTime) {
        // add the route to the scheduler with the given export time
        scheduler.schedule(new Message(exportTime, exportLink, routeReference));

        eventNotifier().notifyExportEvent(new ExportEvent(exportTime, exportLink, routeReference.getRoute()));
    }

}
