package simulation.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates simulation events. Listeners can be registered to listen for any type of simulation event and
 * are notified when the respective event occurs.
 */
public class SimulationEventGenerator {

    private List<LearnListener> learnListeners = new ArrayList<>(); // stores all registered learn listeners

    /**
     * Registers a new learn listener.
     *
     * @param listener learn listener to register.
     */
    public void addLearnListener(LearnListener listener) {
        learnListeners.add(listener);
    }

    /**
     * Unregisters a new learn listener.
     *
     * @param listener learn listener to unregister.
     */
    public void removeLearnListener(LearnListener listener) {
        learnListeners.remove(listener);
    }

    /**
     * Fires a learn event, notifying all registered listeners by invoking their onLearned() method.
     *
     * @param event event to fire.
     */
    public void fireLearnEvent(LearnEvent event) {
        /*
            By having the learn event as a parameter instead of the necessary parameters to build a learn event
            makes changing the event constructor signature easier. If this method received the parameters to build
            a learn event every time the learn event constructor's parameters changed the signature of this method
            also would have to change.
         */

        // events are immutable so the same event object can be passed to all listeners
        learnListeners.forEach(listener -> listener.onLearned(event));
    }
}
