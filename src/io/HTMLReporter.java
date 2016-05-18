package io;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Generates reports in HTML form.
 */
public class HTMLReporter extends AbstractReporter {

    private static final String resourcesDirectory = "reports/html/";
    private static final String modelHtmlFile = resourcesDirectory + "index.html";

    private static final Pattern dataFieldPattern = Pattern.compile("\\s*var\\s*(?<dataType>\\w+)\\s*=\\s*\\[\\s*\\];");
    private static final Pattern javaScriptLinkPattern = Pattern.compile(
            "<script\\s+type=\"text/javascript\"\\s+src=\"(?<scriptFile>[\\w/\\.]+)\"(\\s+|)>(\\s+|)</script>(\\s+|)");

    /**
     * @param outputFile file to output the report.
     * @throws IOException
     */
    public void generate(File outputFile) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(modelHtmlFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile)) ) {

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher javaScriptLinkMatcher = javaScriptLinkPattern.matcher(line);

                if(javaScriptLinkMatcher.matches()) {
                    linkScript(new File(resourcesDirectory + javaScriptLinkMatcher.group("scriptFile")), writer);
                } else {
                    writer.write(line); writer.newLine();
                }
            }

        }
    }

    /**
     * Includes the script included in the HTML report.
     *
     * @param scriptFile script file to include in the HTML report.
     * @param writer used to write the script file.
     * @throws IOException
     */
    private void linkScript(File scriptFile, BufferedWriter writer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {

            writer.write("<script>"); writer.newLine(); // begin script element

            // copy the script inside the script element
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher dataFieldMatcher = dataFieldPattern.matcher(line);

                if (dataFieldMatcher.matches()) {
                    if (!writeData(writer, dataFieldMatcher.group("dataType"))) {
                        writer.write(line); writer.newLine();
                    }
                } else {
                    writer.write(line); writer.newLine();
                }
            }

            writer.write("</script>"); writer.newLine(); // and script element

        } catch (FileNotFoundException e) {
            // file does not exist
            writer.write("<!-- script '" + scriptFile + "' does not exist -->");
            writer.newLine();
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
