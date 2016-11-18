package v2.core.exporters;

import v2.core.*;
import v2.core.schedulers.RouteReference;
import v2.core.schedulers.Scheduler;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.Route.newSelfRoute;


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
    protected BasicExporter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Exports the given message. The message arrival time must be the current time.
     *
     * @param exportLink    link to export route through.
     * @param route         route to export.
     * @param currentTime   current simulation time.
     */
    @Override
    public void export(Link exportLink, Route route, int currentTime) {
        Router exportingRouter = exportLink.getSource();
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

                    // FIXME fire advertisement event here
                } else {
                    // advertisements that are not withdrawals must wait for the timer to expire
                    exportTime = expirationTime;
                    exportedUpdate = true;
                }

                export(exportLink, timer.getExportRouteReference(), exportTime);
            }

            if (exportedUpdate) {
                // only fire this event if there was an advertisement that was not an withdrawal

                // fire an Advertisement Event with the time corresponding to the expiration time
                // the expiration time is the time when the event actually occurs
                // FIXME fire advertisement event here
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

        // FIXME fire export event here
    }

}
