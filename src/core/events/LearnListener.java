package core.events;

/**
 * Listener interface for receiving learn events. The class that is interested in processing a learn event
 * implements this interface, and the object created with that class is registered with a component, using
 * the component's addLearnListener method. When the learn event occurs, that object's onLearned() method
 * is invoked.
 */
public interface LearnListener extends SimulationEventListener {

    /**
     * Invoked when a learn event occurs.
     *
     * @param event learn event that occurred.
     */
    void onLearned(LearnEvent event);
}
