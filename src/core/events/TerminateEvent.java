package core.events;

/**
 * Terminate events are generated when the protocol reaches a stable state (terminates).
 */
public class TerminateEvent implements SimulationEvent {

    @Override
    public String toString() {
        return "TerminateEvent{}";
    }

}
