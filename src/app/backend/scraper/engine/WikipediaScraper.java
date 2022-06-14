package app.backend.scraper.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import app.backend.controllers.BinariesController;
import app.backend.utils.Browser;
import app.backend.utils.OperatingSystem;
import app.backend.utils.SeleniumDriver;

/**
 * This WikipediaScraper manage the scrape on wikipedia.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class WikipediaScraper {
    /**
	 * The wikipedia domain.
	 */
    protected static final String WIKI_DOMAIN = "https://it.wikipedia.org";

    /**
	 * The scraper status holder.
	 */
    private ScraperStatusHolder ScraperStatusHolder;

    /**
	 * The web driver.
	 */
    private WebDriver driver;

    /**
	 * The useSelenium boolean.
	 */
    private boolean useSelenium;

    /**
	 * The useHeadless boolean.
	 */
    private boolean useHeadless;

    /**
	 * The isScraperActive boolean.
	 */
    private boolean isScraperActive;

    /**
	 * The debugMode boolean.
	 */
    private boolean debugMode;
    
    /**
	 * Creates the WikipediaScraper and initializes useSelenium, useHeadless, isScraperActive as false and ScraperStatusHolder.
     * @param useSelenium the new boolean value of useSelenium.
     * @param usHeadless the new boolean value of useHeadless.
	 */
    public WikipediaScraper(boolean useSelenium, boolean useHeadless) {
        this.useSelenium = useSelenium;
        this.useHeadless = useHeadless;
        this.isScraperActive = false;
        this.ScraperStatusHolder = new ScraperStatusHolder();
    }
    
    /**
     * Creates the WikipediaScraper and initializes useSelenium as true, useHeadless as true, isScraperActive as false and ScraperStatusHolder.
     */
    public WikipediaScraper() {
        this(true, true);
    }

    
    /** 
     * Starts the scraper. 
     * @throws FileNotFoundException in case the driver isn't found.
     */
    public void start() throws FileNotFoundException {
        // Avoid usage while scraper has already been spawned
        if (this.isScraperActive) {
            throw new IllegalStateException("The scraper is already active");
        }
        
        this.setStatus("Starting scraper...");

        // Create driver instance
        this.isScraperActive = true;

        if (this.useSelenium) {
            this.spawnDriver();
        }
    }
    
    /** 
     * Kills the scraper. 
     */
    public void kill() {
        // Avoid usage while scraper isn't active
        if (!this.isScraperActive) {
            throw new IllegalStateException("The scraper has not been started");
        }

        this.setStatus("Terminating scraper...");
        
        if (this.useSelenium) {
            this.driver.quit();
        }

        this.isScraperActive = false;
    }

    
    /**
     * Tries to spawn a driver instance with one of the supported browsers.
     * @throws FileNotFoundException in case the driver isn't found.
     */
    private void spawnDriver() throws FileNotFoundException {
        this.setStatus("Finding a supported driver-driven browser to start...");
        
        // Searches through every browser looking for any supported browser
        for (Browser browser : Browser.values()) {
            try {
                this.testBrowserInstance(browser);
                this.setStatus("Found a supported browser");
                
                return;
            } catch (SessionNotCreatedException e) {
                //Do nothing and try next browser
            }
        }
        
        // If no driver was spawned, kill the program
        throw new FileNotFoundException("No supported browsers found installed (Firefox, Chrome, Edge)");
    }

    
    /** 
     * Tests if the given browser type is runnable.
     * @param browser the browser type to be tested.
     * @throws FileNotFoundException in case the file doesn't exists.
     */
    private void testBrowserInstance(Browser browser) throws FileNotFoundException {
        //Setup driver path based on selected browser
        this.selectDriverBasedOnBrowser(browser);

        //Try to spawn an instance based on the selected browser
        switch (browser) {
            case CHROME:
                // Setup Chrome instance
                this.setStatus("Trying to spawn a Google Chrome instance...");

                ChromeOptions chOpt = new ChromeOptions();

                if (this.useHeadless) {
                    chOpt.addArguments("--headless");
                }

                this.driver = new ChromeDriver(chOpt);
                break;
                
            case FIREFOX:

                // Setup Firefox instance
                this.setStatus("Trying to spawn a Mozilla Firefox instance...");

                FirefoxOptions ffOpt = new FirefoxOptions();

                if (this.useHeadless) {
                    ffOpt.addArguments("--headless");
                }
                
                this.driver = new FirefoxDriver(ffOpt);
                break;
                
            default:
                // Setup Edge instance
                this.setStatus("Trying to spawn a Microsoft Edge instance...");
                
                EdgeOptions edOpt = new EdgeOptions();

                if (this.useHeadless) {
                    edOpt.addArguments("--headless");
                }
                
                this.driver = new EdgeDriver(edOpt);
                break;
        }
    }    

    
    /** 
     * Selects the correct driver based on given Browser type.
     * @param browser the browser type.
     * @throws FileNotFoundException in case the fle doesn't exists.
     */ 
    private void selectDriverBasedOnBrowser(Browser browser) throws FileNotFoundException {
        String driverPath;
        String driverType;
        SeleniumDriver driverEnum;
        
        switch (browser) {
            case CHROME:
                driverType = "chrome";
                driverEnum = selectDriverBasedOnOS(SeleniumDriver.WIN_CHROMEDRIVER, SeleniumDriver.UNIX_CHROMEDRIVER);
                break;
            
            case FIREFOX:
                driverType = "gecko";
                driverEnum = selectDriverBasedOnOS(SeleniumDriver.WIN_GECKODRIVER, SeleniumDriver.UNIX_GECKODRIVER);
                break;

            default:
                driverType = "edge";
                driverEnum = selectDriverBasedOnOS(SeleniumDriver.WIN_EDGEDRIVER, SeleniumDriver.UNIX_EDGEDRIVER);
                break;
        }
        
        //extract selected driver binary
        BinariesController.extractDriverBinary(driverEnum);
        
        //load extracted bynary path into system properties
        driverPath = driverEnum.get(BinariesController.DRIVERS_TEMP_PATH + "/");

        System.setProperty("webdriver." + driverType + ".driver", driverPath);
    }

    
    /** 
     * Selects the correct driver based on the current OperativeSystem.
     * @param windowsDriver the selectable Windows driver.
     * @param unixDriver the selectable Unix driver.
     * @return the selected SeleniumDriver type.
     */
    private SeleniumDriver selectDriverBasedOnOS(SeleniumDriver windowsDriver, SeleniumDriver unixDriver) {
        SeleniumDriver driver;

        switch (OperatingSystem.get()) {
            case WINDOWS:
                driver = windowsDriver;
                break;
            
            default:
                driver = unixDriver;
                break;
        }

        return driver;
    }
    
    
    /** 
     * Retrieves the HTML code of the given page.
     * @param pageURL the URL of the page.
     * @return the HTML code. 
     * @throws FileNotFoundException in case fallback on Selenium doesn't work.
     */
    public String getPageHTML(String pageURL) throws FileNotFoundException {
        if (!this.useSelenium) {
            try {
                return getPageHTMLwithHTTP(pageURL);
            } catch (IOException e) {
                //If an error occurres use Selenium as fallback
            }
        }

        return this.getPageHTMLwithSelenium(pageURL);
    }

    
    /** 
     * Retrieves the HTML code of the given page by using HTTP Requests.
     * @param pageURL the URL of the page.
     * @return the HTML code
     * @throws IOException in case the connection socket collapses.
     */
    public static String getPageHTMLwithHTTP(String pageURL) throws IOException {
        StringBuilder result = new StringBuilder();
        
        // Setup HTTP request
        URL url = new URL(pageURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        // Read request body and append it to result
        InputStreamReader httpReader = new InputStreamReader(conn.getInputStream());
        BufferedReader buffReader = new BufferedReader(httpReader);

        String line;

        // Read untill the incoming stream stops
        while ((line = buffReader.readLine()) != null) {
            result.append(line);
        }

        buffReader.close();
        httpReader.close();

        return result.toString();
   }
    
    
    /** 
     * Retrieves the HTML code of the given page by using Selenium.
     * @param pageURL the URL of the page.
     * @return the HTML code
     * @throws FileNotFoundException in case the driver isn't found.
     */
    public String getPageHTMLwithSelenium(String pageURL) throws FileNotFoundException {
        // Avoid usage while scraper isn't active
        if (!this.isScraperActive) {
            throw new IllegalStateException("The scraper has not been started");
        }

        // Spawn driver if not already created (happens on fallback)
        if (this.driver == null) {
            this.spawnDriver();
        }

        this.driver.get(pageURL);
        
        // Get everything inside <html> ... </html>
        String contents = this.driver.findElement(By.tagName("html")).getAttribute("innerHTML");
        
        return "<html>\n" + contents + "\n</html>";
    }

    
    /** 
     * Finds the summary of the wikipedia page and converts it into an HashMap.
     * @param pageURL The wikipedia page URL.
     * @return the converted summary.
     * @throws FileNotFoundException in case the fallback on Selenium doesn't work.
     */
    public HashMap<String, HashMap<String, String>> getWikipediaSummary(String pageURL) throws FileNotFoundException {
        HashMap<String, HashMap<String, String>> summaryData = new HashMap<String, HashMap<String, String>>();
        
        // Check if the pageURL is a valid Wikipedia link
        if (!pageURL.startsWith(WIKI_DOMAIN)) {
            throw new IllegalArgumentException("The given link must be an Italian Wikipedia page (https://it.wikipedia.org)");
        }
        
        Elements summaryRows;
        
        String pageHTML = this.getPageHTML(pageURL);

        // Get the "sinottico" table elenemt
        Elements sinottico = Jsoup.parse(pageHTML).getElementsByClass("sinottico");
            
        // If page is found but there is no wikiSummary
        if (sinottico.size() == 0) {
            return summaryData;
        } else {
            summaryRows = sinottico
                .get(0) // On some pages there are multiples "sinottico" tables
                .getElementsByTag("table").get(0)
                .getElementsByTag("tbody").get(0) // Get the body
                .getElementsByTag("tr"); // Get the rows of the "sinottico" table
        }

        // Skip the first 3 rows because there are always titles and pictures in those rows
        summaryRows.remove(0);
        summaryRows.remove(0);
        summaryRows.remove(0);
        
        for (Element row : summaryRows) {
            // "sinottico_divisione" is a row element used as a separator inside the "sinottico" table
            if (row.attr("class").contains("sinottico_divisione")) {
                continue;
            }

            parseRow(summaryData, row);
        }
        
        return summaryData;
    }

    
    /** 
     * Parses sinottico row adding it's contents to the summary data.
     * @param summaryData the HashMap which contains the sinottico.
     * @param row the row to be parsed.
     */
    private void parseRow(HashMap<String, HashMap<String, String>> summaryData, Element row) {
        Elements ths = row.getElementsByTag("th");
        
        if (ths.size() == 0) {
            return;
        }
        
        // The header element of the row
        String thsText = ths.get(0).text();
        
        // Avoid some type of separation in the rows of the "sinottico"
        if (ths.size() != 0) {
            Elements tds = row.getElementsByTag("td");
            
            // Avoid some other type of separation in the rows of the "sinottico"
            if (tds.size() != 0) {
                // This hashmap represents a table consisting of key-value pairs of Text-Link
                HashMap<String, String> tdData = new HashMap<String, String>();

                // The description element of the row
                Element td = tds.get(0);

                // Split the description HTML code between <br> to take out every piece of information separately
                for (String chunk : td.html().split("<br>")) {
                    this.parseChunk(tdData, chunk);
                }
                
                summaryData.put(thsText, tdData);
            }
        }
    }
    
    
    /** 
     * Parses the given row chunk adding it's contents to the row data.
     * @param tdData the HashMap which contains the row data.
     * @param row the row to be parsed.
     */
    private void parseChunk(HashMap<String, String> tdData, String rowChunk) {
        Document tdChunk = Jsoup.parse(rowChunk);
        String hyperlink = "";

        //Since <td></td> was splitted by using "<br>" as a delimiter
        //every elem is a chunk of td, so there can only be one hyperlink
        //inside every chunk

        String chunkText = tdChunk.text();
        Elements anchors = new Elements();

        for (Element anchor : tdChunk.getElementsByTag("a")) {
            if (isValidAnchor(anchor)) {
                anchors.add(anchor);
            }
        }

        if (anchors.size() == 1) {
            Element anchor = anchors.get(0);
            String chunkLink = anchor.attr("href");
            hyperlink = WIKI_DOMAIN + chunkLink;
        }

        String tdKey = parseParenthesis(chunkText);

        tdData.put(tdKey, hyperlink);
    }
    
    
    /** 
     * Parse the parenthesis of a String.
     * @param text the String to be parsed.
     * @return the new String. 
     */
    protected static String parseParenthesis(String text) {
        return text.replaceAll("\\[.*\\]|\\(.*\\)", "").trim();
    }

    
    /** 
     * Checks if the given anchor element is parsable.
     * @param anchor the anchor element
     * @return true if it's parsable, false if not
     */
    private static boolean isValidAnchor(Element anchor) {
        boolean isValid = true;

        String anchorClass = anchor.attr("class");
        String anchorLink = anchor.attr("href");

        //check if the link is a potential existing wikipedia page
        if (anchorClass.contains("new") || anchorLink.startsWith("#")) {
            isValid = false;
        } else {
            //check if the link is a real wikipedia page
            Matcher matcher = Pattern.compile("/wiki/\\d+").matcher(anchorLink);
            isValid = !matcher.find();
        }

        return isValid;
    }
    
    
    /** 
     * Update the ScraperStatusHolder status variable.
     * @param status The new status.
     */
    protected void setStatus(String status) {
        if (this.debugMode) {
            System.out.println(status);
        }

        this.ScraperStatusHolder.updateStatus(status);
    }
    
    
    /** 
     * Updates the debugMode variable.
     * @param debugMode the debug mode to be set.
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    
    /** 
     * Updates the useSelenium variable.
     * @param useSelenium the selenium mode to be set.
     */
    public void setSeleniumMode(boolean useSelenium) {
        this.useSelenium = useSelenium;
    }
    
    
    /** 
     * Updates the setHeadlessMode variable.
     * @param useHeadless the headless mode to be set.
     */
    public void setHeadlessMode(boolean useHeadless) {
        this.useHeadless = useHeadless;
    }

    
    /**
     * @return the current scraper status message.
     */
    public String getStatus() {
        return this.ScraperStatusHolder.getStatus();
    }

    
    /** 
     * @return the current scraper active status.
     */
    public boolean isActive() {
        return this.isScraperActive;
    }
}