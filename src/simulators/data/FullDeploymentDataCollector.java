package simulators.data;

import io.reporters.Reporter;
import simulation.events.ExportEvent;

import java.io.IOException;

public class FullDeploymentDataCollector extends BasicDataCollector {

    private boolean deployed = false;
    protected FullDeploymentDataSet fullDeploymentDataSet = new FullDeploymentDataSet();

    /**
     * Dumps the current data to the reporter.
     *
     * @param reporter reporter to dump data to.
     */
    @Override
    public void dump(Reporter reporter) throws IOException {
        reporter.dump(basicDataSet, fullDeploymentDataSet);
    }

    /**
     * Clears all data from a data collector.
     */
    @Override
    public void clear() {
        super.clear();
        deployed = false;
        fullDeploymentDataSet.clear();
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        super.onExported(event);
        if (deployed) {
            fullDeploymentDataSet.addMessage();
        }
    }
}
