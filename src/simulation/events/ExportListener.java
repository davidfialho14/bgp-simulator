package simulation.events;

/**
 * Listener interface for receiving export events. The class that is interested in processing a export event
 * implements this interface, and the object created with that class is registered with a component, using
 * the component's addExportListener method. When the export event occurs, that object's onExported() method
 * is invoked.
 */
public interface ExportListener extends SimulationEventListener {

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    void onExported(ExportEvent event);
}
