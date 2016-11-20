package core.events;

/**
 * Events generated when the simulation ends.
 */
public class EndEvent extends AbstractSimulationEvent {

    public EndEvent(int time) {
        super(time);
    }

    @Override
    public String toString() {
        return "EndEvent{}";
    }

}
