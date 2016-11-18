package v2.main.cli;


import v2.core.Topology;
import v2.main.Parameters;
import v2.main.ProgressHandler;

import java.io.File;

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
        printTimedStartMessage("Simulating instance " + simulationNumber + "...");
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
