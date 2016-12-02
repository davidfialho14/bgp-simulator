package core.events;


import java.util.ArrayList;
import java.util.List;

/**
 * The event notifier is a singleton class that is used to notify registered listeners of simulation
 * events. Listeners should register to receive notifications of occurring events. Any component can use
 * the notifier to notify an event.
 */
public class EventNotifier {

    // single instance of teh notifier
    private static final EventNotifier NOTIFIER = new EventNotifier();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Lists with the registered listeners. There is one list for each type of event.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private List<StartListener> startListeners = new ArrayList<>();
    private List<EndListener> endListeners = new ArrayList<>();
    private List<LearnListener> learnListeners = new ArrayList<>();
    private List<ImportListener> importListeners = new ArrayList<>();
    private List<SelectListener> selectListeners = new ArrayList<>();
    private List<ExportListener> exportListeners = new ArrayList<>();
    private List<AdvertisementListener> advertisementListeners = new ArrayList<>();
    private List<DetectListener> detectListeners = new ArrayList<>();
    private List<TerminateListener> terminateListeners = new ArrayList<>();
    private List<ThresholdReachedListener> thresholdReachedListeners = new ArrayList<>();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private EventNotifier() { } // use factory method!

    /**
     * Gives access to the event notifier.
     *
     * @return the event notifier.
     */
    public static EventNotifier eventNotifier() {
        return NOTIFIER;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Basic Operation (Private)
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private <T extends SimulationEventListener> void add(List<T> listeners, T listener) {
        listeners.add(listener);
    }

    private <T extends SimulationEventListener> void remove(List<T> listeners, T listener) {
        listeners.remove(listener);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Start Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new start listener.
     *
     * @param listener start listener to register.
     */
    public void addStartListener(StartListener listener) {
        add(startListeners, listener);
    }

    /**
     * Unregisters a new start listener.
     *
     * @param listener start listener to unregister.
     */
    public void removeStartListener(StartListener listener) {
        remove(startListeners, listener);
    }

    /**
     * Notifies all registered listeners of a start event  by invoking their onStarted() method.
     *
     * @param event event to notify.
     */
    public void notifyStartEvent(StartEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        startListeners.forEach(listener -> listener.onStarted(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  End Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new end listener.
     *
     * @param listener end listener to register.
     */
    public void addEndListener(EndListener listener) {
        add(endListeners, listener);
    }

    /**
     * Unregisters a new end listener.
     *
     * @param listener end listener to unregister.
     */
    public void removeEndListener(EndListener listener) {
        remove(endListeners, listener);
    }

    /**
     * Notifies all registered listeners of a end event  by invoking their onEnded() method.
     *
     * @param event event to notify.
     */
    public void notifyEndEvent(EndEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        endListeners.forEach(listener -> listener.onEnded(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Import Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new import listener.
     *
     * @param listener import listener to register.
     */
    public void addImportListener(ImportListener listener) {
        add(importListeners, listener);
    }

    /**
     * Unregisters a new import listener.
     *
     * @param listener import listener to unregister.
     */
    public void removeImportListener(ImportListener listener) {
        remove(importListeners, listener);
    }

    /**
     * Notifies all registered listeners of a import event  by invoking their onImported() method.
     *
     * @param event event to notify.
     */
    public void notifyImportEvent(ImportEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        importListeners.forEach(listener -> listener.onImported(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Learn Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new learn listener.
     *
     * @param listener learn listener to register.
     */
    public void addLearnListener(LearnListener listener) {
        add(learnListeners, listener);
    }

    /**
     * Unregisters a new learn listener.
     *
     * @param listener learn listener to unregister.
     */
    public void removeLearnListener(LearnListener listener) {
        remove(learnListeners, listener);
    }

    /**
     * Notifies all registered listeners of a learn event  by invoking their onLearned() method.
     *
     * @param event event to notify.
     */
    public void notifyLearnEvent(LearnEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        learnListeners.forEach(listener -> listener.onLearned(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Select Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new select listener.
     *
     * @param listener select listener to register.
     */
    public void addSelectListener(SelectListener listener) {
        add(selectListeners, listener);
    }

    /**
     * Unregisters a new select listener.
     *
     * @param listener select listener to unregister.
     */
    public void removeSelectListener(SelectListener listener) {
        remove(selectListeners, listener);
    }

    /**
     * Notifies all registered listeners of a select event  by invoking their onSelected() method.
     *
     * @param event event to notify.
     */
    public void notifySelectEvent(SelectEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        selectListeners.forEach(listener -> listener.onSelected(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Export Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new export listener.
     *
     * @param listener export listener to register.
     */
    public void addExportListener(ExportListener listener) {
        add(exportListeners, listener);
    }

    /**
     * Unregisters a new export listener.
     *
     * @param listener export listener to unregister.
     */
    public void removeExportListener(ExportListener listener) {
        remove(exportListeners, listener);
    }

    /**
     * Notifies all registered listeners of a export event  by invoking their onExported() method.
     *
     * @param event event to notify.
     */
    public void notifyExportEvent(ExportEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        exportListeners.forEach(listener -> listener.onExported(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Advertisement Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new advertisement listener.
     *
     * @param listener advertisement listener to register.
     */
    public void addAdvertisementListener(AdvertisementListener listener) {
        add(advertisementListeners, listener);
    }

    /**
     * Unregisters a new advertisement listener.
     *
     * @param listener advertisement listener to unregister.
     */
    public void removeAdvertisementListener(AdvertisementListener listener) {
        remove(advertisementListeners, listener);
    }

    /**
     * Notifies all registered listeners of a advertisement event  by invoking their onAdvertised() method.
     *
     * @param event event to notify.
     */
    public void notifyAdvertisementEvent(AdvertisementEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        advertisementListeners.forEach(listener -> listener.onAdvertised(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Detect Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new detect listener.
     *
     * @param listener detect listener to register.
     */
    public void addDetectListener(DetectListener listener) {
        add(detectListeners, listener);
    }

    /**
     * Unregisters a new detect listener.
     *
     * @param listener detect listener to unregister.
     */
    public void removeDetectListener(DetectListener listener) {
        remove(detectListeners, listener);
    }

    /**
     * Notifies all registered listeners of a detect event  by invoking their onDetected() method.
     *
     * @param event event to notify.
     */
    public void notifyDetectEvent(DetectEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        detectListeners.forEach(listener -> listener.onDetected(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Terminate Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new terminate listener.
     *
     * @param listener terminate listener to register.
     */
    public void addTerminateListener(TerminateListener listener) {
        add(terminateListeners, listener);
    }

    /**
     * Unregisters a new terminate listener.
     *
     * @param listener terminate listener to unregister.
     */
    public void removeTerminateListener(TerminateListener listener) {
        remove(terminateListeners, listener);
    }

    /**
     * Notifies all registered listeners of a terminate event  by invoking their onTerminated() method.
     *
     * @param event event to notify.
     */
    public void notifyTerminateEvent(TerminateEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        terminateListeners.forEach(listener -> listener.onTerminated(event));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  ThresholdReached Event
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers a new threshold reached listener.
     *
     * @param listener threshold reached listener to register.
     */
    public void addThresholdReachedListener(ThresholdReachedListener listener) {
        add(thresholdReachedListeners, listener);
    }

    /**
     * Unregisters a new threshold reached listener.
     *
     * @param listener threshold reached listener to unregister.
     */
    public void removeThresholdReachedListener(ThresholdReachedListener listener) {
        remove(thresholdReachedListeners, listener);
    }

    /**
     * Notifies all registered listeners of a threshold reached event  by invoking their onThresholdReached() method.
     *
     * @param event event to notify.
     */
    public void notifyThresholdReachedEvent(ThresholdReachedEvent event) {
        // events are immutable so the same event object can be passed to all listeners
        thresholdReachedListeners.forEach(listener -> listener.onThresholdReached(event));
    }

    /**
     * Removes all listeners from the event notifier.
     */
    public void clearAll() {
        startListeners.clear();
        endListeners.clear();
        importListeners.clear();
        learnListeners.clear();
        selectListeners.clear();
        exportListeners.clear();
        advertisementListeners.clear();
        detectListeners.clear();
        terminateListeners.clear();
        thresholdReachedListeners.clear();
    }

}
