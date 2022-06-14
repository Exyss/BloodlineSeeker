package app.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import app.backend.controllers.BinariesController;
import app.backend.scraper.engine.DynastiesScraper;
import app.backend.scraper.engine.ScraperRunner;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.dynasty.DynastyVisualizer;
import app.backend.scraper.results.member.Member;

/**
 * This BackendManager is the principal class of the BackHand. Thanks to this class the frontEnd can get the access to all the non-graphic functioms of the program.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class BackendManager {
	/**
	 * The default data path.
	 */
    public static final String DATA_PATH = "data/";
    
    /**
     * The PNG complete path.
     */
    public static final String PNG_PATH = DATA_PATH + "pngs/";
    
    /**
     * The JSON complete path. 
     */
    public static final String JSON_PATH = DATA_PATH + "jsons/";

    /**
     * If true the program is running the debug mode.
     */
    private static boolean debugMode = false;
    
    /**
     * If it is true the scraping uses Selenium, else the scraping uses HTTP requests.
     */
    private static boolean seleniumMode = false;
    
    /**
     * If it is true the browser id not showed up.
     */
    private static boolean headlessMode = true;

    /**
     * The whole dynasties.
     */
    private static ArrayList<Dynasty> dynasties = new ArrayList<Dynasty>();
    
    /**
     * The loaded dynasty.
     */
    private static Dynasty loadedDynasty;
    
    /**
     * The loaded member.
     */
    private static Member loadedMember;

    /**
     * The scraper thread.
     */
    private static ScraperRunner runner;

    /**
     * The scraper.
     */
    private static DynastiesScraper scraper;

    /**
     * Sets the Gphviz binaries up.
     */
    public static void setupGraphviz() {
        BinariesController.clearTempDirectories();
        BinariesController.extractGraphVizBinaries();
    }
    /**
     * Does the scrape and loads the dynasties.
     */
    public static void loadFromScraper() {
        BackendManager.printDebug("Loading dynasties from scaper (seleniumMode: " + seleniumMode + ")");

        //Flush existing dynasties
        dynasties.clear();

        try {
            // Create and Start WebScraper instance
            scraper = new DynastiesScraper();

            scraper.setDebugMode(debugMode);
            scraper.setSeleniumMode(seleniumMode);
            scraper.setHeadlessMode(headlessMode);

            //Run the scraper in a different thread
            runner = new ScraperRunner(scraper);
            runner.start();

        } catch (Exception e) {
            BackendManager.printDebug("An error occurred while trying to create a WebScraper instance: " + e.getMessage());
        }
    }

    
    /** 
     * Loads the dynasties from the already existing files.
     * @return true if the Files actually exists. 
     * @throws FileNotFoundException in case the file doesn't exists.
     * @throws JSONException in case the JSON file is empty or corrupted.
     */
    public static boolean loadFromFiles() throws FileNotFoundException, JSONException {
        BackendManager.printDebug("Trying to load dynasties from JSONs");

        //Flush existing dynasties
        dynasties.clear();

        ArrayList<String> fileNames = listDir(JSON_PATH);

        //if no JSONs were found
        if (fileNames.size() == 0) {
            BackendManager.printDebug("No saved dynasties were found");

            return false;
        }

        //if at least a JSON was found
        for (String file : fileNames) {
            JSONtoDynasty(JSON_PATH + file);
        }

        return true;
    }

    
    /** 
     * @param dynastyJSONpath The path of the file that has to be converted.
     * @throws FileNotFoundException in case the file doesn't exists.
     */
    private static void JSONtoDynasty(String dynastyJSONpath) throws FileNotFoundException{
        BackendManager.printDebug("Loading dynasty from: " + dynastyJSONpath);

        try {
            BackendManager.dynasties.add(Dynasty.fromJSONFile(dynastyJSONpath));

        } catch (IOException e) {
            // should never happen because of listDir
            throw new FileNotFoundException("The file '" + dynastyJSONpath + "' was not found");
            
        } catch (JSONException j) {
            // remove partially found dynasties
            dynasties.clear();

            throw new JSONException("An error occurred while trying to parse '" + dynastyJSONpath + "'");
        } catch (NullPointerException n) {
            // remove partially found dynasties
            dynasties.clear();

            throw new JSONException("The dynasty file '" + dynastyJSONpath + "' is empty");
        }
    }
    
    /**
     * Saves the current session by saving every loaded dynasty into a file.
     */
    public static void saveSession() {
        try {
            dynasties = runner.getScraperDynasties();

        } catch (FileNotFoundException e) {
            // This will never happend, however it's needed
            throw new IllegalStateException(e.getMessage());
        }

        //Serialize every loaded dynasty into a JSON file
        for (Dynasty dynasty : dynasties) {
            String dynastyPath = JSON_PATH + dynasty.getName() + ".json";

            try {
                dynasty.toJSONFile(dynastyPath);
                
                BackendManager.printDebug(dynasty.getName() + " saved successfully.");
            } catch (IOException e) {
                BackendManager.printDebug("An error occurred while trying to save" + dynasty.getName());
            }
        }

        try {
            //Reload session for graph consistency
            loadFromFiles();
        } catch (FileNotFoundException | JSONException | NullPointerException e) {
            BackendManager.printDebug("And error occurred while trying to reload session");
        }
    }

    
    /** 
     * @throws IOException if the conversion doesn't work.
     */
    public static void loadedDynastyToPNG() throws IOException {
        String loadedDynastyPNGPath = PNG_PATH + loadedDynasty.getName() + ".png";
        DynastyVisualizer.toImageFile(loadedDynasty, loadedDynastyPNGPath);
    }

    
    /** 
     * @return the converted BufferedImage
     * @throws FileNotFoundException if the conversion doesn't work.
     */
    public static BufferedImage loadedDynastyToBufferedImage() throws FileNotFoundException {
        return DynastyVisualizer.toBufferedImage(loadedDynasty);
    }
    
    
    /** 
     * @throws IOException if the conversion doesn't work.
     */
    public static void loadedMemberToPNG() throws IOException {
        String loadedMemberPNGPath = PNG_PATH + loadedMember.getName() + ".png";
        DynastyVisualizer.toImageFile(loadedMember, loadedMemberPNGPath);
    }

    
    /** 
     * @return the converted BufferedImage
     * @throws FileNotFoundException if the conversion doesn't work.
     */
    public static BufferedImage loadedMemberToBufferedImage() throws FileNotFoundException {
        return DynastyVisualizer.toBufferedImage(loadedMember);
    }
    
    
    /** 
     * Lists all files found in the given directory path.
     * @param dirPath the path of the directory that should be listed.
     * @return the list of files.
     */
    private static ArrayList<String> listDir(String dirPath) {
        ArrayList<String> fileNames = new ArrayList<String>();

        File dir = new File(dirPath);

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }

    
    /** 
     * Prints a debug message only if debug mode is active.
     * @param debugMsg the message to be printed.
     */
    public static void printDebug(String debugMsg) {
        if (debugMode) {
            System.out.println(debugMsg);
        }
    }

    
    /** 
     * @param debugMode the debug mode to be set.
     */
    public static void setDebugMode(boolean debugMode) {
        if (debugMode) {
            BackendManager.printDebug("Debug mode enabled");
        } else {
            BackendManager.printDebug("Debug mode disabled");
        }
        
        BackendManager.debugMode = debugMode;
    }

    
    /** 
     * @param seleniumMode the selenium mode to be set.
     */
    public static void setSeleniumMode(boolean seleniumMode) {
        if (seleniumMode) {
            BackendManager.printDebug("Selenium mode enabled");
        } else {
            BackendManager.printDebug("Selenium mode disabled");
        }
        
        BackendManager.seleniumMode = seleniumMode;
    }
    
    
    /** 
     * @param headlessMode the headless mode to be set.
     */
    public static void setHeadlessMode(boolean headlessMode) {
        if (headlessMode) {
            BackendManager.printDebug("Headless mode enabled");
        } else {
            BackendManager.printDebug("Headless mode disabled");
        }
        
        BackendManager.headlessMode = headlessMode;
    }
    
    
    /**
     * @param dynasty the dynasty to be set.
     */
    public static void setLoadedDynasty(Dynasty dynasty) {
        loadedDynasty = dynasty;
    }

    
    /** 
     * @param member the member to be set.
     */
    public static void setLoadedMember(Member member) {
        loadedMember = member;
    }

    
    /** 
     * @return the current selenium mode setting.
     */
    public static boolean getSeleniumMode() {
        return seleniumMode;
    }

    
    /** 
     * @return the current scraper status message.
     */
    public static String getScraperStatus() {
        if (scraper != null) {
            return scraper.getStatus();
        }

        return "";
    }

    
    /** 
     * @return the currently loaded dynasties.
     */
    public static ArrayList<Dynasty> getDynasties() {
        return dynasties;
    }
    
    
    /** 
     * @return the currently loaded dynasty.
     */
    public static Dynasty getLoadedDynasty() {
        return loadedDynasty;
    }

    
    /** 
     * @return the currently loaded member.
     */
    public static Member getLoadedMember() {
        return loadedMember;
    }

    
    /** 
     * @return the scraper activation status.
     */
    public static boolean isScraperRunnerActive() {
        return runner.isScraperActive();
    }

    
    /** 
     * @return the current debug mode setting.
     */
    public static boolean isDebugModeActive() {
        return debugMode;
    }
}