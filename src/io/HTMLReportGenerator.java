package io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates reports in HTML form.
 */
public class HTMLReportGenerator {

    private List<Integer> iterations = new ArrayList<>();
    private List<Integer> messageCounts = new ArrayList<>();

    public void addMessageCount(int count) {
        iterations.add(iterations.size() + 1);
        messageCounts.add(count);
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
                    String labels = "\tvar labels = " + iterations + ";";
                    String counts = "\tvar messageCounts = " + messageCounts + ";";

                    writer.write(labels); writer.newLine();
                    writer.write(counts); writer.newLine();
                    writer.newLine();
                }
            }
        }
    }

}
