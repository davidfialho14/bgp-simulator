package io.reporters;

import java.io.File;
import java.io.IOException;


/**
 * Reporter factory to encapsulate the creation of CSV reporters.
 */
public class CSVReporterFactory implements ReporterFactory {

    /**
     * Creates a new CSV reporter instance.
     *
     * @param reportFile file to associate with the reporter.
     * @return new instance CSV Reporter.
     * @throws IOException if the reporter fails to open the report file.
     */
    @Override
    public Reporter getReporter(File reportFile) throws IOException {
        return new CSVReporter(reportFile);
    }
}
