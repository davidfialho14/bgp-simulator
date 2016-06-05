package io.reporters;

import io.reporters.html.*;
import network.Link;
import network.Node;
import org.apache.commons.lang.StringUtils;
import policies.Path;
import simulators.statscollectors.*;

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
    public void generate(BasicStatsCollection statsCollector) throws IOException {
        throw new UnsupportedEncodingException();
    }

    /**
     * Generates report for a full deployment stats collector.
     */
    @Override
    public void generate(FullDeploymentStatsCollection statsCollector) throws IOException {
        throw new UnsupportedEncodingException();
    }

    /**
     * Generates report for a basic stats collector for the SP policy.
     */
    @Override
    public void generate(SPPolicyBasicStatsCollection statsCollector) throws IOException {
        try (HTMLWriter htmlWriter = new HTMLWriter(outputFile)) {

            htmlWriter.beginClass("network-info");
                htmlWriter.getTableWriter("Basic Information")
                        .begin()
                        .writeRow("Node count", String.valueOf(statsCollector.getNodeCount()))
                        .writeRow("Link count", String.valueOf(statsCollector.getLinkCount()))
                        .end();
            htmlWriter.endClass();

            // --- counts charts ---

            Chart chart = new LineChart("Total Message Counts", Colors.RED);
            chart.setData(statsCollector.getTotalMessageCounts());
            htmlWriter.writeChart(chart);

            chart = new LineChart("Detecting Nodes Counts", Colors.BLUE);
            chart.setData(statsCollector.getDetectingNodesCounts());
            htmlWriter.writeChart(chart);

            chart = new LineChart("Cut-Off Link Counts", Colors.RED);
            chart.setData(statsCollector.getCutOffLinksCounts());
            htmlWriter.writeChart(chart);

            // --- detections ---

            htmlWriter.beginClass("detection");
            for (int i = 0; i < statsCollector.getSimulationCount(); i++) {
                htmlWriter.writeTitleSeparator("Simulation " + (i + 1));

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

    /**
     * Generates report for a full deployment collector for the SP policy.
     */
    @Override
    public void generate(SPPolicyFullDeploymentStatsCollection statsCollector) throws IOException {
        try (HTMLWriter htmlWriter = new HTMLWriter(outputFile)) {

            htmlWriter.beginClass("network-info");
            htmlWriter.getTableWriter("Basic Information")
                    .begin()
                    .writeRow("Node count", String.valueOf(statsCollector.getNodeCount()))
                    .writeRow("Link count", String.valueOf(statsCollector.getLinkCount()))
                    .end();
            htmlWriter.endClass();

            // --- counts charts ---

            Chart chart = new LineChart("Total Message Counts", Colors.RED);
            chart.setData(statsCollector.getTotalMessageCounts());
            htmlWriter.writeChart(chart);

            chart = new LineChart("Detecting Nodes Counts", Colors.BLUE);
            chart.setData(statsCollector.getDetectingNodesCounts());
            htmlWriter.writeChart(chart);

            chart = new LineChart("Cut-Off Link Counts", Colors.RED);
            chart.setData(statsCollector.getCutOffLinksCounts());
            htmlWriter.writeChart(chart);

            chart = new LineChart("Message Counts After Deployment", Colors.BLUE);
            chart.setData(statsCollector.getMessageCountsAfterDeployment());
            htmlWriter.writeChart(chart);

            // --- detections ---

            htmlWriter.beginClass("detection");
            for (int i = 0; i < statsCollector.getSimulationCount(); i++) {
                htmlWriter.writeTitleSeparator("Simulation " + (i + 1));

                htmlWriter.beginClass("counts");
                htmlWriter.getTableWriter("Counts")
                        .begin()
                        .writeRow("Total message count", String.valueOf(statsCollector.getTotalMessageCount(i)))
                        .writeRow("Message count after deployment",
                                String.valueOf(statsCollector.getMessageCountAfterDeployment(i)))
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
