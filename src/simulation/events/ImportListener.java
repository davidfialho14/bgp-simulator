package simulation.events;

/**
 * Listener interface for receiving import events. The class that is interested in processing a import event
 * implements this interface, and the object created with that class is registered with a component, using
 * the component's addLearnListener method. When the import event occurs, that object's onImported() method
 * is invoked.
 */
public interface ImportListener extends SimulationEventListener {

    /**
     * Invoked when a import event occurs.
     *
     * @param event import event that occurred.
     */
    void onImported(ImportEvent event);
}
