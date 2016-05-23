package io.reporters;

import simulators.statscollectors.BasicStatsCollector;
import simulators.statscollectors.FullDeploymentStatsCollector;
import simulators.statscollectors.SPPolicyBasicStatsCollector;

import java.io.File;
import java.io.IOException;

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
        System.out.println("detecting nodes count: " + statsCollector.getDetectingNodesCounts());
        System.out.println("detecting nodes: " + statsCollector.getDetectingNodes());
        System.out.println("cut-off links count: " + statsCollector.getCutOffLinksCounts());
        System.out.println("cut-off links: " + statsCollector.getCutoffLinks());
        System.out.println("detections: " + statsCollector.getDetections());
    }

    /**
     * Generates report for a full deployment stats collector.
     */
    @Override
    public void generate(FullDeploymentStatsCollector statsCollector) throws IOException {
        generate((BasicStatsCollector) statsCollector);
        System.out.println("message counts after deployment: " + statsCollector.getMessageCountsAfterDeployment());
    }

    /**
     * Generates report for a basic stats collector for the SP policy.
     *
     * @param statsCollector
     */
    @Override
    public void generate(SPPolicyBasicStatsCollector statsCollector) throws IOException {
        generate((BasicStatsCollector) statsCollector);
        System.out.println("false positive counts: " + statsCollector.getFalsePositiveCounts());
    }
}
