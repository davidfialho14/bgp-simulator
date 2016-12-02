package io.reporters;


import core.Link;
import core.Path;
import core.Router;
import core.schedulers.Scheduler;
import main.Parameters;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;
import simulators.DetectionData;
import simulators.basic.BasicDataset;
import simulators.basic.BasicSetup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    private final File reportDirectory;     // directory where the report files are saved
    private final String topologyFilename;  // name of the topology file
    private final int destinationID;        // ID of the destination router

    private boolean countsHeadersWereAlreadyPrinted = false;
    private boolean detectionsHeadersWereAlreadyPrinted = false;
    private boolean deploymentsHeadersWereAlreadyPrinted = false;
    private boolean timesHeadersWereAlreadyPrinted = false;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a reporter associating the report directory.
     *
     * @param reportDirectory   directory where to place the report files.
     * @param topologyFilename  name of the topology file.
     * @param destinationID     ID of the destination router used on the simulation.
     */
    public CSVReporter(File reportDirectory, String topologyFilename, int destinationID) throws IOException {
        this.reportDirectory = reportDirectory;
        this.topologyFilename = topologyFilename;
        this.destinationID = destinationID;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public interface - Report setup methods from the Reporter Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Writes a report of the simulation setup and the parameters.
     *
     * @param basicSetup    setup to report.
     * @param parameters    parameters of the simulation.
     */
    @Override
    public void reportSetup(BasicSetup basicSetup, Parameters parameters) throws IOException {

        try (CSVPrinter printer = getBeforeSummaryFilePrinter()) {
            printer.printRecord("Setup", "Basic");
            printer.printRecord("Policy", basicSetup.getTopology().getPolicy());
            printer.printRecord("Router Count", basicSetup.getTopology().getRouterCount());
            printer.printRecord("Link Count", basicSetup.getTopology().getLinkCount());
            printer.printRecord("Destination", basicSetup.getDestination().getId());
            Scheduler scheduler = basicSetup.getEngine().getScheduler();
            printer.printRecord("Message Delay", scheduler.getMinDelay(), scheduler.getMaxDelay());
        }

    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public interface - Report methods from the Reporter Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Writes the simulation time, total message count, number of detection nodes, and number of cut-off 
     * links to the counts csv file and fills the detections file with the detections relative to this 
     * simulation.
     *
     * @param dataset basic data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    @Override
    public void report(int simulationNumber, BasicDataset dataset) throws IOException {

        try (CSVPrinter printer = getCountsFilePrinter()) {
            if (!countsHeadersWereAlreadyPrinted) {
                printer.printRecord((Object[]) getHeaders(dataset));
                countsHeadersWereAlreadyPrinted = true;
            }

            printCounts(printer, dataset);
            printer.println();
        }

        writeDetections(simulationNumber, dataset);
        writeTimes(simulationNumber, dataset);
    }
    
    /**
     * Writes an end file with the indication of a finished simulation.
     */
    @Override
    public void writeAfterSummary() throws IOException {

        try (CSVPrinter csvPrinter = getAfterSummaryFilePrinter()) {
            csvPrinter.printRecord("Finished");
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
        printer.print(dataSet.getDetectingRoutersCount());
        printer.print(dataSet.getCutOffLinksCount());
        printer.print(dataSet.getFalsePositiveCount());
        printer.print(dataSet.didProtocolTerminate() ? "Yes" : "No");
    }

    private void writeDetections(int simulationNumber, BasicDataset dataSet) throws IOException {

        try (CSVPrinter printer = getDetectionsFilePrinter()) {

            if (!detectionsHeadersWereAlreadyPrinted) {

                final String[] detectionsHeaders = {
                        "Simulation", "Detections", "Detecting Routers",
                        "Cut-Off Links", "Cycles", "Initial Attribute", "False Positive"
                };

                printer.printRecord((Object[]) detectionsHeaders);
                detectionsHeadersWereAlreadyPrinted = true;
            }

            int detectionNumber = 1;
            for (DetectionData detection : dataSet.getDetections()) {

                printer.printRecord(
                        simulationNumber,
                        detectionNumber++,
                        pretty(detection.getDetectingRouter()),
                        pretty(detection.getCutOffLink()),
                        pretty(detection.getCycle()),
                        detection.getInitialAttribute(),
                        detection.isFalsePositive() ? "Yes" : "No"
                );
            }
        }
    }

    private void writeTimes(int simulationNumber, BasicDataset dataSet) throws IOException {

        try (CSVPrinter printer = getTimesFilePrinter()) {

            if (!timesHeadersWereAlreadyPrinted) {

                final String[] timesHeaders = { "Simulation", "Router", "Last Message Times" };

                printer.printRecord((Object[]) timesHeaders);
                timesHeadersWereAlreadyPrinted = true;
            }

            for (Map.Entry<Router, Long> nodeLastMessageTime : dataSet.getLastMessageTimes().entrySet()) {
                printer.printRecord(
                        simulationNumber,
                        pretty(nodeLastMessageTime.getKey()),
                        nodeLastMessageTime.getValue()
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
    
    private CSVPrinter getTimesFilePrinter() throws IOException {
        return getFilePrinter(getFile("times"), true);
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
     * Returns a file within the report directory with the name given by 
     * (topology_filename)-dest(destinationID)-(tag).csv. It forces the extension to be CSV.
     *
     * @param tag   tag to add to the file name.
     * @return file with the tag name associated to its name.
     */
    private File getFile(String tag) {
        return new File(reportDirectory, 
                String.format("%s-dest%d-%s.csv", topologyFilename, destinationID, tag));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Set of helper methods to display any element like Routers, Links, Paths, etc in a prettier
     *  format then its standard toString() result.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static String pretty(Router router) {
        return String.valueOf(router.getId());
    }

    private static String pretty(Link link) {
        return pretty(link.getSource()) + " → " + pretty(link.getTarget());
    }

    private static String pretty(Path path) {
        List<String> pathRoutersIds = path.stream()
                .map(CSVReporter::pretty)
                .collect(Collectors.toList());

        return StringUtils.join(pathRoutersIds.iterator(), " → ");
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    /**
     * Returns the headers for the basic dataset.
     *
     * @param dataset data set to get headers for
     * @return string array containing the headers in the correct order
     */
    private String[] getHeaders(BasicDataset dataset) {

        return new String[]{
                dataset.getSimulationSeedLabel(),
                dataset.getSimulationTimeLabel(),
                dataset.getTotalMessageCountLabel(),
                dataset.getDetectingRoutersCountLabel(),
                dataset.getCutOffLinksCountLabel(),
                dataset.getFalsePositiveCountLabel(),
                dataset.getDidProtocolTerminateLabel()
        };
    }

}
