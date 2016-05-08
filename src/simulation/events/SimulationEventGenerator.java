package simulation.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates simulation events. Listeners can be registered to listen for any type of simulation event and
 * are notified when the respective event occurs.
 */
public class SimulationEventGenerator {

    private List<LearnListener> learnListeners = new ArrayList<>();     // stores all registered learn listeners
    private List<ImportListener> importListeners = new ArrayList<>();   // stores all registered import listeners
    private List<SelectListener> selectListeners = new ArrayList<>();   // stores all registered select listeners

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
        // events are immutable so the same event object can be passed to all listeners
        learnListeners.forEach(listener -> listener.onLearned(event));
    }

    /**
     * Registers a new import listener.
     *
     * @param listener import listener to register.
     */
    public void addImportListener(ImportListener listener) {
        importListeners.add(listener);
    }

    /**
     * Unregisters a new import listener.
     *
     * @param listener import listener to unregister.
     */
    public void removeImportListener(ImportListener listener) {
        importListeners.remove(listener);
    }

    /**
     * Fires a import event, notifying all registered listeners by invoking their onImported() method.
     *
     * @param event event to fire.
     */
    public void fireImportEvent(ImportEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        importListeners.forEach(listener -> listener.onImported(event));
    }

    /**
     * Registers a new select listener.
     *
     * @param listener select listener to register.
     */
    public void addSelectListener(SelectListener listener) {
        selectListeners.add(listener);
    }

    /**
     * Unregisters a new select listener.
     *
     * @param listener select listener to unregister.
     */
    public void removeSelectListener(SelectListener listener) {
        selectListeners.remove(listener);
    }

    /**
     * Fires a select event, notifying all registered listeners by invoking their onSelected() method.
     *
     * @param event event to fire.
     */
    public void fireSelectEvent(SelectEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        selectListeners.forEach(listener -> listener.onSelected(event));
    }
}
