package core.events;

/**
 * Tagging interface that all simulation events must extend. All simulation events must record the time
 * instant at which they are generated.
 */
public interface SimulationEvent {

    /**
     * Returns the instant of time in which the event was generated.
     *
     * @return the instant of time in which the event was generated.
     */
    long getTimeInstant();

}
