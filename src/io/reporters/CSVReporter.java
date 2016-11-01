package io.reporters;

import core.Path;
import core.Protocol;
import core.topology.ConnectedNode;
import core.topology.Link;
import core.topology.Network;
import core.topology.Topology;
import main.ExecutionStateTracker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import simulators.Detection;
import simulators.Simulator;
import simulators.basic.BasicDataset;
import simulators.gradualdeployment.GradualDeploymentDataset;
import simulators.timeddeployment.TimedDeploymentDataset;

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
     *  Set of headers
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static final String[] BASIC_COUNTS_HEADERS = {
            "Seed", "Time", "Total Message Count", "Detecting Nodes Count",
            "Cut-Off Links Count", "False Positive Count"
    };
    private static final String[] TIMED_DEPLOYMENT_COUNTS_HEADERS = { "Messages After Deployment Count" };
    private static final String[] GRADUAL_DEPLOYMENT_COUNTS_HEADERS = { "Deployed Nodes Count" };
    private static final String[] DETECTIONS_HEADERS = { "Simulation", "Detections", "Detecting Nodes",
            "Cut-Off Links", "Cycles", "Initial Attribute", "False Positive" };
    private static final String[] DEPLOYMENTS_HEADERS = { "Simulation", "Deployed Nodes" };

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final File baseOutputFile;  // path with the base file name for the output
    private final ExecutionStateTracker stateTracker;

    private boolean countsHeadersWereAlreadyPrinted = false;
    private boolean detectionsHeadersWereAlreadyPrinted = false;
    private boolean deploymentsHeadersWereAlreadyPrinted = false;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a reporter associating the output file. Takes a simulation tracker used by the reporter when
     * reporting information relative to some simulation instance.
     *
     * @param baseOutputFile file to output report to.
     */
    public CSVReporter(File baseOutputFile, ExecutionStateTracker stateTracker) throws IOException {
        this.baseOutputFile = baseOutputFile;
        this.stateTracker = stateTracker;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public interface - Write methods from the Reporter Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Writes the simulation time, total message count, number of detection nodes, and number of cut-off links to
     * the counts csv file and fills the detections file with the detections relative to this simulation.
     *
     * @param dataSet basic data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    @Override
    public void writeData(BasicDataset dataSet) throws IOException {

        try (CSVPrinter printer = getCountsFilePrinter()) {
            if (!countsHeadersWereAlreadyPrinted) {
                printer.printRecord((Object[]) BASIC_COUNTS_HEADERS);
                countsHeadersWereAlreadyPrinted = true;
            }

            printCounts(printer, dataSet);
            printer.println();
        }

        writeDetections(dataSet);
    }

    @Override
    public void writeData(BasicDataset basicDataset, TimedDeploymentDataset timedDeploymentDataset)
            throws IOException {

        try (CSVPrinter printer = getCountsFilePrinter()) {
            if (!countsHeadersWereAlreadyPrinted) {
                printHeaders(printer, BASIC_COUNTS_HEADERS, TIMED_DEPLOYMENT_COUNTS_HEADERS);
                countsHeadersWereAlreadyPrinted = true;
            }

            printCounts(printer, basicDataset);
            printer.print(timedDeploymentDataset.getMessageCountAfterDeployment());
            printer.println();
        }

        writeDetections(basicDataset);
    }

    @Override
    public void writeData(BasicDataset basicDataset, GradualDeploymentDataset gradualDeploymentDataset)
            throws IOException {

        try (CSVPrinter printer = getCountsFilePrinter()) {
            if (!countsHeadersWereAlreadyPrinted) {
                printHeaders(printer, BASIC_COUNTS_HEADERS, GRADUAL_DEPLOYMENT_COUNTS_HEADERS);
                countsHeadersWereAlreadyPrinted = true;
            }

            printCounts(printer, basicDataset);
            printer.print(gradualDeploymentDataset.getDeployingNodesCount());
            printer.println();
        }

        writeDetections(basicDataset);

        // write the deploying nodes
        try (CSVPrinter printer = getDeploymentsFilePrinter()) {
            if (!deploymentsHeadersWereAlreadyPrinted) {
                printHeaders(printer, DEPLOYMENTS_HEADERS);
                deploymentsHeadersWereAlreadyPrinted = true;
            }

            printer.print(currentSimulationNumber());
            for (ConnectedNode node : gradualDeploymentDataset.getDeployingNodes()) {
                printer.print(node);
            }
            printer.println();
        }
    }

    /**
     * Writes a summary of the simulation before it starts. Writes basic information about the topology and the
     * simulation parameters.
     *
     * @param topology      original topology.
     * @param destinationId ID of the destination.
     * @param minDelay      minimum delay for an exported message.
     * @param maxDelay      maximum delay for an exported message.
     * @param protocol      protocol being analysed.
     * @param simulator     simulator used for the simulation.
     */
    @Override
    public void writeBeforeSummary(Topology topology, int destinationId, int minDelay, int maxDelay, Protocol protocol,
                                   Simulator simulator) throws IOException {
        Network network = topology.getNetwork();

        try (CSVPrinter csvPrinter = getBeforeSummaryFilePrinter()) {
            csvPrinter.printRecord("Policy", topology.getPolicy());
            csvPrinter.printRecord("ConnectedNode Count", network.getNodeCount());
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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Print methods that can be concatenated. This is true because they do not print a
     *  new line after printing a new column of data.
     *
     *  There is also write methods in the cases where concatenation is not necessary. Write
     *  methods print a new line after each record.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private void printCounts(CSVPrinter printer, BasicDataset dataSet) throws IOException {
        printer.print(dataSet.getSimulationSeed());
        printer.print(dataSet.getSimulationTime());
        printer.print(dataSet.getTotalMessageCount());
        printer.print(dataSet.getDetectingNodesCount());
        printer.print(dataSet.getCutOffLinksCount());
        printer.print(dataSet.getFalsePositiveCount());
    }

    private void writeDetections(BasicDataset dataSet) throws IOException {

        try (CSVPrinter printer = getDetectionsFilePrinter()) {

            if (!detectionsHeadersWereAlreadyPrinted) {
                printHeaders(printer, DETECTIONS_HEADERS);
                detectionsHeadersWereAlreadyPrinted = true;
            }

            int detectionNumber = 1;
            for (Detection detection : dataSet.getDetections()) {

                printer.printRecord(
                        currentSimulationNumber(),
                        detectionNumber++,
                        pretty(detection.getDetectingNode()),
                        pretty(detection.getCutOffLink()),
                        pretty(detection.getCycle()),
                        detection.getInitialAttribute(),
                        detection.isFalsePositive() ? "Yes" : "No"
                );
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

    private static String pretty(ConnectedNode node) {
        return String.valueOf(node.getId());
    }

    private static String pretty(Link link) {
        return link.getSource().getId() + " → " + link.getDestination().getId();
    }

    private static String pretty(Path path) {
        List<Integer> pathNodesIds = path.stream()
                .map(ConnectedNode::getId)
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

    /**
     * Prints multiple groups of headers.
     *
     * @param printer       printer used to print.
     * @param headerGroups  groups of headers to print.
     * @throws IOException
     */
    private void printHeaders(CSVPrinter printer, String[]... headerGroups) throws IOException {

        for (String[] headers : headerGroups) {
            for (String header : headers) {
                printer.print(header);
            }
        }

        printer.println();

    }

}
