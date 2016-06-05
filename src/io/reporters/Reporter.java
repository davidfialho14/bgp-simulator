package io.reporters;

import simulators.statscollectors.*;

import java.io.File;
import java.io.IOException;

/**
 * Base class that all reports must extend.
 * A reporter generates reports depending on the given stats collector.
 */
public abstract class Reporter {

    protected File outputFile;

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     */
    public Reporter(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Generates report for a basic stats collector.
     */
    public abstract void generate(BasicStatsCollection statsCollector) throws IOException;

    /**
     * Generates report for a full deployment stats collector.
     */
    public abstract void generate(FullDeploymentStatsCollection statsCollector) throws IOException;

    /**
     * Generates report for a basic stats collector for the SP policy.
     */
    public abstract void generate(SPPolicyBasicStatsCollection statsCollector) throws IOException;

    /**
     * Generates report for a full deployment collector for the SP policy.
     */
    public abstract void generate(SPPolicyFullDeploymentStatsCollection statsCollector) throws IOException;
}
