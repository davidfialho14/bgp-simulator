package io.reporters.html;

import java.io.*;

public class HTMLWriter implements Closeable, AutoCloseable {

    BufferedWriter writer = null;

    /**
     * Opens the output file and gets ready to write.
     *
     * @param outputFile file to write to.
     */
    public HTMLWriter(File outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
        start();
    }

    // ----- BEGIN Write Methods ------

    /**
     * Writes a header to the HTML file.
     *
     * @param header text to write.
     */
    public void writeHeader(String header) throws IOException {
        writer.write(String.format("<header><h1>%s</h1></header>", header));
        writer.newLine();
    }

    /**
     * Begins a new div with the given class name.
     *
     * @param className class name for the div.
     */
    public void beginClass(String className) throws IOException {
        writer.write(String.format("<div class=\"%s\">", className));
        writer.newLine();
    }

    /**
     * Ends the previous class by ending the previous opened div.
     */
    public void endClass() throws IOException {
        writer.write("</div>");
        writer.newLine();
    }

    /**
     * Writes a titled separator.
     *
     * @param title title for the separator.
     */
    public void writeTitleSeparator(String title) throws IOException {
        writer.write(String.format("<header><span>%s</span></header>", title));
        writer.newLine();
    }

    /**
     * Returns a table writer ready to start writing to the html write output file in the current positision.
     *
     * @param caption caption for the table.
     * @return HTML table writer used to write the table.
     */
    public HTMLTableWriter getTableWriter(String caption) {
        return new HTMLTableWriter(this, caption);
    }

    // ----- END Write Methods ------

    @Override
    public void close() throws IOException {
        if (writer != null) {
            finish();
            writer.close();
            writer = null;
        }
    }

    /**
     * Writes initial part of the HTML file. It must be called before writing any other thing or the
     * output HTML will not be correct. It also imports the CSS file used for the HTML file.
     */
    private void start() throws IOException {
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Report</title>\n" +
                "    <script src=\"https://code.jquery.com/jquery-2.2.3.js\"></script>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.0/Chart.js\"></script>\n" +
                "</head>\n" +
                "<body>");
        writer.newLine();

        writer.write("<style scoped>"); writer.newLine();
        importFile(getClass().getResourceAsStream("css/style.css"));
        writer.write("</style>"); writer.newLine();
    }

    /**
     * Finishes the HTML file by closing the necessary tags. Must be called after finishing writing the HTML
     * or the output will be incorrect.
     */
    private void finish() throws IOException {
        writer.write("</body>\n" +
                "</html>");
        writer.newLine();
    }

    /**
     * Writes the content of a file to the current position of the output file.
     * Helper method that can be used to import both CSS and Javascript files.
     */
    private void importFile(InputStream input) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line); writer.newLine();
            }

        }
    }

    /**
     * Imports a javascript file to the current output file position. It puts the script code between the
     * tags <script> and </script>.
     *
     * @param input stream of the javascript file.
     */
    private void importScript(InputStream input) throws IOException {
        writer.write("<script>"); writer.newLine();
        importFile(input);
        writer.write("</script>"); writer.newLine();
    }
}
