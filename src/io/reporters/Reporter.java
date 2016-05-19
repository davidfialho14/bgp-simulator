package io.reporters;

import simulators.statscollectors.BasicStatsCollector;

import java.io.File;

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
    public abstract void generate(BasicStatsCollector statsCollector);

}
