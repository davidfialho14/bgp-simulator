package io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            Pattern dataFieldPattern = Pattern.compile("\\s+var (?<dataType>\\w+) = \\[\\];");

            while ((line = reader.readLine()) != null) {
                Matcher dataFieldMatcher = dataFieldPattern.matcher(line);

                if (dataFieldMatcher.matches()) {
                    writeData(writer, dataFieldMatcher.group("dataType"));
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    /**
     * Writes data to the report file according to the given data type. If the data type is invalid
     *
     * @param writer writer where to write data.
     * @param dataType data type.
     * @return true if data was written and false otherwise.
     * @throws IOException
     */
    private boolean writeData(BufferedWriter writer, String dataType) throws IOException {
        boolean wroteData = true;

        switch (dataType) {
            case "labels":
                IntStream range = IntStream.range(1, messageCounts.size() + 1);
                writer.write("\tvar labels = " + Arrays.toString(range.toArray()) + ";");
                writer.newLine();
                break;
            case "messageCounts":
                writer.write("\tvar messageCounts = " + messageCounts + ";");
                writer.newLine();
                break;
            case "detectionCounts":
                writer.write("\tvar detectionCounts = " + detectionCounts + ";");
                writer.newLine();
                break;
            default:
                wroteData = false;
        }

        return wroteData;
    }

}
