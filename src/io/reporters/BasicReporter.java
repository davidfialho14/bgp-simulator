package io.reporters;

import core.Router;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import simulators.basic.BasicDataset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class BasicReporter implements ReporterNew {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // directory where the report files are saved
    private File reportDirectory;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a new basic reporter. Uses the default report directory defined in the Reporter interface.
     */
    public BasicReporter() {
        // use execution directory by default
        this.reportDirectory = REPORT_DIRECTORY;
    }

    /**
     * Constructs a new basic reporter with a custom report directory. This directory is where the report
     * file wil be output.
     *
     * @param reportDirectory   directory where to place the report files.
     */
    public BasicReporter(File reportDirectory) {
        this.reportDirectory = reportDirectory;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public File getReportDirectory() {
        return reportDirectory;
    }

    public void setReportDirectory(File reportDirectory) {
        this.reportDirectory = reportDirectory;
    }

    public void report(String filename, int simulationNumber, BasicDataset dataset) throws IOException {

        try (CSVPrinter printer = getDataFilePrinter(new File(reportDirectory, filename))) {

            if (simulationNumber == 0) {    // check if it is first simulation
                printer.printRecord((Object[]) getHeaders(dataset));
            }

            printCounts(printer, dataset);
            printer.println();
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
        printer.print(averageLastTimes(dataSet.getLastMessageTimes()));
        printer.print(dataSet.getTotalMessageCount());
        printer.print(dataSet.getDetectingRoutersCount());
        printer.print(dataSet.getCutOffLinksCount());
        printer.print(dataSet.getFalsePositiveCount());
        printer.print(dataSet.didProtocolTerminate() ? "Yes" : "No");
    }

    private double averageLastTimes(Map<Router, Long> lastMessageTimes) {
        SummaryStatistics stats = new SummaryStatistics();

        for (Long timeValue : lastMessageTimes.values()) {
            stats.addValue(timeValue);
        }

        return stats.getMean();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods to get a CSV printer for each type of output file
     *
     *  They all return a file printer for the respective file type
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns a printer for the Data CSV file.
     *
     * @param file file to get printer for.
     * @return a new instance of a CSV printer associated with the given file.
     * @throws IOException if fails to open the file.
     */
    private CSVPrinter getDataFilePrinter(File file) throws IOException {
        return new CSVPrinter(new FileWriter(file, true), CSVFormat.EXCEL.withDelimiter(';'));
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
                "Time",
                "Avg Time",
                "Messages",
                "Detecting Nodes",
                "Detections",
                "False Positives",
                dataset.getDidProtocolTerminateLabel()
        };
    }

}
