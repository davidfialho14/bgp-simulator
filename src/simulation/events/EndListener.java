package simulation.events;

/**
 * Listener interface for receiving end events. The class that is interested in processing a end event
 * implements this interface, and the object created with that class is registered with a component, using
 * the component's addEndListener method. When the end event occurs, that object's onEnded() method
 * is invoked.
 */
public interface EndListener extends SimulationEventListener {

    /**
     * Invoked when a end event occurs.
     *
     * @param event end event that occurred.
     */
    void onEnded(EndEvent event);
}
