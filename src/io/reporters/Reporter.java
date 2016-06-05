package io.reporters;

import java.io.File;

/**
 * Base class that all reports must extend.
 * A reporter generates reports depending on the given stats collector.
 */
public abstract class Reporter {

    protected File outputFile;

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     */
    public Reporter(File outputFile) {
        this.outputFile = outputFile;
    }

}
