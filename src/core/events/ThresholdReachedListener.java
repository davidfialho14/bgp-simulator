package core.events;

/**
 * Listener interface for receiving terminate events. The class that is interested in processing a terminate
 * event implements this interface, and the object created with that class is registered with a component,
 * using the component's addThresholdReachedListener method. When the terminate event occurs, that object's
 * onThresholdReached() method is invoked.
 */
public interface ThresholdReachedListener extends SimulationEventListener {

    /**
     * Invoked when a terminate event occurs.
     *
     * @param event terminate event that occurred.
     */
    void onThresholdReached(ThresholdReachedEvent event);
}
