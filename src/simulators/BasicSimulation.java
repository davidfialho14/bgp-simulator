package simulators;

import core.Link;
import core.Router;
import io.reporters.BasicReporter;
import org.apache.commons.io.FilenameUtils;
import simulators.basic.BasicDataCollector;
import simulators.basic.BasicDataset;

import java.io.IOException;

public class BasicSimulation {

    private final BasicDataCollector dataCollector = new BasicDataCollector();
    private final BasicReporter reporter = new BasicReporter();

    public BasicSimulation(Simulator simulator) {
        reporter.setReportDirectory(simulator.getReportDestination());
    }

    public void setup(BasicExperiment experiment, Simulator simulator) {
        // clear all data from last simulation - ensure collector is clean
        dataCollector.clear();
    }

    public void run(BasicExperiment experiment, Simulator simulator) {

        String description = String.format("iteration %d/%d",
                experiment.getCurrentRepetition() + 1, experiment.getRepetitionCount());

        simulator.simulate(experiment.getDestination(), description);
    }

    public void report(BasicExperiment experiment, Simulator simulator) throws IOException {

        String topologyName = FilenameUtils.removeExtension(simulator.getTopologyFile().getName());
        String defaultReportFilename = String.format("%s_%d.csv", topologyName,
                experiment.getDestination().getId());

        reporter.report(
                defaultReportFilename,
                experiment.getCurrentRepetition(),
                (BasicDataset) dataCollector.getDataset()
        );
    }

    public void cleanup(BasicExperiment experiment, Simulator simulator) {

        // reset the routers
        for (Router router : simulator.getTopology().getRouters()) {

            router.getTable().reset();
            router.getMRAITimer().clear();

            // reset router's links
            for (Link link : router.getInLinks()) {
                link.setTurnedOff(false);
                link.setLastArrivalTime(0);
            }

        }

        // guarantee the arrival times of the destination's in-links are reset to 0
        for (Link link : experiment.getDestination().getInLinks()) {
            link.setLastArrivalTime(0);
        }

        experiment.getDestination().getMRAITimer().clear();

    }

}
