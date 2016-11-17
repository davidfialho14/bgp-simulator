package io.reporters;

import main.ExecutionStateTracker;
import org.apache.commons.io.FilenameUtils;

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
     * @param stateTracker tracker of the simulation state.
     * @return new instance CSV Reporter.
     * @throws IOException if the reporter fails to open the report file.
     */
    @Override
    public Reporter getReporter(File reportFile, ExecutionStateTracker stateTracker) throws IOException {
        // force extension to be CSV
        String filename = FilenameUtils.getBaseName(reportFile.getName()) + ".csv";
        return new CSVReporter(new File(reportFile.getParent(), filename), stateTracker);
    }

}
