package io.reporters;

import simulators.statscollectors.BasicStatsCollector;

import java.io.File;

public class DebugReporter extends Reporter {

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     */
    public DebugReporter(File outputFile) {
        super(outputFile);
    }

    /**
     * Generates report for a basic stats collector.
     */
    @Override
    public void generate(BasicStatsCollector statsCollector) {
        System.out.println("node count: " + statsCollector.getNodeCount());
        System.out.println("link count: " + statsCollector.getLinkCount());
        System.out.println("message counts: " + statsCollector.getTotalMessageCounts());
    }

}
