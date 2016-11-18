package v2.core.events;

/**
 * Events generated when the simulation ends.
 */
public class EndEvent extends AbstractSimulationEvent {

    public EndEvent(long time) {
        super(time);
    }

    @Override
    public String toString() {
        return "EndEvent{}";
    }

}
