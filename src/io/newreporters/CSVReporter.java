package io.newreporters;

import core.Path;
import core.topology.Link;
import core.topology.Node;
import main.ExecutionStateTracker;
import newsimulators.basic.BasicDataSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import simulators.data.Detection;

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
    private final ExecutionStateTracker stateTracker;

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

    @Override
    public void writeData(BasicDataSet dataSet) throws IOException {

        try (CSVPrinter printer = getCountsFilePrinter()) {

            printer.print(dataSet.getSimulationTime());
            printer.print(dataSet.getTotalMessageCount());
            printer.print(dataSet.getDetectingNodesCount());
            printer.print(dataSet.getCutOffLinksCount());
            printer.println();
        }

        try (CSVPrinter printer = getDetectionsFilePrinter()) {

            int detectionNumber = 1;
            for (Detection detection : dataSet.getDetections()) {
                printer.print(currentSimulationNumber());
                printer.print(detectionNumber++);
                printer.print(pretty(detection.getDetectingNode()));
                printer.print(pretty(detection.getCutOffLink()));
                printer.print(pretty(detection.getCycle()));
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

    private CSVPrinter getCountsFilePrinter() throws IOException {
        return getFilePrinter(getFile("counts"), true);
    }

    private CSVPrinter getDetectionsFilePrinter() throws IOException {
        return getFilePrinter(getFile("detections"), true);
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
