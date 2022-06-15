package app.frontend.utils;

/**
 * The image paths that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum ImagePath {
    /**
     * The APP_LOGO image path.
     */
    APP_LOGO("Icon.png"),
    
    /**
     * The DEFAULT_IMAGE image path.
     */
    DEFAULT_IMAGE("DefaultImage.png"),

    /**
     * The DOWNLOAD_ICON image path.
     */
    DOWNLOAD_ICON("DownloadIcon.png"),
    
    /**
     * The PRESSED_DOWNLOAD_ICON image path.
     */
    PRESSED_DOWNLOAD_ICON("ActiveDownloadIcon.png"),

    /**
     * The ARROW_ICON image path.
     */
    ARROW_ICON("ArrowCardIcon.png"),
    
    /**
     * The PRESSED_ARROW_ICON image path.
     */
    PRESSED_ARROW_ICON("ActiveArrowCardIcon.png"),  
    
    /**
     * The SEARCH_ICON image path.
     */
    SEARCH_ICON("SearchIcon.png"),
    
    /**
     * The PRESSED_SEARCH_ICON image path.
     */
    PRESSED_SEARCH_ICON("ActiveSearchIcon.png"),

    /**
     * The WIKIPEDIA_ICON image path.
     */
    WIKIPEDIA_ICON("WikipediaIcon.png"),
    
    /**
     * The PRESSED_WIKIPEDIA_ICON image path.
     */
    PRESSED_WIKIPEDIA_ICON("ActiveWikipediaIcon.png"),
    
    /**
     * The SMALL_WIKIPEDIA_ICON image path.
     */
    SMALL_WIKIPEDIA_ICON("WikipediaCardIcon.png"),
    
    /**
     * The PRESSED_SMALL_WIKIPEDIA_ICON image path.
     */
    PRESSED_SMALL_WIKIPEDIA_ICON("ActiveWikipediaCardIcon.png"),

    /**
     * The WASD_ICON image path.
     */
    WASD_ICON("WasdIcon.png"),
    
    /**
     * The MOUSE_ICON image path.
     */
    MOUSE_ICON("MouseWheelIcon.png");

    /**
     * The default image directory.
     */
    private static final String IMAGE_DIR = "/assets/images/";

    /**
     * The image path.
     */
    private String path;

    /**
     * Initializes the image path using the IMAGE_DIR and the parameter.
     * @param imagePath the new image path.
     */
    private ImagePath(String imagePath) {
        this.path = IMAGE_DIR + imagePath;
    }

    /**
     * 
     * @return the image path.
     */
    public String get() {
        return this.path;
    }
}