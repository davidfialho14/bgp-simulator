package io.reporters;

import simulators.statscollectors.BasicStatsCollector;
import simulators.statscollectors.Detection;
import simulators.statscollectors.FullDeploymentStatsCollector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
                    report.write("; " + detection.getDetectingNode() + "; " + detection.getCutoffLink() + "; " +
                            detection.getCycle());
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
                    report.write("; " + detection.getDetectingNode() + "; " + detection.getCutoffLink() + "; " +
                            detection.getCycle());
                    report.newLine();
                }
            }
        }
    }
}
