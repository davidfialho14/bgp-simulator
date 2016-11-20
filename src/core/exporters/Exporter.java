package core.exporters;

import core.Destination;
import core.Policy;
import core.Route;
import core.Router;
import core.schedulers.Scheduler;

/**
 * An exporter is responsible for the export operation of the simulation engine. Performs two different
 * operations: the generic exporting of one message and the exporting of the destination router's self-route.
 */
public interface Exporter {

    /**
     * Returns the scheduler used by the exporter to put the messages.
     *
     * @return the scheduler used by the exporter to put the messages.
     */
    Scheduler getScheduler();

    /**
     * Exports the given message. This consists of exporting a message to all of the in-neighbors of the
     * target router of the message.
     *
     * @param exportingRouter   router exporting the route.
     * @param route             route to export.
     * @param currentTime       current simulation time.
     */
    void export(Router exportingRouter, Route route, int currentTime);

    /**
     * Exports the self route for the given destination to all of its in-neighbors.
     *
     * @param destination   destination to export self routes for.
     * @param policy        policy used to get the self route.
     */
    void export(Destination destination, Policy policy);

}
