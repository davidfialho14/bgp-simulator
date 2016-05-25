package simulation.events;

/**
 * Listener interface for receiving select events. The class that is interested in processing a select event
 * implements this interface, and the object created with that class is registered with a component, using
 * the component's addSelectListener method. When the select event occurs, that object's onSelected() method
 * is invoked.
 */
public interface SelectListener extends SimulationEventListener {

    /**
     * Invoked when a select event occurs.
     *
     * @param event select event that occurred.
     */
    void onSelected(SelectEvent event);
}
