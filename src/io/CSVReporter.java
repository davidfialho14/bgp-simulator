package io;

import io.stats.Stat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Generates a CSV file as a report of a simulation.
 */
public class CSVReporter extends AbstractReporter {

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

            for (int i = 0; i < countsLength; i++) {
                writer.write(String.valueOf(i + 1));    // start by writing the iteration number

                for (Map.Entry<Stat, List<Integer>> statData : statsCounts.entrySet()) {
                    if (!statData.getValue().isEmpty()) {
                        writer.write(", " + statData.getValue().get(i));
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

        for (Stat stat : statsCounts.keySet()) {
            writer.write(", " + stat.getTitle());
        }

        writer.newLine();
    }

}
