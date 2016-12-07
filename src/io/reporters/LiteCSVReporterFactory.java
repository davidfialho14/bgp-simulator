package io.reporters;

import java.io.File;
import java.io.IOException;

/**
 * Reporter factory to encapsulate the creation of lite CSV reporters.
 */
public class LiteCSVReporterFactory implements ReporterFactory {

    private final String topologyFileName;
    private final int destinationID;

    public LiteCSVReporterFactory(File topologyFile, int destinationID) {
        this.topologyFileName = topologyFile.getName();
        this.destinationID = destinationID;
    }

    /**
     * Creates a new lite CSV reporter instance.
     *
     * @param reportDestination file to associate with the reporter.
     * @return new instance lite CSV Reporter.
     * @throws IOException if the reporter fails to open the report file.
     */
    @Override
    public Reporter getReporter(File reportDestination) throws IOException {
        return new LiteCSVReporter(reportDestination, topologyFileName, destinationID);
    }

}
