package io.reporters.html;

public class Color {

    private final int red;
    private final int green;
    private final int blue;

    private static final double backgroundTransparency = 0.2;
    private static final double borderTransparency = 1;
    private static final double hoverBackgroundTransparency = 0.4;
    private static final double hoverBorderTransparency = 1;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    String getJavacriptObject() {
        return "{" +
                "background: \"" + rgba(backgroundTransparency) + "\"," +
                "border: \"" + rgba(borderTransparency) + "\"," +
                "hoverBackground: \"" + rgba(hoverBackgroundTransparency) + "\"," +
                "hoverBorder: \"" + rgba(hoverBorderTransparency) + "\"," +
                "}";
    }

    private String rgba(double transparency) {
        return String.format("rgba(%d, %d, %d, %1.1f)", red, green, blue, transparency);
    }

}
