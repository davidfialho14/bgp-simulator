package io.reporters;

import core.Path;
import core.Protocol;
import core.topology.Link;
import core.topology.Network;
import core.topology.Node;
import core.topology.Topology;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import simulators.Simulator;
import simulators.data.*;

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
    private final SimulationStateTracker stateTracker;

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
    public CSVReporter(File baseOutputFile, SimulationStateTracker stateTracker) throws IOException {
        this.baseOutputFile = baseOutputFile;
        this.stateTracker = stateTracker;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public interface - Write methods from the Reporter Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Writes a summary of the simulation. Containing basic information about the topology and the simulation
     * parameters.
     */
    public void writeBeforeSummary(Topology topology, int destinationId, int minDelay, int maxDelay, Protocol protocol,
                                   Simulator simulator) throws IOException {

        Network network = topology.getNetwork();

        try (CSVPrinter csvPrinter = getBeforeSummaryFilePrinter()) {
            csvPrinter.printRecord("Policy", topology.getPolicy());
            csvPrinter.printRecord("Node Count", network.getNodeCount());
            csvPrinter.printRecord("Link Count", network.getLinkCount());
            csvPrinter.printRecord("Destination", destinationId);
            csvPrinter.printRecord("Message Delay", minDelay, maxDelay);
            csvPrinter.printRecord("Protocol", protocol);
            csvPrinter.printRecord("Simulation Type", simulator);
        }
    }

    /**
     * Writes a summary of the simulation after it finishes. Writes basic information abouts the total results of
     * the simulation.
     */
    @Override
    public void writeAfterSummary() throws IOException {

        // be careful to avoid division by zero
        float detectionAvg = stateTracker.getDetectionCount() != 0 ?
                (float) stateTracker.getDetectionCount() / (float) stateTracker.getSimulationCount() : 0;

        try (CSVPrinter csvPrinter = getAfterSummaryFilePrinter()) {
            csvPrinter.printRecord("Simulation Count", stateTracker.getSimulationCount());
            csvPrinter.printRecord("Avg. Detection Count", String.format("%.2f", detectionAvg));
        }

    }

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

        //
        // Write counts data
        //

        try (CSVPrinter printer = getCountsFilePrinter()) {

            printer.print(basicDataSet.getSimulationTime());
            printer.print(basicDataSet.getTotalMessageCount());
            printer.print(basicDataSet.getDetectingNodesCount());
            printer.print(basicDataSet.getCutOffLinksCount());

            if (fullDeploymentDataSet != null) {
                printer.print(fullDeploymentDataSet.getMessageCount());
            }

            if (gradualDeploymentDataSet != null) {
                printer.print(gradualDeploymentDataSet.getDeployedNodesCount());
            }

            if (spPolicyDataSet != null) {
                printer.print(spPolicyDataSet.getFalsePositiveCount());
            }

            printer.println();
        }

        //
        // Write the detections table data
        //

        try (CSVPrinter printer = getDetectionsFilePrinter()) {

            int detectionNumber = 1;
            for (Detection detection : basicDataSet.getDetections()) {
                printer.print(currentSimulationNumber());
                printer.print(detectionNumber++);
                printer.print(pretty(detection.getDetectingNode()));
                printer.print(pretty(detection.getCutOffLink()));
                printer.print(pretty(detection.getCycle()));

                if (spPolicyDataSet != null) {
                    printer.print(detection.isFalsePositive() ? "Yes" : "No");
                }

                printer.println();
            }
        }

        if (gradualDeploymentDataSet != null) {

            //
            // Write the deployments table data
            //

            try (CSVPrinter printer = getDeploymentsFilePrinter()) {
                printer.print(currentSimulationNumber());

                for (Node node : gradualDeploymentDataSet.getDeployedNodes()) {
                    printer.print(node);
                }

                printer.println();
            }
        }

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods to get a CSV printer for each type of output file
     *
     *  They all return a file printer for the respective file type
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private CSVPrinter getBeforeSummaryFilePrinter() throws IOException {
        return getFilePrinter(getFile("beforesummary"), false);
    }

    private CSVPrinter getAfterSummaryFilePrinter() throws IOException {
        return getFilePrinter(getFile("aftersummary"), false);
    }

    private CSVPrinter getCountsFilePrinter() throws IOException {
        return getFilePrinter(getFile("counts"), true);
    }

    private CSVPrinter getDetectionsFilePrinter() throws IOException {
        return getFilePrinter(getFile("detections"), true);
    }

    private CSVPrinter getDeploymentsFilePrinter() throws IOException {
        return getFilePrinter(getFile("deployments"), true);
    }

    /**
     * Base method to get a CSV printer for any file. All other "get printer" methods call this base method.
     *
     * @param file      file to associate with the printer.
     * @param append    true to open the file in append mode and false to truncate the file.
     * @return a new instance of a CSV printer associated with the given file.
     * @throws IOException if fails to open the file.
     */
    private static CSVPrinter getFilePrinter(File file, boolean append) throws IOException {
        return new CSVPrinter(new FileWriter(file, append), CSVFormat.EXCEL.withDelimiter(DELIMITER));
    }

    /**
     * Appends the given tag to the end of the base output filename and returns the result file. Keeps original file
     * extension.
     *
     * @param tag   tag to add to the base output file.
     * @return file with the class name associated to its name.
     */
    private File getFile(String tag) {
        String extension = FilenameUtils.getExtension(baseOutputFile.getName());

        // append the tag to the original file name (keep the extension)
        String filename = FilenameUtils.getBaseName(baseOutputFile.getName()) + String.format("-%s.%s", tag, extension);

        return new File(baseOutputFile.getParent(), filename);
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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the current simulation number based on the current simulation stateTracker.
     *
     * @return the current simulation number.
     */
    private int currentSimulationNumber() {
        return stateTracker.getSimulationCount();
    }

}
