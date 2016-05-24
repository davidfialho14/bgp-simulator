package io.reporters.html;

import java.io.BufferedWriter;
import java.io.IOException;

public class HTMLTableWriter {

    private BufferedWriter writer;
    private String caption;

    /**
     * Takes an HTML writer used to write the table.
     */
    HTMLTableWriter(HTMLWriter htmlWriter, String caption) {
        this.writer = htmlWriter.writer;
        this.caption = caption;
    }

    /**
     * Begins the table with the table opening tag '<table>' and writes the caption of the table after it.
     * To close the table tag the end() method must be used.
     *
     * @return current table writer.
     */
    public HTMLTableWriter begin() throws IOException {
        writer.write("<table class=\"stat-table\">"); writer.newLine();
        writer.write(String.format("<caption>%s</caption>", caption)); writer.newLine();

        return this;
    }

    /**
     * Ends the last table tag with the respective ending table tag. Must be called in the end of each table!
     */
    public void end() throws IOException {
        writer.write("</table>"); writer.newLine();
    }

    /**
     * Writes all the headers in the current file position.
     *
     * @param headers headers to write, each string is an header.
     * @return current table writer.
     */
    public HTMLTableWriter writeHeaders(String... headers) throws IOException {
        writer.write("<thead>"); writer.newLine();

        for (String header : headers) {
            writer.write(String.format("<th>%s</th>", header)); writer.newLine();
        }

        writer.write("</thead>"); writer.newLine();
        return this;
    }

    /**
     * Writes a row to the current table. The elements are written in the same order they are given.
     *
     * @param elements elements of ech columnd of the row.
     * @return current table writer.
     */
    public HTMLTableWriter writeRow(String... elements) throws IOException {
        writer.write("<tr>"); writer.newLine();

        for (String element : elements) {
            writer.write(String.format("<td>%s</td>", element)); writer.newLine();
        }

        writer.write("</tr>"); writer.newLine();
        return this;
    }

}
