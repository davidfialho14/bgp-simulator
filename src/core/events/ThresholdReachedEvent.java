package core.events;

/**
 * This event are fired if the simulation time reaches the threshold defined for the simulation.
 */
public class ThresholdReachedEvent extends AbstractSimulationEvent {

    private final int threshold;

    public ThresholdReachedEvent(int time, int threshold) {
        super(time);
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    @Override
    public String toString() {
        return "ThresholdReachedEvent{}";
    }

}
