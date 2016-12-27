package main.cli;


import core.Topology;
import main.Parameters;
import main.ProgressHandler;

import java.io.File;
import java.util.List;

/**
 * Implements the handling of progress by the CLI application.
 */
public class CLIProgressHandler implements ProgressHandler {

    private long lastStartTime = System.currentTimeMillis();

    /**
     * Invoked in the start of the execution.
     */
    @Override
    public void onStartExecution() {
        System.out.println("Started execution");
    }

    /**
     * Invoked in the end of the execution.
     */
    @Override
    public void onFinishExecution() {
        System.out.println("Finished execution successfully");
    }

    /**
     * Invoked before starting to load the topology file.
     *
     * @param topologyFile topology file to be loaded.
     */
    @Override
    public void onStartLoadingTopology(File topologyFile) {
        printTimedStartMessage("Loading topology file: " + topologyFile.getName() + "...");
    }

    /**
     * Invoked after loading the topology file.
     *
     * @param topology loaded topology.
     */
    @Override
    public void onFinishedLoadingTopology(Topology topology) {
        printTimedFinishMessage(
                String.format("Loaded topology with %d nodes, %d links, and with routing policy %s",
                topology.getRouterCount(), topology.getLinkCount(), topology.getPolicy()));
    }

    /**
     * Invoked before starting each simulation instance.
     *
     * @param simulationNumber number of the simulation instance.
     * @param parameters       parameters of the simulation.
     */
    @Override
    public void onStartSimulation(int simulationNumber, Parameters parameters) {
        String message = String.format("Simulating instance %d for destination %d, threshold %d",
                simulationNumber, parameters.getDestinationId(), parameters.getThreshold());

        if (parameters.hasSeed()) {
            message += "seed " + parameters.getSeed();
        }

        printTimedStartMessage(message + "...");
    }

    /**
     * Invoked after finishing each simulation instance.
     *
     * @param simulationNumber number of the simulation instance.
     */
    @Override
    public void onFinishSimulation(int simulationNumber) {
        printTimedFinishMessage("Finished simulation successfully");
    }

    /**
     * Invoked before starting to report a simulation instance.
     *
     * @param simulationNumber number of the simulation instance to be reported.
     * @param reportFile       report file to be used.
     */
    @Override
    public void onStartReporting(int simulationNumber, File reportFile) {
        printTimedStartMessage("Reporting to " + reportFile.getName() + "...");
    }

    /**
     * Invoked after finishing to report a simulation instance.
     *
     * @param simulationNumber number of the simulation instance reported.
     */
    @Override
    public void onFinishReporting(int simulationNumber) {
        printTimedFinishMessage("Finished reporting");
    }

    /**
     * Invoked before starting to load the seeds file.
     *
     * @param seedFile seeds file to be loaded.
     */
    @Override
    public void onStartLoadingSeedsFile(File seedFile) {
        printTimedStartMessage("Loading seeds file: " + seedFile.getName() + "...");
    }

    /**
     * Invoked after loading the topology file.
     *
     * @param seeds loaded seeds.
     */
    @Override
    public void onFinishedLoadingSeedsFile(List<Long> seeds) {
        printTimedFinishMessage(String.format("Loaded %d seeds", seeds.size()));
    }

    private void printTimedStartMessage(String message) {
        System.out.println(message);

        // store current time to measure execution time
        lastStartTime = System.currentTimeMillis();
    }

    private void printTimedFinishMessage(String message) {
        System.out.print(message);

        // store current time to measure execution time
        System.out.println(String.format(": in %.02f seconds", (System.currentTimeMillis() - lastStartTime) / 1000.0));
    }

}
