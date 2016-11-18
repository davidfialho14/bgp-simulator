package v2.core.events;

import v2.core.schedulers.Scheduler;

/**
 * Events generated when the simulation starts.
 */
public class StartEvent extends AbstractSimulationEvent {

    private final Long seed;

    /**
     * Creates a start event with the seed used for the simulation instance.
     *
     * @param scheduler scheduler being used for the simulation
     */
    public StartEvent(long time, Scheduler scheduler) {
        super(time);
        seed = scheduler.getSeed();
    }

    /**
     * Returns the seed used for the started simulation instance if the simulation uses random delays, otherwise the
     * returned value is null.
     *
     * @return seed used for the started simulation instance or null if there is no seed available.
     */
    public Long getSeed() {
        return seed;
    }

    @Override
    public String toString() {
        return "StartEvent{}";
    }

}
