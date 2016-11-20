package main;


import core.Topology;

import java.io.File;

/**
 * A progress handler manages how progress events are handled. The CLI or GUI applications can use progress handlers
 * to display progress feedback to the user. The simulation launcher is responsible for calling the evetn methods at
 * the appropriate times. By default all events are ignored.
 */
public interface ProgressHandler {

    /**
     * Invoked in the start of the execution.
     */
    default void onStartExecution() {}

    /**
     * Invoked before starting to load the topology file.
     *
     * @param topologyFile topology file to be loaded.
     */
    default void onStartLoadingTopology(File topologyFile) {}

    /**
     * Invoked after loading the topology file.
     *
     * @param topology loaded topology.
     */
    default void onFinishedLoadingTopology(Topology topology) {}

    /**
     * Invoked before starting each simulation instance.
     *
     * @param simulationNumber number of the simulation instance.
     * @param parameters parameters of the simulation.
     */
    default void onStartSimulation(int simulationNumber, Parameters parameters) {}

    /**
     * Invoked after finishing each simulation instance.
     *
     * @param simulationNumber number of the simulation instance.
     */
    default void onFinishSimulation(int simulationNumber) {}

    /**
     * Invoked before starting to report a simulation instance.
     *
     * @param simulationNumber  number of the simulation instance to be reported.
     * @param reportDestination report destination.
     */
    default void onStartReporting(int simulationNumber, File reportDestination) {}

    /**
     * Invoked after finishing to report a simulation instance.
     *
     * @param simulationNumber number of the simulation instance reported.
     */
    default void onFinishReporting(int simulationNumber) {}

    /**
     * Invoked in the end of the execution.
     */
    default void onFinishExecution() {}

}
