package simulators.gradualdeployment;

import core.Engine;
import core.TimeListener;
import core.events.DetectEvent;
import core.events.DetectListener;
import core.events.ExportEvent;
import core.events.ExportListener;
import core.topology.ConnectedNode;
import registers.Registration;
import simulators.DataCollector;
import simulators.Dataset;
import simulators.basic.BasicDataCollector;

import static registers.Registration.noRegistration;
import static registers.Registration.registrationFor;

/**
 * Adds to the basic data collector, the collection of data from a gradual deployment simulation.
 * Collects the nodes which have deployed a new protocol during one simulation instance.
 */
public class GradualDeploymentDataCollector implements DataCollector, ExportListener, DetectListener, TimeListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final GradualDeploymentDataset gradualDeploymentDataSet;
    private final BasicDataCollector basicDataCollector;
    private Registration registration = noRegistration();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates new gradual deployment data collector with an empty dataset.
     */
    public GradualDeploymentDataCollector() {
        gradualDeploymentDataSet = new GradualDeploymentDataset();

        // basic data collector stores data to the underlying basic dataset of the timed deployment dataset
        basicDataCollector = new BasicDataCollector(gradualDeploymentDataSet.getBasicDataset());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Notifies the collector that a new node has deployed a new protocol. Must be invoked when a new node deploys a
     * new protocol.
     *
     * @param node deploying node.
     */
    public void notifyDeployment(ConnectedNode node) {
        gradualDeploymentDataSet.setAsDeployingNode(node);
    }

    /**
     * Clears all data that has been collected.
     */
    @Override
    public void clear() {
        gradualDeploymentDataSet.clear();
    }

    /**
     * Gives access to the data set storing the collected data.
     *
     * @return a timed deployment dataset instance with the collected data.
     */
    @Override
    public Dataset getDataset() {
        return gradualDeploymentDataSet;
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
     *  Public Interface - Delegate methods for basic dataset (unchanged)
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Invoked the time property of the engine changes
     *
     * @param newTime the new time value
     */
    @Override
    public void onTimeChange(long newTime) {
        basicDataCollector.onTimeChange(newTime);
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        basicDataCollector.onDetected(event);
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        basicDataCollector.onExported(event);
    }
}
