package simulation.events;

/**
 * Listener interface for receiving start events. The class that is interested in processing a start event
 * implements this interface, and the object created with that class is registered with a component, using
 * the component's addStartListener method. When the start event occurs, that object's onStarted() method
 * is invoked.
 */
public interface StartListener extends SimulationEventListener {

    /**
     * Invoked when a start event occurs.
     *
     * @param event start event that occurred.
     */
    void onStarted(StartEvent event);
}
