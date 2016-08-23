package io.reporters;

import java.io.File;
import java.io.IOException;


/**
 * Reporter factory creates reporters based on the factory implementation. When a reporter is created the output
 * stream might be open right away. This prevents a method from receiving a reporter as parameter and use a try()
 * block to ensure the reporter is closed properly. Reporter factories allow to pass the reporter implementation as a
 * parameter and to create an instance when necessary inside a try() block.
 */
public interface ReporterFactory {

    /**
     * Creates a new reporter instance. The type of reporter instance returned depends on the factory
     * implementation.
     *
     * @param reportFile file to associate with the reporter.
     * @param simulationStateTracker
     * @return new instance Reporter.
     * @throws IOException if the reporter fails to open the report file.
     */
    Reporter getReporter(File reportFile, SimulationStateTracker simulationStateTracker) throws IOException;
}
