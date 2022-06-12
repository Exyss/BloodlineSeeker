package app.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import app.backend.scraper.engine.DynastiesScraper;
import app.backend.scraper.engine.ScraperRunner;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.dynasty.DynastyVisualizer;
import app.backend.scraper.results.member.Member;

public final class BackendManager {
    public static final String DATA_PATH = "data/";
    public static final String PNG_PATH = DATA_PATH + "pngs/";
    public static final String JSON_PATH = DATA_PATH + "jsons/";

    private static boolean debugMode = false;
    private static boolean seleniumMode = false;
    private static boolean headlessMode = true;

    private static ArrayList<Dynasty> dynasties = new ArrayList<Dynasty>();
    private static Dynasty loadedDynasty;
    private static Member loadedMember;

    private static ScraperRunner runner;
    private static DynastiesScraper scraper;

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
     * @return boolean
     * @throws FileNotFoundException
     * @throws JSONException
     * @throws NullPointerException
     */
    public static boolean loadFromFiles() throws FileNotFoundException, JSONException, NullPointerException {
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
     * @param dynastyJSONpath
     * @throws FileNotFoundException
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
            dynasties.clear();

            throw new JSONException("The dynasty file '" + dynastyJSONpath + "' is empty");
        }
    }

    public static void saveSession() {
        try {
            dynasties = runner.getScraperDynasties();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getMessage());
        }

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
     * @throws IOException
     */
    public static void loadedDynastyToPNG() throws IOException {
        String loadedDynastyPNGPath = PNG_PATH + loadedDynasty.getName() + ".png";
        DynastyVisualizer.toImageFile(loadedDynasty, loadedDynastyPNGPath);
    }

    
    /** 
     * @return BufferedImage
     * @throws FileNotFoundException
     */
    public static BufferedImage loadedDynastyToBufferedImage() throws FileNotFoundException {
        return DynastyVisualizer.toBufferedImage(loadedDynasty);
    }
    
    
    /** 
     * @throws IOException
     */
    public static void loadedMemberToPNG() throws IOException {
        String loadedMemberPNGPath = PNG_PATH + loadedMember.getName() + ".png";
        DynastyVisualizer.toImageFile(loadedMember, loadedMemberPNGPath);
    }

    
    /** 
     * @return BufferedImage
     * @throws FileNotFoundException
     */
    public static BufferedImage loadedMemberToBufferedImage() throws FileNotFoundException {
        return DynastyVisualizer.toBufferedImage(loadedMember);
    }
    
    
    /** 
     * @param dirPath
     * @return ArrayList<String>
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
     * @param debugMsg
     */
    public static void printDebug(String debugMsg) {
        if (debugMode) {
            System.out.println(debugMsg);
        }
    }

    
    /** 
     * @param debugMode
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
     * @param seleniumMode
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
     * @param headlessMode
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
     * @param dynasty
     */
    public static void setLoadedDynasty(Dynasty dynasty) {
        loadedDynasty = dynasty;
    }

    
    /** 
     * @param member
     */
    public static void setLoadedMember(Member member) {
        loadedMember = member;
    }

    
    /** 
     * @return boolean
     */
    public static boolean getSeleniumMode() {
        return seleniumMode;
    }

    
    /** 
     * @return String
     */
    public static String getScraperStatus() {
        if (scraper != null) {
            return scraper.getStatus();
        }

        return "";
    }

    
    /** 
     * @return ArrayList<Dynasty>
     */
    public static ArrayList<Dynasty> getDynasties() {
        return dynasties;
    }
    
    
    /** 
     * @return Dynasty
     */
    public static Dynasty getLoadedDynasty() {
        return loadedDynasty;
    }

    
    /** 
     * @return Member
     */
    public static Member getLoadedMember() {
        return loadedMember;
    }

    
    /** 
     * @return boolean
     */
    public static boolean isScraperRunnerActive() {
        return runner.isScraperActive();
    }

    
    /** 
     * @return boolean
     */
    public static boolean isDebugModeActive() {
        return debugMode;
    }
}