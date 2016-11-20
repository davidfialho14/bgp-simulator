package core.events;

/**
 * Terminate events are generated when the protocol reaches a stable state (terminates).
 */
public class TerminateEvent extends AbstractSimulationEvent {

    public TerminateEvent(int time) {
        super(time);
    }

    @Override
    public String toString() {
        return "TerminateEvent{}";
    }

}
