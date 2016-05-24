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
     * Returns a string that defines the type of chart.
     *
     * @return string that defines the type of chart.
     */
    @Override
    protected String getType() {
        return "\'line\'";
    }
}
