package io.reporters;

import core.Protocol;
import core.topology.Topology;
import simulators.Simulator;
import simulators.basic.BasicDataset;
import simulators.gradualdeployment.GradualDeploymentDataset;
import simulators.timeddeployment.TimedDeploymentDataset;

import java.io.IOException;

/**
 * Reporter is used to report the data in a dataset. Reporting is implemented using the visitor design pattern, where
 * the reporter is the visitor class and the dataset is the visited class. This allows adding new report formats
 * without changing the datasets interfaces.
 */
public interface Reporter {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *
    *  Visitor Methods to Write Data from different datasets
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Writes the data in a basic dataset to the report. Called by the basic dataset report method.
     *
     * @param dataSet basic data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    void writeData(BasicDataset dataSet) throws IOException;

    /**
     * Writes the data in a timed deployment dataset to the report. Called by the timed deployment dataset report
     * method.
     *
     * @param dataSet timed deployment data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    void writeData(TimedDeploymentDataset dataSet) throws IOException;

    /**
     * Writes the data in a gradual deployment dataset to the report. Called by the gradual deployment dataset report
     * method.
     *
     * @param dataSet gradual deployment data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    void writeData(GradualDeploymentDataset dataSet) throws IOException;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *
    *  Methods to write summaries
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Writes a summary of the simulation before it starts. Writes basic information about the topology and the
     * simulation parameters.
     *
     * @param topology      original topology.
     * @param destinationId ID of the destination.
     * @param minDelay      minimum delay for an exported message.
     * @param maxDelay      maximum delay for an exported message.
     * @param protocol      protocol being analysed.
     * @param simulator     simulator used for the simulation.
     */
    void writeBeforeSummary(Topology topology, int destinationId, int minDelay, int maxDelay,
                            Protocol protocol, Simulator simulator) throws IOException;

    /**
     * Writes a summary of the simulation after it finishes. Writes basic information abouts the total results of
     * the simulation.
     */
    void writeAfterSummary() throws IOException;

}
