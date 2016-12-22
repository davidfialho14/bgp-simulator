package core.events;

/**
 * Abstract simulation event stores the time of an event.
 */
public class AbstractSimulationEvent implements SimulationEvent {

    private final int time;

    public AbstractSimulationEvent(int time) {
        this.time = time;
    }

    /**
     * Returns the instant of time in which the event was generated.
     *
     * @return the instant of time in which the event was generated.
     */
    @Override
    public int getTimeInstant() {
        return time;
    }

}
