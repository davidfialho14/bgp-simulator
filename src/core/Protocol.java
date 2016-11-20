package core;

import core.exporters.Exporter;

/**
 * Base interface for any protocol implementation. All protocol implementations must implement this interface.
 */
public interface Protocol {

    /**
     * Processes a message that arrives at the given router.
     *
     * @param message   message to process.
     * @param exporter  exporter used to export a route.
     */
    void process(Message message, Exporter exporter);

}
