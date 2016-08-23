package io.reporters;

import core.Protocol;
import core.topology.Topology;
import simulators.Simulator;
import simulators.data.BasicDataSet;
import simulators.data.FullDeploymentDataSet;
import simulators.data.GradualDeploymentDataSet;
import simulators.data.SPPolicyDataSet;

import java.io.IOException;

/**
 * Base class that all reports must extend. A reporter generates reports depending on the given stats collector.
 * Reporters and simulators use the Visitor pattern, where the reported is the visitor class and the simulator the
 * visited class. This allows adding new report formats without changing the simulator interface.
 */
public interface Reporter {

    /**
     * Writes a summary of the simulation before it starts. Writes basic information about the topology and the
     * simulation parameters.
     */
    void writeBeforeSummary(Topology topology, int destinationId, int minDelay, int maxDelay,
                            Protocol protocol, Simulator simulator) throws IOException;

    /**
     * Writes a summary of the simulation after it finishes. Writes basic information abouts the total results of
     * the simulation.
     */
    void writeAfterSummary() throws IOException;

    void write(BasicDataSet dataSet) throws IOException;

    void write(BasicDataSet basicDataSet, SPPolicyDataSet spPolicyDataSet) throws IOException;

    void write(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet) throws IOException;

    void write(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet, SPPolicyDataSet spPolicyDataSet)
            throws IOException;

    void write(BasicDataSet basicDataSet, GradualDeploymentDataSet gradualDeploymentDataSet) throws IOException;

}
