package io.reporters.html;

public class LineChart extends AbstractChart {

    /**
     * Creates a new chart with a title.
     *
     * @param title title for the chart.
     * @param color color for the chart.
     */
    public LineChart(String title, Color color) {
        super(title, color);
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
