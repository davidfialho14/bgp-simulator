package simulators.timeddeployment;

import core.Engine;
import core.TimeListener;
import core.events.DetectEvent;
import core.events.DetectListener;
import core.events.ExportEvent;
import core.events.ExportListener;
import registers.Registration;
import simulators.DataCollector;
import simulators.Dataset;
import simulators.basic.BasicDataCollector;

import static registers.Registration.noRegistration;
import static registers.Registration.registrationFor;

/**
 * Collects all data that can be stored in a timed deployment dataset.
 */
public class TimedDeploymentDataCollector implements DataCollector, ExportListener, DetectListener, TimeListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final TimedDeploymentDataset timedDeploymentDataset;
    private final BasicDataCollector basicDataCollector;

    private Registration registration = noRegistration();
    private boolean deployed = false;   // flag to indicate if the deployment already took place

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates new timed deployment data collector with an empty dataset.
     */
    public TimedDeploymentDataCollector() {
        timedDeploymentDataset = new TimedDeploymentDataset();

        // basic data collector stores data to the underlying basic dataset of the timed deployment dataset
        basicDataCollector = new BasicDataCollector(timedDeploymentDataset.getBasicDataset());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Tells the collector the deployment already happened and that it may start collecting data.
     *
     * @param deployed true to indicate the deployment took place.
     */
    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    /**
     * Clears all data that has been collected.
     */
    @Override
    public void clear() {
        timedDeploymentDataset.clear();
        deployed = false;
    }

    /**
     * Gives access to the data set storing the collected data.
     *
     * @return a timed deployment dataset instance with the collected data.
     */
    @Override
    public Dataset getDataset() {
        return timedDeploymentDataset;
    }

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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Event Handling Methods - This methods are called during the simulation and should not be
     *  called elsewhere.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */

    public void onExported(ExportEvent event) {
        basicDataCollector.onExported(event);

        if (deployed) {
            timedDeploymentDataset.addMessageAfterDeployment();
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Delegate methods for basic dataset (unchanged)
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Invoked the time property of the engine changes
     *
     * @param newTime the new time value
     */
    public void onTimeChange(long newTime) {
        basicDataCollector.onTimeChange(newTime);
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    public void onDetected(DetectEvent event) {
        basicDataCollector.onDetected(event);
    }
}
