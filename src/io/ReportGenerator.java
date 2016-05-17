package io;

import java.io.File;
import java.io.IOException;

/**
 * Interface for any report geenrator.
 */
public interface ReportGenerator {

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

}
