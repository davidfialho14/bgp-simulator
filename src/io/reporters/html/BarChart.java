package io.reporters.html;

public class BarChart extends AbstractChart {

    /**
     * Creates a new chart with a title.
     *
     * @param title title for the chart.
     * @param color color for the chart.
     */
    public BarChart(String title, Color color) {
        super(title, color);
    }

    /**
     * Returns the string 'bar'.
     *
     * @return string 'bar'.
     */
    @Override
    protected String getType() {
        return "\'bar\'";
    }
}
