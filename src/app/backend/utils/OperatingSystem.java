package app.backend.utils;

/**
 * The OperationSystem that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum OperatingSystem {
	
	/**
	 * The WINDOWS OperativeSystem.
	 */
    WINDOWS,
    
    /**
	 * The LINUX OperativeSystem.
	 */
    LINUX,
    
    /**
	 * The MACOSX OperativeSystem.
	 */
    MACOSX;
    
	/**
	 * 
	 * @return The current OperativeSystem.
	 */
    public static OperatingSystem get() {
        String osName = System.getProperty("os.name").toLowerCase();
    
        if (osName.contains("windows")) {
            return WINDOWS;
        } else if (osName.contains("linux")) {
            return LINUX;
        } else {
            return MACOSX;
        }
    }
}