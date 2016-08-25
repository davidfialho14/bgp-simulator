package newsimulators.basic;

import core.Engine;
import core.TimeListener;
import core.events.DetectEvent;
import core.events.DetectListener;
import core.events.ExportEvent;
import core.events.ExportListener;
import newsimulators.DataCollector;
import newsimulators.Dataset;
import registers.Registration;
import simulators.data.Detection;

import static registers.Registration.noRegistration;
import static registers.Registration.registrationFor;

/**
 * Collects all data that can be stored in a basic dataset.
 */
public class BasicDataCollector implements DataCollector, ExportListener, DetectListener, TimeListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected final BasicDataset dataset;
    private Registration registration = noRegistration();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates new basic data collector with an empty basic dataset.
     */
    public BasicDataCollector() {
        dataset = new BasicDataset();
    }

    /**
     * Creates a new basic dataset with the given basic dataset. The collector will store the collected data in the
     * given dataset. This can be used by subclasses to define their own basic dataset
     *
     * @param basicDataset basic dataset to store the collected data.
     */
    public BasicDataCollector(BasicDataset basicDataset) {
        dataset = basicDataset;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Data Collector Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers the collector as an export, detect, and time listener of the engine.
     *
     * @param engine engine used for simulating.
     * @throws IllegalStateException if the data collector is already registered.
     */
    @Override
    public void register(Engine engine) throws IllegalStateException {
        if (registration.isRegistered()) {
            throw new IllegalStateException("can not register collector that is already registered");
        }

        registration = registrationFor(engine, this)
                .exportEvents()
                .detectEvents()
                .timeEvents()
                .register();
    }

    /**
     * Unregisters the collector as an export, detect, and time listener of the engine. If the collector is not
     * registered the method will take no effect.
     */
    @Override
    public void unregister() {
        registration.cancel();
    }

    /**
     * Clears all data that has been collected.
     */
    @Override
    public void clear() {
        dataset.clear();
    }

    /**
     * Gives access to the data set storing the collected data. The dataset implementation returned depends on the
     * collector implementation.
     *
     * @return a dataset instance with the collected data.
     */
    @Override
    public Dataset getDataset() {
        return dataset;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Event Handling Methods - This methods are called during the simulation and should not be
     *  called elsewhere.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Invoked the time property of the engine changes
     *
     * @param newTime the new time value
     */
    @Override
    public void onTimeChange(long newTime) {
        dataset.setSimulationTime(newTime);
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        dataset.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), DetectEvent.getCycle(event)));
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        dataset.addMessage();
    }

}
