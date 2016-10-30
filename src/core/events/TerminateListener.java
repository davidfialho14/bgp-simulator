package core.events;

/**
 * Listener interface for receiving terminate events. The class that is interested in processing a terminate
 * event implements this interface, and the object created with that class is registered with a component,
 * using the component's addTerminateListener method. When the terminate event occurs, that object's
 * onTerminated() method is invoked.
 */
public interface TerminateListener extends SimulationEventListener {

    /**
     * Invoked when a terminate event occurs.
     *
     * @param event terminate event that occurred.
     */
    void onTerminated(TerminateEvent event);
}
