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
    private Color color;

    protected List<Integer> data = null;
    protected List<String> labels = null;

    /**
     * Creates a new chart with a title.
     *
     * @param title title for the chart.
     * @param color color for the chart.
     */
    public AbstractChart(String title, Color color) {
        this.title = title;
        this.color = color;
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

        // include the minimum, average and maximum labels
        String minimumId = canvasId + "Minimum";
        String averageId = canvasId + "Average";
        String maximumId = canvasId + "Maximum";

        htmlWriter.beginClass("basicStats");
            htmlWriter.writer.write("<p id=\"" + minimumId + "\">Minimum: </p>");
            htmlWriter.writer.write("<p id=\"" + averageId + "\">Average: </p>");
            htmlWriter.writer.write("<p id=\"" + maximumId + "\">Maximum: </p>");
        htmlWriter.endClass();

        // the canvas must be placed before the script
        htmlWriter.writer.write(String.format("<canvas id=\"%s\" width=\"300\" height=\"100\"></canvas>", canvasId));
        htmlWriter.writer.newLine();

        Iterable<String> labels = this.labels != null ? this.labels : labelsRange(1, data.size());

        // write the script
        htmlWriter.writer.write("<script>"); htmlWriter.writer.newLine();

        // write the data
        String dataVarName = canvasId + "Data";
        htmlWriter.writer.write(String.format("var labels = %s;\n" + "var %s = %s;", labels, dataVarName, data));

        // write code to compute minimum, average, and maximum values
        htmlWriter.writer.write(
                String.format("$(\"#%s\").text(\"Minimum: \" + (Math.min.apply(null, %s)));\n" +
                        "$(\"#%s\").text(\"Average: \" + avg(%s));\n" +
                        "$(\"#%s\").text(\"Maximum: \" + Math.max.apply(null, %s));",
                        minimumId, dataVarName, averageId, dataVarName, maximumId, dataVarName));
        htmlWriter.writer.newLine();

        // load chart
        htmlWriter.writer.write(String.format("var %s = loadChart($(\"#%s\"), \"%s\", labels, %s, %s, %s);",
                canvasId, canvasId, getTitle(), dataVarName, getType(), color.getJavacriptObject()));
        htmlWriter.writer.newLine();

        htmlWriter.writer.write("</script>");
        htmlWriter.writer.newLine();
    }

    /**
     * Returns a string that defines the type of chart.
     *
     * @return string that defines the type of chart.
     */
    protected abstract String getType();

    /**
     * Returns a list with 'length' integer labels from the start value.
     *
     * @param start value to start with.
     * @param length total length of the range.
     * @return list with values between start and start + length.
     */
    private List<String> labelsRange(int start, int length) {
        List<String> labels = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            labels.add(String.valueOf(start + i));
        }

        return labels;
    }

}
