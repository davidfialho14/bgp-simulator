package io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Generates reports in HTML form.
 */
public class HTMLReportGenerator {

    private List<Integer> messageCounts = new ArrayList<>();
    private List<Integer> detectionCounts = new ArrayList<>();

    public void addMessageCount(int count) {
        messageCounts.add(count);
    }

    public void addDetectionCount(int count) {
        detectionCounts.add(count);
    }

    /**
     * Generates the report based on the current available data.
     *
     * @param outputFile file to output the report.
     * @throws IOException
     */
    public void generate(File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/index.html"));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile)) ) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line); writer.newLine();

                if (line.equals("<script>")) {  // insert the data variables at the beginning of the script
                    IntStream range = IntStream.range(1, messageCounts.size() + 1);
                    writer.write("\tvar labels = " + Arrays.toString(range.toArray()) + ";"); writer.newLine();
                    writer.write("\tvar messageCounts = " + messageCounts + ";"); writer.newLine();
                    writer.write("\tvar detectionCounts = " + detectionCounts + ";"); writer.newLine();
                    writer.newLine();
                }
            }
        }
    }

}
