package core.events;

/**
 * Terminate events are generated when the protocol reaches a stable state (terminates).
 */
public class TerminateEvent extends AbstractSimulationEvent  {

    public TerminateEvent(long time) {
        super(time);
    }

    @Override
    public String toString() {
        return "TerminateEvent{}";
    }

}
