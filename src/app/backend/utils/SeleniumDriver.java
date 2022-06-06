package app.backend.utils;

public enum SeleniumDriver {
    UNIX_GECKODRIVER("geckodriver"),
    UNIX_CHROMEDRIVER("chromedriver"),
    UNIX_EDGEDRIVER("msedgedriver"),
    
    WIN_GECKODRIVER("geckodriver.exe"),
    WIN_CHROMEDRIVER("chromedriver.exe"),
    WIN_EDGEDRIVER("msedgedriver.exe");

    private String path;
    
    private SeleniumDriver(String path) {
        this.path = path;
    }
    
    public String get(String driversFolder) {
        return driversFolder + path;
    }
}