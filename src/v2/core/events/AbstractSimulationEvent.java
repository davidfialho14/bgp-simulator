package v2.core.events;

/**
 * Abstract simulation event stores the time of an event.
 */
public class AbstractSimulationEvent implements SimulationEvent {

    private final long time;

    public AbstractSimulationEvent(long time) {
        this.time = time;
    }

    /**
     * Returns the instant of time in which the event was generated.
     *
     * @return the instant of time in which the event was generated.
     */
    @Override
    public long getTimeInstant() {
        return time;
    }

}
