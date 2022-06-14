package app.frontend.utils;

/**
 * The font paths that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum FontPath {
	
	/**
	 * The QUATTROCENTO_FONT_REGULAR font path.
	 */
    QUATTROCENTO_FONT_REGULAR("/assets/fonts/Quattrocento-Regular.ttf"),
    /**
	 * The QUATTROCENTO_FONT_BOLD font path.
	 */
    QUATTROCENTO_FONT_BOLD("/assets/fonts/Quattrocento-Bold.ttf");

	/**
	 * The font path.
	 */
    private String path;

    /**
     * Initializes the font path with the value of the parameter.
     * @param fontPath the new path value.
     */
    private FontPath(String fontPath) {
        this.path = fontPath;
    }

    /**
     * 
     * @return the path.
     */
    public String get() {
        return this.path;
    }
}