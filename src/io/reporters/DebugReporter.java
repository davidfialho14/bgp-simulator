package io.reporters;

import java.io.File;

public class DebugReporter extends Reporter {

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     */
    public DebugReporter(File outputFile) {
        super(outputFile);
    }

}
