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

public class WikipediaScraper {
    protected static final String WIKI_DOMAIN = "https://it.wikipedia.org";

    private ScraperStatusHolder ScraperStatusHolder;
    private WebDriver driver;

    private boolean useSelenium;
    private boolean useHeadless;
    private boolean isScraperActive;
    private boolean debugMode;
    
    public WikipediaScraper(boolean useSelenium, boolean useHeadless) {
        this.useSelenium = useSelenium;
        this.useHeadless = useHeadless;
        this.isScraperActive = false;
        this.ScraperStatusHolder = new ScraperStatusHolder();
    }

    public WikipediaScraper() {
        this(true, true);
    }

    
    /** 
     * @throws FileNotFoundException
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
     * @throws FileNotFoundException
     */
    private void spawnDriver() throws FileNotFoundException {
        this.setStatus("Finding a supported driver-driven browser to start...");
        
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
     * @param browser
     * @throws FileNotFoundException
     */
    private void testBrowserInstance(Browser browser) throws FileNotFoundException {
        //Setup driver path based on selected browser
        this.selectDriverBasedOnBrowser(browser);

        //Try to spawn an instance based on the selected browser
        switch (browser) {
            case CHROME:
                this.setStatus("Trying to spawn a Google Chrome instance...");

                ChromeOptions chOpt = new ChromeOptions();

                if (this.useHeadless) {
                    chOpt.addArguments("--headless");
                }

                this.driver = new ChromeDriver(chOpt);
                break;
                
            case FIREFOX:
                this.setStatus("Trying to spawn a Mozilla Firefox instance...");

                FirefoxOptions ffOpt = new FirefoxOptions();

                if (this.useHeadless) {
                    ffOpt.addArguments("--headless");
                }
                
                this.driver = new FirefoxDriver(ffOpt);
                break;
                
            default:
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
     * @param browser
     * @throws FileNotFoundException
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
     * @param windowsDriver
     * @param unixDriver
     * @return SeleniumDriver
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
     * @param pageURL
     * @return String
     * @throws FileNotFoundException
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
     * @param pageURL
     * @return String
     * @throws IOException
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
     * @param pageURL
     * @return String
     * @throws FileNotFoundException
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
     * @param pageURL
     * @return HashMap<String, HashMap<String, String>>
     * @throws FileNotFoundException
     */
    public HashMap<String, HashMap<String, String>> getWikipediaSummary(String pageURL) throws FileNotFoundException {
        HashMap<String, HashMap<String, String>> summaryData = new HashMap<String, HashMap<String, String>>();
        
        // Check pageURL
        if (!pageURL.startsWith(WIKI_DOMAIN)) {
            throw new IllegalArgumentException("The given link must be an Italian Wikipedia page (https://it.wikipedia.org)");
        }
        
        Elements summaryRows;
        
        String pageHTML = this.getPageHTML(pageURL);

        Elements sinottico = Jsoup.parse(pageHTML).getElementsByClass("sinottico");
            
        // If page is found but there is no wikiSummary
        if (sinottico.size() == 0) {
            return summaryData;
        } else {
            summaryRows = sinottico
                .get(0) // make sure it's the first one (in some pages there are multiples)
                .getElementsByTag("table") // get the table element
                .get(0)
                .getElementsByTag("tbody") // get the table body
                .get(0)
                .getElementsByTag("tr"); // get the rows
        }

        // skip the first 3 rows
        // because of names and pictures
        summaryRows.remove(0);
        summaryRows.remove(0);
        summaryRows.remove(0);
        
        for (Element row : summaryRows) {
            // extract the th text from the row
            // which will be the key in the HashMap entry

            if (row.attr("class").contains("sinottico_divisione")) {
                continue;
            }

            parseRow(summaryData, row);
        }
        
        return summaryData;
    }

    
    /** 
     * @param summaryData
     * @param row
     */
    private void parseRow(HashMap<String, HashMap<String, String>> summaryData, Element row) {
        Elements ths = row.getElementsByTag("th");
        
        if (ths.size() == 0) {
            return;
        }
        
        String thsText = ths.get(0).text();
        
        if (ths.size() != 0) {
            // extract the td text from the row
            // which will be the value in the HashMap entry
            Elements tds = row.getElementsByTag("td");
            
            // this check does the same thing
            // but for another type of possible
            // separations between sections
            if (tds.size() != 0) {
                HashMap<String, String> tdData = new HashMap<String, String>();

                Element td = tds.get(0);

                for (String chunk : td.html().split("<br>")) {
                    this.parseChunk(tdData, chunk);
                }
                
                summaryData.put(thsText, tdData);
            }
        }
    }
    
    
    /** 
     * @param tdData
     * @param rowChunk
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
     * @param text
     * @return String
     */
    public static String parseParenthesis(String text) {
        return text.replaceAll("\\[.*\\]|\\(.*\\)", "").trim();
    }

    
    /** 
     * @param anchor
     * @return boolean
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
     * @param status
     */
    protected void setStatus(String status) {
        if (this.debugMode) {
            System.out.println(status);
        }

        this.ScraperStatusHolder.updateStatus(status);
    }
    
    
    /** 
     * @param debugMode
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    
    /** 
     * @param useSelenium
     */
    public void setSeleniumMode(boolean useSelenium) {
        this.useSelenium = useSelenium;
    }
    
    
    /** 
     * @param useHeadless
     */
    public void setHeadlessMode(boolean useHeadless) {
        this.useHeadless = useHeadless;
    }

    
    /** 
     * @return String
     */
    public String getStatus() {
        return this.ScraperStatusHolder.getStatus();
    }

    
    /** 
     * @return boolean
     */
    public boolean isActive() {
        return this.isScraperActive;
    }
}