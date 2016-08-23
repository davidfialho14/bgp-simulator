package io.reporters;

import core.Path;
import core.Protocol;
import core.topology.Link;
import core.topology.Network;
import core.topology.Node;
import core.topology.Topology;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;
import protocols.D1R1Protocol;
import protocols.D2R1Protocol;
import simulators.FullDeploymentSimulator;
import simulators.GradualDeploymentSimulator;
import simulators.InitialDeploymentSimulator;
import simulators.Simulator;
import simulators.data.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates reports in CSV format.
 */
public class CSVReporter implements Reporter {

    private static final char DELIMITER = ';';

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final File baseOutputFile;  // path with the base file name for the output

    private final CSVPrinter countsWriter;
    private final CSVPrinter detectionsWriter;
    private final CSVPrinter deploymentsWriter;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private int simulationCounter = 0;                     // counts the simulations
    private boolean isCountsFileMissingHeaders = true;     // indicates if the counts file is missing the headers
    private boolean isDetectionsFileMissingHeaders = true; // indicates if the detections file is missing the headers
    private boolean isDeploymentsFileMissingHeaders = true; // indicates if the deployments file is missing the headers

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a reporter associating the output file.
     *
     * @param baseOutputFile file to output report to.
     */
    public CSVReporter(File baseOutputFile) throws IOException {
        this.baseOutputFile = baseOutputFile;

        File countsFile = getClassFile(baseOutputFile, "counts");
        this.countsWriter = new BufferedWriter(new FileWriter(countsFile));

        File detectionsFile = getClassFile(baseOutputFile, "detections");
        this.detectionsWriter = new BufferedWriter(new FileWriter(detectionsFile));

        File deploymentsFile = getClassFile(baseOutputFile, "deployments");
        this.deploymentsWriter = new BufferedWriter(new FileWriter(deploymentsFile));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public interface - Write methods from teh Reporter Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Dumps all the basic information from the simulation.
     */
    public void writeSimulationInfo(Topology topology, int destinationId, int minDelay, int maxDelay, Protocol protocol,
                                    Simulator simulator) throws IOException {

        Network network = topology.getNetwork();

        File basicFile = getClassFile(baseOutputFile, "basic");
        try (BufferedWriter basicWriter = new BufferedWriter(new FileWriter(basicFile))) {
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
            } else if (simulator instanceof GradualDeploymentSimulator) {
                simulationType = "Gradual";
            }
            writeColumns(basicWriter, "Simulation Type", simulationType); basicWriter.newLine();

        }
    }

    /**
     * Dumps that data from the data set to the current output file.
     *
     * @param dataSet data set to write to the output file.
     */
    @Override
    public void write(BasicDataSet dataSet) throws IOException {
        mainWrite(dataSet, null, null, null);
    }

    @Override
    public void write(BasicDataSet basicDataSet, SPPolicyDataSet spPolicyDataSet) throws IOException {
        mainWrite(basicDataSet, null, null, spPolicyDataSet);
    }

    @Override
    public void write(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet) throws IOException {
        mainWrite(basicDataSet, fullDeploymentDataSet, null, null);
    }

    @Override
    public void write(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet,
                      SPPolicyDataSet spPolicyDataSet) throws IOException {
        mainWrite(basicDataSet, fullDeploymentDataSet, null, spPolicyDataSet);
    }

    @Override
    public void write(BasicDataSet basicDataSet, GradualDeploymentDataSet gradualDeploymentDataSet) throws IOException {
        mainWrite(basicDataSet, null, gradualDeploymentDataSet, null);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Main write called by all other write methods
     *  Centralized way to write reports
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Main write method. All write methods call this method underneath.
     */
    private void mainWrite(BasicDataSet basicDataSet, FullDeploymentDataSet fullDeploymentDataSet,
                           GradualDeploymentDataSet gradualDeploymentDataSet, SPPolicyDataSet spPolicyDataSet)
            throws IOException {

        simulationCounter++;    // every time write is called it is for a new simulation

        // write counts headers

        if (isCountsFileMissingHeaders) {
            isCountsFileMissingHeaders = false;

            writeColumns(countsWriter, "Time", "Total Message Count", "Detecting Nodes Count", "Cut-Off " +
                    "Links " +
                    "Count");
            if (fullDeploymentDataSet != null) {
                appendColumn(countsWriter, "Messages After Deployment Count");
            }
            if (gradualDeploymentDataSet != null) {
                appendColumn(countsWriter, "Deployed Nodes Count");
            }
            if (spPolicyDataSet != null) {
                appendColumn(countsWriter, "False Positive Count");
            }
            countsWriter.newLine();
        }

        // write counts data

        writeColumns(countsWriter,
                basicDataSet.getSimulationTime(),
                basicDataSet.getTotalMessageCount(),
                basicDataSet.getDetectingNodesCount(),
                basicDataSet.getCutOffLinksCount()
        );

        if (fullDeploymentDataSet != null) {
            appendColumn(countsWriter, fullDeploymentDataSet.getMessageCount());
        }
        if (gradualDeploymentDataSet != null) {
            appendColumn(countsWriter, gradualDeploymentDataSet.getDeployedNodesCount());
        }
        if (spPolicyDataSet != null) {
            appendColumn(countsWriter, spPolicyDataSet.getFalsePositiveCount());
        }

        countsWriter.newLine();

        // write the detections table headers

        if (isDetectionsFileMissingHeaders) {
            isDetectionsFileMissingHeaders = false;

            writeColumns(detectionsWriter, "Simulation", "Detections", "Detecting Nodes", "Cut-Off Links", "Cycles");
            if (spPolicyDataSet != null) {
                appendColumn(detectionsWriter, "False Positive");
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
                appendColumn(detectionsWriter, (detection.isFalsePositive() ? "Yes" : "No"));
            }
            detectionsWriter.newLine();
        }

        if (gradualDeploymentDataSet != null) {

            // write the deployments table headers

            if (isDeploymentsFileMissingHeaders) {
                isDeploymentsFileMissingHeaders = false;

                writeColumns(deploymentsWriter, "Simulation", "Deployed Nodes");

                deploymentsWriter.newLine();
            }

            // write the deployments table data

            String deployedNodes = gradualDeploymentDataSet.getDeployedNodes().stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(", "));

            writeColumns(deploymentsWriter, simulationCounter, deployedNodes);
            deploymentsWriter.newLine();
        }

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

    /**
     * Writes a sequence of columns in the CSV format.
     *
     * @param writer      writer used to write columns.
     * @param firstColumn first column to write.
     * @param columns     following columns to write
     * @throws IOException
     */
    private void writeColumns(BufferedWriter writer, Object firstColumn, Object... columns) throws IOException {
        writer.write(firstColumn.toString());

        for (Object column : columns) {
            appendColumn(writer, column);
        }
    }

    private void appendColumn(BufferedWriter writer, Object column) throws IOException {
        writer.write(COMMA + column.toString());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Set of helper methods to display any element like Nodes, Links, Paths, etc in a prettier
     *  format then its standard toString() result.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
