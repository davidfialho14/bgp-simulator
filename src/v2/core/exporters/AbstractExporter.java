package v2.core.exporters;

import v2.core.*;
import v2.core.schedulers.Scheduler;

import static v2.core.InvalidAttribute.invalidAttr;


/**
 * This class provides a skeleton implementation of the Exporter interface. Implements the association of an
 * exporter with an engine and provides a base implementation of the export() methods.
 */
public abstract class AbstractExporter implements Exporter {

    private final Scheduler scheduler;

    /**
     * Constructs an exporter given the scheduler where to put exported messages.
     *
     * @param scheduler scheduler where the messages are pushed to.
     */
    protected AbstractExporter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Exports the given message. The message arrival time must be the current time.
     *
     * @param message message to export.
     */
    @Override
    public void export(Message message) {
        Router exportingRouter = message.getTraversedLink().getSource();
        MRAITimer timer = exportingRouter.getMRAITimer();

        int currentTime = message.getArrivalTime();

        if (!timer.hasExpired(currentTime)) {
            // update the export route for the current timer
            timer.updateExportRoute(message.getRoute());

        } else {
            timer.reset(currentTime, message.getRouteReference());

            int expirationTime = timer.getExpirationTime();
            boolean exportedUpdate = false; // indicates if a message other than an withdrawal was exported

            // export the route to each in-neighbor
            for (Link link : exportingRouter.getInLinks()) {

                int exportTime;
                Attribute extendedAttribute = link.getLabel().extend(link, message.getRoute().getAttribute());
                if (extendedAttribute == invalidAttr()) {
                    // withdrawals are exported immediately
                    exportTime = currentTime;

                    // FIXME fire advertisement event here
                } else {
                    // advertisements that are not withdrawals must wait for the timer to expire
                    exportTime = expirationTime;
                    exportedUpdate = true;
                }

                export(message, exportTime);
            }

            if (exportedUpdate) {
                // only fire this event if there was an advertisement that was not an withdrawal

                // fire an Advertisement Event with the time corresponding to the expiration time
                // the expiration time is the time when the event actually occurs
                // FIXME fire advertisement event here
            }
        }

    }

    protected void export(Message message, int exportTime) {
        // add the route to the scheduler with the given export time
        message.setArrivalTime(exportTime);
        scheduler.schedule(message);

        // FIXME fire export event here
    }

}
