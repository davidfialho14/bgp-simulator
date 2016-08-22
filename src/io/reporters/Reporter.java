package io.reporters;

import core.network.Network;
import core.Protocol;
import simulators.Simulator;
import simulators.data.BasicDataSet;
import simulators.data.FullDeploymentDataSet;
import simulators.data.GradualDeploymentDataSet;
import simulators.data.SPPolicyDataSet;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Base class that all reports must extend.
 * A reporter generates reports depending on the given stats collector.
 */
public abstract class Reporter implements Closeable, AutoCloseable {

    protected File outputFile;
    protected Network network;    // holds the core.network to dump core.network information

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     * @param network    core.network being simulated.
     */
    public Reporter(File outputFile, Network network) {
        this.outputFile = outputFile;
        this.network = network;
    }

    /**
     * Dumps all the basic information from the simulation.
     */
    public abstract void dumpBasicInfo(Network network, int destinationId, int minDelay, int maxDelay,
                                       Protocol protocol, Simulator simulator) throws IOException;

    /**
     * Dumps that data from the data set to the current output file.
     *
     * @param dataSet data set to dump to the output file.
     */
    public abstract void dump(BasicDataSet dataSet) throws IOException;

    public abstract void dump(BasicDataSet basicDataSet, SPPolicyDataSet spPolicyDataSet) throws IOException;

    public abstract void dump(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet) throws IOException;

    public abstract void dump(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet,
                              SPPolicyDataSet spPolicyDataSet) throws IOException;

    public abstract void dump(BasicDataSet basicDataSet, GradualDeploymentDataSet gradualDeploymentDataSet) throws IOException;

}
