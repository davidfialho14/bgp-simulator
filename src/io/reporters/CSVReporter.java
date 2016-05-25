package io.reporters;

import network.Link;
import network.Node;
import org.apache.commons.lang.StringUtils;
import policies.Path;
import simulators.statscollectors.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates reports in CSV format.
 */
public class CSVReporter extends Reporter {

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     */
    public CSVReporter(File outputFile) {
        super(outputFile);
    }

    /**
     * Generates report for a basic stats collector.
     */
    @Override
    public void generate(BasicStatsCollector statsCollector) throws IOException {

        try (BufferedWriter report = new BufferedWriter(new FileWriter(outputFile))) {
            report.write("number of nodes:; " + statsCollector.getNodeCount()); report.newLine();
            report.write("number of links:; " + statsCollector.getLinkCount()); report.newLine();

            for (int i = 0; i < statsCollector.getSimulationCount(); i++) {
                report.newLine();
                report.write("Simulation " + i + ":"); report.newLine();
                report.write("; Total message count:; " + statsCollector.getTotalMessageCount(i)); report.newLine();
                report.write("; Detecting nodes count:; " + statsCollector.getDetectingNodesCount(i)); report.newLine();
                report.write("; Cut-off links count:; " + statsCollector.getCutOffLinksCount(i)); report.newLine();

                report.newLine();
                report.write("; DETECTING NODE; CUT-OFF LINK; CYCLE"); report.newLine();

                for (Detection detection : statsCollector.getDetections(i)) {
                    report.write("; " +
                            pretty(detection.getDetectingNode()) + "; " +
                            pretty(detection.getCutoffLink()) + "; " +
                            pretty(detection.getCycle()));
                    report.newLine();
                }
            }
        }
    }

    /**
     * Generates report for a full deployment stats collector.
     */
    @Override
    public void generate(FullDeploymentStatsCollector statsCollector) throws IOException {

        try (BufferedWriter report = new BufferedWriter(new FileWriter(outputFile))) {
            report.write("number of nodes:; " + statsCollector.getNodeCount()); report.newLine();
            report.write("number of links:; " + statsCollector.getLinkCount()); report.newLine();

            for (int i = 0; i < statsCollector.getSimulationCount(); i++) {
                report.newLine();
                report.write("Simulation " + i + ":"); report.newLine();
                report.write("; Total message count:; " + statsCollector.getTotalMessageCount(i)); report.newLine();
                report.write("; Detecting nodes count:; " + statsCollector.getDetectingNodesCount(i)); report.newLine();
                report.write("; Cut-off links count:; " + statsCollector.getCutOffLinksCount(i)); report.newLine();
                report.write("; Message Count After Deployment:; " + statsCollector.getMessageCountAfterDeployment(i));
                report.newLine();

                report.newLine();
                report.write("; DETECTING NODE; CUT-OFF LINK; CYCLE"); report.newLine();

                for (Detection detection : statsCollector.getDetections(i)) {
                    report.write("; " +
                            pretty(detection.getDetectingNode()) + "; " +
                            pretty(detection.getCutoffLink()) + "; " +
                            pretty(detection.getCycle()));
                    report.newLine();
                }
            }
        }
    }

    /**
     * Generates report for a basic stats collector for the SP policy.
     */
    @Override
    public void generate(SPPolicyBasicStatsCollector statsCollector) throws IOException {
        try (BufferedWriter report = new BufferedWriter(new FileWriter(outputFile))) {
            report.write("number of nodes:; " + statsCollector.getNodeCount()); report.newLine();
            report.write("number of links:; " + statsCollector.getLinkCount()); report.newLine();

            for (int i = 0; i < statsCollector.getSimulationCount(); i++) {
                report.newLine();
                report.write("Simulation " + i + ":"); report.newLine();
                report.write("; Total message count:; " + statsCollector.getTotalMessageCount(i)); report.newLine();
                report.write("; Detecting nodes count:; " + statsCollector.getDetectingNodesCount(i)); report.newLine();
                report.write("; Cut-off links count:; " + statsCollector.getCutOffLinksCount(i)); report.newLine();
                report.write("; False positive count:; " + statsCollector.getFalsePositiveCount(i)); report.newLine();
                report.newLine();

                report.newLine();
                report.write("; DETECTING NODE; CUT-OFF LINK; CYCLE; FALSE POSITIVE"); report.newLine();

                for (Detection detection : statsCollector.getDetections(i)) {
                    report.write("; " +
                            pretty(detection.getDetectingNode()) + "; " +
                            pretty(detection.getCutoffLink()) + "; " +
                            pretty(detection.getCycle()) + "; " +
                            (detection.isFalsePositive() ? "Yes" : "No"));
                    report.newLine();
                }
            }
        }
    }

    /**
     * Generates report for a full deployment collector for the SP policy.
     */
    @Override
    public void generate(SPPolicyFullDeploymentStatsCollector statsCollector) throws IOException {
        try (BufferedWriter report = new BufferedWriter(new FileWriter(outputFile))) {
            report.write("number of nodes:; " + statsCollector.getNodeCount()); report.newLine();
            report.write("number of links:; " + statsCollector.getLinkCount()); report.newLine();

            for (int i = 0; i < statsCollector.getSimulationCount(); i++) {
                report.newLine();
                report.write("Simulation " + i + ":"); report.newLine();
                report.write("; Total message count:; " + statsCollector.getTotalMessageCount(i)); report.newLine();
                report.write("; Detecting nodes count:; " + statsCollector.getDetectingNodesCount(i)); report.newLine();
                report.write("; Cut-off links count:; " + statsCollector.getCutOffLinksCount(i)); report.newLine();
                report.write("; Message Count After Deployment:; " + statsCollector.getMessageCountAfterDeployment(i));
                report.newLine();
                report.write("; False positive count:; " + statsCollector.getFalsePositiveCount(i)); report.newLine();
                report.newLine();

                report.newLine();
                report.write("; DETECTING NODE; CUT-OFF LINK; CYCLE; FALSE POSITIVE"); report.newLine();

                for (Detection detection : statsCollector.getDetections(i)) {
                    report.write("; " +
                            pretty(detection.getDetectingNode()) + "; " +
                            pretty(detection.getCutoffLink()) + "; " +
                            pretty(detection.getCycle()) + "; " +
                            (detection.isFalsePositive() ? "Yes" : "No"));
                    report.newLine();
                }
            }
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
        return link.getSource().getId() + " → " + link.getDestination().getId();
    }

    private static String pretty(Path path) {
        List<Integer> pathNodesIds = path.stream()
                .map(Node::getId)
                .collect(Collectors.toList());

        return StringUtils.join(pathNodesIds.iterator(), " → ");
    }
}
