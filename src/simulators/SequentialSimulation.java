package simulators;

import core.Destination;
import core.Link;
import core.Router;
import io.reporters.BasicReporter;
import org.apache.commons.io.FilenameUtils;
import simulators.basic.BasicDataCollector;
import simulators.basic.BasicDataset;

import java.io.IOException;

public class SequentialSimulation {

    private final BasicDataCollector dataCollector = new BasicDataCollector();
    private final BasicReporter reporter = new BasicReporter();

    public SequentialSimulation(Simulator simulator) {
        reporter.setReportDirectory(simulator.getReportDestination());
    }

    public void setup(SequentialExperiment experiment, Simulator simulator) {
        // does nothing!
    }

    private void setup() {
        // clear all data from last simulation - ensure collector is clean
        dataCollector.clear();
    }

    public void run(SequentialExperiment experiment, Simulator simulator) throws IOException {

        String topologyName = FilenameUtils.removeExtension(simulator.getTopologyFile().getName());
        Destination[] permutation = experiment.getCurrentSequence();

        for (Destination destination : permutation) {
            setup();

            String description = String.format("permutation %d/%d iteration %d/%d",
                    experiment.getCurrentPermutation() + 1, experiment.getPermutationCount(),
                    experiment.getCurrentRepetition() + 1, experiment.getRepetitionCount());

            simulator.simulate(destination, description);
            report(experiment.getCurrentPermutation(), experiment.getCurrentRepetition(),
                    destination.getId(), topologyName);

            cleanup(simulator, destination);
        }

    }

    public void report(SequentialExperiment experiment, Simulator simulator) throws IOException {
        // does nothing! The reporting is done for each destination!
    }

    private void report(int permutationId, int repetitionNumber, int destinationId,
                        String topologyName) throws IOException {

        String defaultReportFilename = String.format("%s_%d_%d.csv",
                topologyName, destinationId, permutationId);

        reporter.report(defaultReportFilename, repetitionNumber, (BasicDataset) dataCollector.getDataset());
    }

    public void cleanup(SequentialExperiment experiment, Simulator simulator) {

        // reset the routers
        for (Router router : simulator.getTopology().getRouters()) {

            // reset router's links
            for (Link link : router.getInLinks()) {
                link.setTurnedOff(false);
                link.setLastArrivalTime(0);
            }

        }

    }

    private void cleanup(Simulator simulator, Destination destination) {

        for (Router router : simulator.getTopology().getRouters()) {

            // reset router's links
            for (Link link : router.getInLinks()) {
                link.setLastArrivalTime(0);
            }

            router.getTable().reset();
            router.getMRAITimer().clear();
        }

        // guarantee the arrival times of the destination's in-links are reset to 0
        for (Link link : destination.getInLinks()) {
            link.setLastArrivalTime(0);
        }

        destination.getMRAITimer().clear();

    }

}
