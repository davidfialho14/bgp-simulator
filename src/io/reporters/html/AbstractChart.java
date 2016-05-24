package io.reporters.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation for any chart type.
 */
public abstract class AbstractChart implements Chart {

    private static int idCount = 0;

    private int id = idCount++;
    private String title;

    protected List<Integer> data = null;
    protected List<String> labels = null;

    /**
     * Creates a new chart with a title.
     *
     * @param title title for the chart.
     */
    public AbstractChart(String title) {
        this.title = title;
    }

    /**
     * Returns the ID of the chart. All charts should have unique IDs since they will be used to identify them
     * in the HTML file.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Returns the title for the chart.
     *
     * @return title for the chart.
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the data for the chart.
     *
     * @param data data to be set.
     */
    public void setData(List<Integer> data) {
        this.data = data;
    }

    /**
     * Sets the labels for the chart.
     *
     * @param labels labels to be set
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * Writes the chart to an HTML file.
     *
     * @param htmlWriter where to write the chart.
     */
    @Override
    public void write(HTMLWriter htmlWriter) throws IOException {
        String canvasId = "chart" + getId();

        // the canvas must be placed before the script
        htmlWriter.writer.write(String.format("<canvas id=\"%s\" width=\"300\" height=\"100\"></canvas>", canvasId));
        htmlWriter.writer.newLine();

        // write the script
        htmlWriter.writer.write(String.format("<script>\n" +
                "    var labels = %s;\n" +
                "    var data = %s;\n" +
                "    %s = loadChart($(\"#%s\"), \"%s\", labels, data, %s, RED);\n" +
                "\n" +
                "</script>", labels, data, canvasId, canvasId, getTitle(), getType()));
        htmlWriter.writer.newLine();
    }

    /**
     * Returns a string that defines the type of chart.
     *
     * @return string that defines the type of chart.
     */
    protected abstract String getType();

}
