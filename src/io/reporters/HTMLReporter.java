package io.reporters;

import io.reporters.html.HTMLTableWriter;
import io.reporters.html.HTMLWriter;
import network.Link;
import network.Node;
import org.apache.commons.lang.StringUtils;
import policies.Path;
import simulators.statscollectors.BasicStatsCollector;
import simulators.statscollectors.Detection;
import simulators.statscollectors.FullDeploymentStatsCollector;
import simulators.statscollectors.SPPolicyBasicStatsCollector;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLReporter extends Reporter {
    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     */
    public HTMLReporter(File outputFile) {
        super(outputFile);
    }

    /**
     * Generates report for a basic stats collector.
     */
    @Override
    public void generate(BasicStatsCollector statsCollector) throws IOException {
        throw new UnsupportedEncodingException();
    }

    /**
     * Generates report for a full deployment stats collector.
     */
    @Override
    public void generate(FullDeploymentStatsCollector statsCollector) throws IOException {
        throw new UnsupportedEncodingException();
    }

    /**
     * Generates report for a basic stats collector for the SP policy.
     */
    @Override
    public void generate(SPPolicyBasicStatsCollector statsCollector) throws IOException {
        try (HTMLWriter htmlWriter = new HTMLWriter(outputFile)) {

            htmlWriter.beginClass("network-info");
                htmlWriter.getTableWriter("Basic Information")
                        .begin()
                        .writeRow("Node count", String.valueOf(statsCollector.getNodeCount()))
                        .writeRow("Link count", String.valueOf(statsCollector.getLinkCount()))
                        .end();
            htmlWriter.endClass();

            htmlWriter.beginClass("detection");
            for (int i = 0; i < statsCollector.getSimulationCount(); i++) {
                htmlWriter.writeTitleSeparator("Simulation" + (i + 1));

                htmlWriter.beginClass("counts");
                    htmlWriter.getTableWriter("Counts")
                            .begin()
                            .writeRow("Total message count", String.valueOf(statsCollector.getTotalMessageCount(i)))
                            .writeRow("Detecting nodes count", String.valueOf(statsCollector.getDetectingNodesCount(i)))
                            .writeRow("Cut-off links count", String.valueOf(statsCollector.getCutOffLinksCount(i)))
                            .writeRow("False positive count", String.valueOf(statsCollector.getFalsePositiveCount(i)))
                            .end();
                htmlWriter.endClass();

                htmlWriter.beginClass("summary");
                    HTMLTableWriter summaryTable = htmlWriter.getTableWriter("Summary");
                    summaryTable.begin();
                    summaryTable.writeHeaders("Detection", "Detecting Node", "Cut-Off Link", "Cycle", "False Positive");

                    int detectionNumber = 1;
                    for (Detection detection : statsCollector.getDetections().get(i)) {
                        summaryTable.writeRow(
                                String.valueOf(detectionNumber),
                                pretty(detection.getDetectingNode()),
                                pretty(detection.getCutoffLink()),
                                pretty(detection.getCycle()),
                                detection.isFalsePositive() ? "Yes" : "No");

                        detectionNumber++;
                    }
                    summaryTable.end();

                htmlWriter.endClass();
            }
            htmlWriter.endClass();
        }
    }

    /*
        Set of helper method that allow displaying any element like Nodes, Links, Paths, etc into a more prettier
        format.
     */

    private static String pretty(Node node) {
        return String.valueOf(node.getId());
    }

    private static String pretty(Link link) {
        return link.getSource().getId() + " <span>&#8594;</span> " + link.getDestination().getId();
    }

    private static String pretty(Path path) {
        List<Integer> pathNodesIds = path.stream()
                .map(Node::getId)
                .collect(Collectors.toList());

        return StringUtils.join(pathNodesIds.iterator(), " <span>&#8594;</span> ");
    }
}
