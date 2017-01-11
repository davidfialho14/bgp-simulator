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

    public SequentialSimulation(SimulatorNew simulator) {
        reporter.setReportDirectory(simulator.getReportDestination());
    }

    public void setup(SequentialExperiment experiment, SimulatorNew simulator) {
        // clear all data from last simulation - ensure collector is clean
        dataCollector.clear();
    }

    public void run(SequentialExperiment experiment, SimulatorNew simulator) throws IOException {

        String topologyName = FilenameUtils.removeExtension(simulator.getTopologyFile().getName());
        Destination[] permutation = experiment.getCurrentSequence();

        for (Destination destination : permutation) {

            String description = String.format("permutation %d/%d iteration %d/%d",
                    experiment.getCurrentPermutation() + 1, experiment.getPermutationCount(),
                    experiment.getCurrentRepetition() + 1, experiment.getRepetitionCount());

            simulator.simulate(destination, description);
            report(experiment.getCurrentPermutation(), experiment.getCurrentRepetition(),
                    destination.getId(), topologyName);
        }

    }

    public void report(SequentialExperiment experiment, SimulatorNew simulator) throws IOException {
        // does nothing! The reporting is done for each destination!
    }

    private void report(int permutationId, int repetitionNumber, int destinationId,
                        String topologyName) throws IOException {

        String defaultReportFilename = String.format("%s_%d_%d.csv",
                topologyName, destinationId, permutationId);

        reporter.report(defaultReportFilename, repetitionNumber, (BasicDataset) dataCollector.getDataset());
    }

    public void cleanup(SequentialExperiment experiment, SimulatorNew simulator) {

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
        // do this for all destinations in the sequence
        for (Destination destination : experiment.getCurrentSequence()) {
            for (Link link : destination.getInLinks()) {
                link.setLastArrivalTime(0);
            }
        }

    }

}
