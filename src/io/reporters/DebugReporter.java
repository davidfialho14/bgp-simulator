package io.reporters;

import network.Network;
import simulators.data.BasicDataSet;

import java.io.File;
import java.io.IOException;

public class DebugReporter extends Reporter {

    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     * @param network    network being simulated.
     */
    public DebugReporter(File outputFile, Network network) {
        super(outputFile, network);
    }

    /**
     * Dumps that data from the data set to the current output file.
     *
     * @param dataSet data set to dump to the output file.
     */
    @Override
    public void dump(BasicDataSet dataSet) {
        System.out.println("Total Message Count: " + dataSet.getTotalMessageCount());
        System.out.println("Detecting Nodes Count: " + dataSet.getDetectingNodesCount());
        System.out.println("Cut-Off Links Count: " + dataSet.getCutOffLinksCount());
        dataSet.getDetections().forEach(System.out::println);
    }

    @Override
    public void close() throws IOException {
        // does nothing in this case
    }
}
