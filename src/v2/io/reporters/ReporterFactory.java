package v2.io.reporters;

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
     * @param reportDestination destination where to place the reports.
     * @return new instance Reporter.
     * @throws IOException if the reporter fails to access the report destination.
     */
    Reporter getReporter(File reportDestination) throws IOException;

}
