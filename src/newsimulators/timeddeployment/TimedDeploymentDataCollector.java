package newsimulators.timeddeployment;

import core.events.ExportEvent;
import newsimulators.basic.BasicDataCollector;

/**
 * Collects all data that can be stored in a timed deployment dataset.
 */
public class TimedDeploymentDataCollector extends BasicDataCollector {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final TimedDeploymentDataset timedDeploymentDataset;

    private boolean deployed = false;   // flag to indicate if the deployment already took place

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates new basic data collector with an empty basic dataset.
     */
    public TimedDeploymentDataCollector() {
        // the basic data collector will also collect to the timed deployment dataset
        super(new TimedDeploymentDataset());
        this.timedDeploymentDataset = (TimedDeploymentDataset) this.dataset;
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
    @Override
    public void onExported(ExportEvent event) {
        super.onExported(event);

        if (deployed) {
            timedDeploymentDataset.addMessageAfterDeployment();
        }
    }

}
