package v2.core;

/**
 * Base interface for any protocol implementation. All protocol implementations must implement this interface.
 * Protocols might have state and therefore must be unique for each router and need to be reset before each
 * simulation.
 */
public interface Protocol {

    /**
     * Processes a message that arrives at the given router.
     *
     * @param router    router receiving the message.
     * @param message   message to process.
     * @param exporter  exporter used to export a route.
     */
    void process(Router router, Message message, Exporter exporter);

    /**
     * Resets the state of the protocol. Protocols must be reset before each simulation.
     */
    void reset();

}
