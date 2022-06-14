package app.backend.utils;

/**
 * The SeleniumDriver that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum SeleniumDriver {
	
	/**
	 * The UNIX_GECKODRIVER SeleniumDriver.
	 */
    UNIX_GECKODRIVER("geckodriver"),
    
    /**
	 * The UNIX_CHROMEDRIVER SeleniumDriver.
	 */
    UNIX_CHROMEDRIVER("chromedriver"),
    
    /**
	 * The UNIX_EDGEDRIVER SeleniumDriver.
	 */
    UNIX_EDGEDRIVER("msedgedriver"),
    
    /**
	 * The WIN_GECKODRIVER SeleniumDriver.
	 */
    WIN_GECKODRIVER("geckodriver.exe"),
    
    /**
	 * The WIN_CHROMEDRIVER SeleniumDriver.
	 */
    WIN_CHROMEDRIVER("chromedriver.exe"),
    
    /**
	 * The WIN_EDGEDRIVER SeleniumDriver.
	 */
    WIN_EDGEDRIVER("msedgedriver.exe");

	/**
	 * The path of the SeleniumDriver.
	 */
    private String path;
    
    /**
     * Initializes the path.
     * @param path the String containing the new path.
     */
    private SeleniumDriver(String path) {
        this.path = path;
    }
    
    /**
     * 
     * @param driversFolder The path of the driver directories.
     * @return The correct path of the SeleniumDriver.
     */
    public String get(String driversFolder) {
        return driversFolder + path;
    }
}