package io.reporters;

import simulators.statscollectors.*;

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
    public void generate(BasicStatsCollection statsCollector) {
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
    public void generate(FullDeploymentStatsCollection statsCollector) throws IOException {
        generate((BasicStatsCollection) statsCollector);
        System.out.println("message counts after deployment: " + statsCollector.getMessageCountsAfterDeployment());
    }

    /**
     * Generates report for a basic stats collector for the SP policy.
     */
    @Override
    public void generate(SPPolicyBasicStatsCollection statsCollector) throws IOException {
        generate((BasicStatsCollection) statsCollector);
        System.out.println("false positive counts: " + statsCollector.getFalsePositiveCounts());
    }

    /**
     * Generates report for a full deployment collector for the SP policy.
     */
    @Override
    public void generate(SPPolicyFullDeploymentStatsCollection statsCollector) throws IOException {
        generate((FullDeploymentStatsCollection) statsCollector);
        System.out.println("false positive counts: " + statsCollector.getFalsePositiveCounts());
    }
}
