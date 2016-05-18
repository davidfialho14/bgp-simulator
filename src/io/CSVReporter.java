package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates a CSV file as a report of a simulation.
 */
public class CSVReporter extends AbstractReporter {

    private static final String MESSAGE_HEADER = "Message Count";
    private static final String DETECTION_HEADER = "Detection Count";
    private static final String CUTOFF_LINKS_HEADER = "Cut-off Links Count";
    private static final String DETECTING_NODES_HEADER = "Detecting Nodes Count";

    private final Map<String, List<Integer>> stats = new LinkedHashMap<>();

    /**
     * Initializes the stats map with each stat title and respective data.
     */
    public CSVReporter() {
        stats.put("Message Count", messageCounts);
        stats.put("Detection Count", detectionCounts);
        stats.put("Cut-off Links Count", cutOffLinksCounts);
        stats.put("Detecting Nodes Count", detectingNodesCounts);
    }

    /**
     * Generates the report based on the current available data.
     *
     * @param outputFile file to output the report.
     * @throws IOException if an error occurs when outputting the report file.
     */
    @Override
    public void generate(File outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile)) ) {
            writeHeaders(writer);

            for (int i = 0; i < messageCounts.size(); i++) {
                writer.write(String.valueOf(i + 1));    // start by writing the iteration number

                for (Map.Entry<String, List<Integer>> stat : stats.entrySet()) {
                    if (!stat.getValue().isEmpty()) {
                        writer.write(", " + stat.getValue().get(i));
                    }
                }

                writer.newLine();
            }
        }
    }

    /**
     * Writes the headers to the CSV file. If a stat is empty (without counts) its header is not included
     * in the report.
     */
    private void writeHeaders(BufferedWriter writer) throws IOException {

        writer.write("Iteration");  // first column contains the iterations

        for (Map.Entry<String, List<Integer>> stat : stats.entrySet()) {
            if (!stat.getValue().isEmpty()) {
                writer.write(", " + stat.getKey());
            }
        }

        writer.newLine();
    }

}
