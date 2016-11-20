package core.events;

/**
 * Listener interface for receiving detect events. The class that is interested in processing a detect event
 * implements this interface, and the object created with that class is registered with a component, using
 * the component's addDetectListener method. When the detect event occurs, that object's onDetected() method
 * is invoked.
 */
public interface DetectListener extends SimulationEventListener {

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    void onDetected(DetectEvent event);
}
