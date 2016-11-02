package simulators.timeddeployment;

import core.Engine;
import core.events.ExportEvent;
import core.events.ExportListener;
import io.reporters.Reporter;
import registers.Registration;
import simulators.DataCollector;
import simulators.Dataset;
import simulators.basic.BasicDataCollector;
import simulators.basic.BasicDataset;

import java.io.IOException;

import static registers.Registration.noRegistration;
import static registers.Registration.registrationFor;

/**
 * Collects all data that can be stored in a timed deployment dataset.
 */
public class TimedDeploymentDataCollector implements DataCollector, ExportListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final TimedDeploymentDataset timedDeploymentDataset = new TimedDeploymentDataset();
    private final BasicDataCollector basicDataCollector = new BasicDataCollector();

    private Registration registration = noRegistration();
    private boolean deployed = false;   // flag to indicate if the deployment already took place

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
     * Reports the current collected data using the given reporter implementation. Calls the reporter's
     * writeData(BasicDataset, TimedDeploymentDataset).
     *
     * @param reporter reporter implementation to be used.
     */
    @Override
    public void report(Reporter reporter) throws IOException {
        reporter.writeData((BasicDataset) basicDataCollector.getDataset(), timedDeploymentDataset);
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

        basicDataCollector.register(engine);
        registration = registrationFor(engine, this)
                .exportEvents()
                .register();
    }

    /**
     * Unregisters the collector as an export, detect, and time listener of the engine. If the collector is not
     * registered the method will take no effect.
     */
    @Override
    public void unregister() {
        registration.cancel();
        basicDataCollector.unregister();
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

}
