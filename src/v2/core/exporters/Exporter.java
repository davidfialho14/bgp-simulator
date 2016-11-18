package v2.core.exporters;

import v2.core.Destination;
import v2.core.Message;
import v2.core.Policy;

/**
 * An exporter is responsible for the export operation of the simulation engine. Performs two different
 * operations: the generic exporting of one message and the exporting of the destination router's self-route.
 */
public interface Exporter {

    /**
     * Exports the given message. This consists of exporting a message to all of the in-neighbors of the
     * target router of the message (this is the source router of the message's link).
     *
     * @param message message to export.
     */
    void export(Message message);

    /**
     * Exports the self route for the given destination to all of its in-neighbors.
     *
     * @param destination   destination to export self routes for.
     * @param policy        policy used to get the self route.
     */
    void export(Destination destination, Policy policy);

}
