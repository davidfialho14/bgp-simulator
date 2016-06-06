package io.reporters;

import network.Link;
import network.Network;
import network.Node;
import org.apache.commons.lang.StringUtils;
import policies.Path;
import simulators.data.BasicDataSet;
import simulators.data.Detection;
import simulators.data.FullDeploymentDataSet;
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
    private BufferedWriter countsWriter;
    private BufferedWriter detectionsWriter;

    private int simulationCounter = 0;                     // counts the simulations
    private boolean isCountsFileMissingHeaders = true;     // indicates if the counts file is missing the headers
    private boolean isDetectionsFileMissingHeaders = true; // indicates if the detections file is missing the headers

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     * @param network    network being simulated.
     */
    public CSVReporter(File outputFile, Network network) throws IOException {
        super(outputFile, network);

        File countsFile = getClassFile(outputFile, "counts");
        this.countsWriter = new BufferedWriter(new FileWriter(countsFile));

        File detectionsFile = getClassFile(outputFile, "detections");
        this.detectionsWriter = new BufferedWriter(new FileWriter(detectionsFile));
    }

    private static File getClassFile(File originalFile, String fileClass) {
        return new File(originalFile.getParent(),
                originalFile.getName().replaceFirst(".csv", "-" + fileClass + ".csv"));
    }

    public void dumpMain(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet,
                         SPPolicyDataSet spPolicyDataSet) throws IOException {

        simulationCounter++;    // every time dump is called it is for a new simulation

        // write counts headers

        if (isCountsFileMissingHeaders) {
            isCountsFileMissingHeaders = false;

            countsWriter.write("Total Message Count" +
                    COMMA + "Detecting Nodes Count" +
                    COMMA + "Cut-Off Links Count");
            if (fullDeploymentDataSet != null) {
                countsWriter.write(COMMA + "Messages After Deployment Count");
            }
            if (spPolicyDataSet != null) {
                countsWriter.write(COMMA + "False Positive Count");
            }
            countsWriter.newLine();
        }

        // write counts data

        countsWriter.write(String.valueOf(basicDataSet.getTotalMessageCount()) +
                COMMA + String.valueOf(basicDataSet.getDetectingNodesCount()) +
                COMMA + String.valueOf(basicDataSet.getCutOffLinksCount()));
        if (fullDeploymentDataSet != null) {
            countsWriter.write(COMMA + String.valueOf(fullDeploymentDataSet.getMessageCount()));
        }
        if (spPolicyDataSet != null) {
            countsWriter.write(COMMA + String.valueOf(spPolicyDataSet.getFalsePositiveCount()));
        }

        countsWriter.newLine();

        // write the detections table headers

        if (isDetectionsFileMissingHeaders) {
            isDetectionsFileMissingHeaders = false;

            detectionsWriter.write("Simulation" +
                    COMMA + "Detections" +
                    COMMA + "Detecting Nodes" +
                    COMMA + "Cut-Off Links" +
                    COMMA + "Cycles");

            if (spPolicyDataSet != null) {
                detectionsWriter.write(COMMA + "False Positive");
            }

            detectionsWriter.newLine();
        }

        // write the detections table data

        int detectionNumber = 1;
        for (Detection detection : basicDataSet.getDetections()) {
            detectionsWriter.write(String.valueOf(simulationCounter) +
                    COMMA + String.valueOf(detectionNumber++) +
                    COMMA + pretty(detection.getDetectingNode()) +
                    COMMA + pretty(detection.getCutOffLink()) +
                    COMMA + pretty(detection.getCycle()));
            if (spPolicyDataSet != null) {
                detectionsWriter.write(COMMA + (detection.isFalsePositive() ? "Yes" : "No"));
            }
            detectionsWriter.newLine();
        }
    }

    /**
     * Dumps that data from the data set to the current output file.
     *
     * @param dataSet data set to dump to the output file.
     */
    @Override
    public void dump(BasicDataSet dataSet) throws IOException {
        dumpMain(dataSet, null, null);
    }

    @Override
    public void dump(BasicDataSet basicDataSet, SPPolicyDataSet spPolicyDataSet) throws IOException {
        dumpMain(basicDataSet, null, spPolicyDataSet);
    }

    @Override
    public void dump(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet) throws IOException {
        dumpMain(basicDataSet, fullDeploymentDataSet, null);
    }

    @Override
    public void dump(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet,
                     SPPolicyDataSet spPolicyDataSet) throws IOException {
        dumpMain(basicDataSet, fullDeploymentDataSet, spPolicyDataSet);
    }

    @Override
    public void close() throws IOException {
        if (countsWriter != null) {
            countsWriter.close();
        }

        if (detectionsWriter != null) {
            detectionsWriter.close();
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
