package io;

import java.io.File;
import java.io.IOException;

/**
 * Interface for any reporter.
 */
public interface Reporter {

    /**
     * Generates the report based on the current available data.
     *
     * @param outputFile file to output the report.
     * @throws IOException if an error occurs when outputting the report file.
     */
    void generate(File outputFile) throws IOException;

}
