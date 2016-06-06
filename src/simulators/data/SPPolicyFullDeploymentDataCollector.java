package simulators.data;

import io.reporters.Reporter;
import policies.Path;
import simulation.events.DetectEvent;

import java.io.IOException;

import static simulators.data.SPPolicyBasicDataCollector.isNegative;

public class SPPolicyFullDeploymentDataCollector extends FullDeploymentDataCollector {

    protected SPPolicyDataSet spPolicyDataSet = new SPPolicyDataSet();

    /**
     * Dumps the current data to the reporter.
     *
     * @param reporter reporter to dump data to.
     */
    @Override
    public void dump(Reporter reporter) throws IOException {
        reporter.dump(basicDataSet, fullDeploymentDataSet, spPolicyDataSet);
    }

    /**
     * Clears all data from a data collector.
     */
    @Override
    public void clear() {
        super.clear();
        spPolicyDataSet.clear();
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        Path cycle = DetectEvent.getCycle(event);

        boolean isFalsePositive = isNegative(cycle);
        if (isFalsePositive) {
            spPolicyDataSet.addFalsePositive();
        }

        basicDataSet.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), cycle, isFalsePositive));
    }

}
