package io.reporters.html;

import java.io.IOException;
import java.util.List;

/**
 * Interface to be implemented by any chart.
 */
public interface Chart {

    /**
     * Returns the ID of the chart. All charts should have unique IDs since they will be used to identify them
     * in the HTML file.
     */
    int getId();

    /**
     * Returns the title for the chart.
     *
     * @return title for the chart.
     */
    String getTitle();

    /**
     * Sets the data for the chart.
     *
     * @param data data to be set.
     */
    void setData(List<Integer> data);

    /**
     * Sets the labels for the chart.
     *
     * @param labels labels to be set
     */
    void setLabels(List<String> labels);

    /**
     * Writes the chart to an HTML file
     *
     * @param htmlWriter where to write the chart.
     */
    void write(HTMLWriter htmlWriter) throws IOException;

}
