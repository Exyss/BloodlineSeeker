package app.backend.utils;

public enum OperatingSystem {
    WINDOWS,
    LINUX,
    MACOSX;
    
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