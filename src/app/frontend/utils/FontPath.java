package app.frontend.utils;

public enum FontPath {
    QUATTROCENTO_FONT_REGULAR("/assets/fonts/Quattrocento-Regular.ttf"),
    QUATTROCENTO_FONT_BOLD("/assets/fonts/Quattrocento-Bold.ttf");

    private String path;

    private FontPath(String fontPath) {
        this.path = fontPath;
    }

    public String get() {
        return this.path;
    }
}