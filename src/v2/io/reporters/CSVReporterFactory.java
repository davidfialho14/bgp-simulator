package v2.io.reporters;

import java.io.File;
import java.io.IOException;

/**
 * Reporter factory to encapsulate the creation of CSV reporters.
 */
public class CSVReporterFactory implements ReporterFactory {

    private final String topologyFileName;
    private final int destinationID;

    public CSVReporterFactory(File topologyFile, int destinationID) {
        this.topologyFileName = topologyFile.getName();
        this.destinationID = destinationID;
    }

    /**
     * Creates a new CSV reporter instance.
     *
     * @param reportDestination file to associate with the reporter.
     * @return new instance CSV Reporter.
     * @throws IOException if the reporter fails to open the report file.
     */
    @Override
    public Reporter getReporter(File reportDestination) throws IOException {
        return new CSVReporter(reportDestination, topologyFileName, destinationID);
    }

}
