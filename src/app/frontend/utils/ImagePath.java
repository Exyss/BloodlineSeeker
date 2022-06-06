package app.frontend.utils;

public enum ImagePath {
    APP_LOGO("Icon.png"),
    DEFAULT_IMAGE("DefaultImage.png"),

    DOWNLOAD_ICON("DownloadIcon.png"),
    PRESSED_DOWNLOAD_ICON("ActiveDownloadIcon.png"),

    ARROW_ICON("ArrowCardIcon.png"),
    PRESSED_ARROW_ICON("ActiveArrowCardIcon.png"),  
    
    SEARCH_ICON("SearchIcon.png"),
    PRESSED_SEARCH_ICON("ActiveSearchIcon.png"),

    WIKIPEDIA_ICON("WikipediaIcon.png"),
    PRESSED_WIKIPEDIA_ICON("ActiveWikipediaIcon.png"),
    
    SMALL_WIKIPEDIA_ICON("WikipediaCardIcon.png"),
    PRESSED_SMALL_WIKIPEDIA_ICON("ActiveWikipediaCardIcon.png"),

    WASD_ICON("WasdIcon.png"),
    MOUSE_ICON("MouseWheelIcon.png");

    private static final String IMAGE_DIR = "/assets/images/";

    private String path;

    private ImagePath(String imagePath) {
        this.path = IMAGE_DIR + imagePath;
    }

    public String get() {
        return this.path;
    }
}