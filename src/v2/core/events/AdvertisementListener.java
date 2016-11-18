package v2.core.events;

/**
 * Listener interface for receiving advertisement events. The class that is interested in processing an
 * advertisement event implements this interface, and the object created with that class is registered with
 * a component, using the component's addAdvertisementListener method. When the export event occurs, that
 * object's onAdvertised() method is invoked.
 */
public interface AdvertisementListener extends SimulationEventListener {

    /**
     * Invoked when a advertisement event occurs.
     *
     * @param event advertisement event that occurred.
     */
    void onAdvertised(AdvertisementEvent event);
}
