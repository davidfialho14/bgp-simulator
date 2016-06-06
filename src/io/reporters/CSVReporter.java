package io.reporters;

import network.Link;
import network.Network;
import network.Node;
import org.apache.commons.lang.StringUtils;
import policies.Path;
import protocols.D1R1Protocol;
import protocols.D2R1Protocol;
import protocols.Protocol;
import simulators.FullDeploymentSimulator;
import simulators.InitialDeploymentSimulator;
import simulators.Simulator;
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

    /**
     * Dumps all the basic information from the simulation.
     */
    public void dumpBasicInfo(Network network, int destinationId, int minDelay, int maxDelay, Protocol protocol,
                              Simulator simulator) throws IOException {

        File basicFile = getClassFile(outputFile, "basic");
        try (BufferedWriter basicWriter = new BufferedWriter(new FileWriter(basicFile))) {
            writeColumns(basicWriter, "Network Name", network.getName());       basicWriter.newLine();
            writeColumns(basicWriter, "Node Count", network.getNodeCount());    basicWriter.newLine();
            writeColumns(basicWriter, "Link Count", network.getLinkCount());    basicWriter.newLine();
            writeColumns(basicWriter, "Destination", destinationId);            basicWriter.newLine();
            writeColumns(basicWriter, "Message Delay", minDelay, maxDelay);     basicWriter.newLine();

            String protocolName = "";
            if (protocol instanceof D1R1Protocol)
                protocolName = "D1";
            else if(protocol instanceof D2R1Protocol) {
                protocolName = "D2";
            }
            writeColumns(basicWriter, "Detection", protocolName); basicWriter.newLine();


            String simulationType = "";
            if (simulator instanceof InitialDeploymentSimulator)
                simulationType = "Initial";
            else if(simulator instanceof FullDeploymentSimulator) {
                simulationType = "Full";
            }
            writeColumns(basicWriter, "Simulation Type", simulationType); basicWriter.newLine();

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

    // --- PRIVATE METHODS ---

    /**
     * Main dump method. All dump methods call this method underneath.
     */
    private void dumpMain(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet,
                          SPPolicyDataSet spPolicyDataSet) throws IOException {

        simulationCounter++;    // every time dump is called it is for a new simulation

        // write counts headers

        if (isCountsFileMissingHeaders) {
            isCountsFileMissingHeaders = false;

            writeColumns(countsWriter, "Total Message Count", "Detecting Nodes Count", "Cut-Off Links Count");
            if (fullDeploymentDataSet != null) {
                writeColumns(countsWriter, "Messages After Deployment Count");
            }
            if (spPolicyDataSet != null) {
                writeColumns(countsWriter, "False Positive Count");
            }
            countsWriter.newLine();
        }

        // write counts data

        writeColumns(countsWriter,
                basicDataSet.getTotalMessageCount(),
                basicDataSet.getDetectingNodesCount(),
                basicDataSet.getCutOffLinksCount()
        );

        if (fullDeploymentDataSet != null) {
            writeColumns(countsWriter, fullDeploymentDataSet.getMessageCount());
        }
        if (spPolicyDataSet != null) {
            writeColumns(countsWriter, spPolicyDataSet.getFalsePositiveCount());
        }

        countsWriter.newLine();

        // write the detections table headers

        if (isDetectionsFileMissingHeaders) {
            isDetectionsFileMissingHeaders = false;

            writeColumns(detectionsWriter, "Simulation", "Detections", "Detecting Nodes", "Cut-Off Links", "Cycles");
            if (spPolicyDataSet != null) {
                writeColumns(detectionsWriter, "False Positive");
            }

            detectionsWriter.newLine();
        }

        // write the detections table data

        int detectionNumber = 1;
        for (Detection detection : basicDataSet.getDetections()) {
            writeColumns(detectionsWriter, simulationCounter,
                    detectionNumber++,
                    pretty(detection.getDetectingNode()),
                    pretty(detection.getCutOffLink()),
                    pretty(detection.getCycle()));
            if (spPolicyDataSet != null) {
                writeColumns(detectionsWriter, (detection.isFalsePositive() ? "Yes" : "No"));
            }
            detectionsWriter.newLine();
        }
    }

    /**
     * Writes a sequence of columns in the CSV format.
     *
     * @param writer      writer used to write columns.
     * @param firstColumn first column to write.
     * @param columns     following columns to write
     * @throws IOException
     */
    private void writeColumns(BufferedWriter writer, Object firstColumn, Object... columns) throws IOException {
        writer.write(firstColumn.toString() + COMMA);

        for (Object column : columns) {
            writer.write(column.toString() + COMMA);
        }
    }

    /**
     * Returns a file with the class name associated to its name.
     *
     * @param originalFile original file.
     * @param fileClass    class name to associate with the file.
     * @return file with the class name associated to its name.
     */
    private static File getClassFile(File originalFile, String fileClass) {
        return new File(originalFile.getParent(),
                originalFile.getName().replaceFirst(".csv", "-" + fileClass + ".csv"));
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
