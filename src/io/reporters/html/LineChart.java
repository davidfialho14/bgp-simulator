package io.reporters.html;

public class LineChart extends AbstractChart {


    /**
     * Creates a new chart with a title.
     *
     * @param title  title for the chart.
     */
    public LineChart(String title) {
        super(title);
    }

    /**
     * Returns the string 'line'.
     *
     * @return string 'line'.
     */
    @Override
    protected String getType() {
        return "\'line\'";
    }
}
