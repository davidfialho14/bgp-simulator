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

    /**
     * Adds a new message count.
     *
     * @param count new message count.
     */
    void addMessageCount(int count);

    /**
     * Adds a new detection count.
     *
     * @param count new detection count.
     */
    void addDetectionCount(int count);

    /**
     * Adds a new detecting nodes count.
     *
     * @param count new detecting nodes count.
     */
    void addDetectingNodesCount(int count);

    /**
     * Adds a new cut-off links count.
     *
     * @param count new cut-off links count.
     */
    void addCutOffLinksCount(int count);

}
