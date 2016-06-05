package io.reporters;

import network.Link;
import network.Network;
import network.Node;
import org.apache.commons.lang.StringUtils;
import policies.Path;
import simulators.data.BasicDataSet;
import simulators.data.Detection;
import simulators.data.SPPolicyDataSet;

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

    private static final char COMMA = ';';
    private BufferedWriter writer;

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     * @param network    network being simulated.
     */
    public CSVReporter(File outputFile, Network network) throws IOException {
        super(outputFile, network);
        this.writer = new BufferedWriter(new FileWriter(outputFile));
    }

    public void dumpMain(BasicDataSet basicDataSet, SPPolicyDataSet spPolicyDataSet) throws IOException {
        writer.write("Total Message Count" + COMMA + basicDataSet.getTotalMessageCount());       writer.newLine();
        writer.write("Detecting Nodes Count" + COMMA + basicDataSet.getDetectingNodesCount());   writer.newLine();
        writer.write("Cut-Off Links Count" + COMMA + basicDataSet.getCutOffLinksCount());        writer.newLine();
        if (spPolicyDataSet != null) {
            writer.write("False Positive Count" + COMMA + spPolicyDataSet.getFalsePositiveCount()); writer.newLine();
        }
        writer.newLine();

        writer.write("Detections" + COMMA +
                "Detecting Nodes" + COMMA +
                "Cut-Off Links" + COMMA +
                "Cycles");
        if (spPolicyDataSet != null) {
            writer.write(COMMA + "False Positive");
        }
        writer.newLine();

        int detectionNumber = 1;
        for (Detection detection : basicDataSet.getDetections()) {
            writer.write(String.valueOf(detectionNumber++) + COMMA +
                    pretty(detection.getDetectingNode()) + COMMA +
                    pretty(detection.getCutOffLink()) + COMMA +
                    pretty(detection.getCycle()));
            if (spPolicyDataSet != null) {
                writer.write(COMMA + (detection.isFalsePositive() ? "Yes" : "No"));
            }
            writer.newLine();
        }

        writer.newLine();
        writer.newLine();
    }

    /**
     * Dumps that data from the data set to the current output file.
     *
     * @param dataSet data set to dump to the output file.
     */
    @Override
    public void dump(BasicDataSet dataSet) throws IOException {
        dumpMain(dataSet, null);
    }

    @Override
    public void dump(BasicDataSet basicDataSet, SPPolicyDataSet spPolicyDataSet) throws IOException {
        dumpMain(basicDataSet, spPolicyDataSet);
    }

    @Override
    public void close() throws IOException {
        if (this.writer != null) {
            this.writer.close();
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
