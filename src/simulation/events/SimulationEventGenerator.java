package simulation.events;

import network.Link;
import simulation.Route;

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
     * @param learnLink link from which the route was learned.
     * @param learnedRoute route learned.
     */
    public void fireLearnEvent(Link learnLink, Route learnedRoute) {
        // events are immutable so the same event object can be passed to all listeners
        LearnEvent event = new LearnEvent(learnLink, learnedRoute);
        learnListeners.forEach(listener -> listener.onLearned(event));
    }
}
