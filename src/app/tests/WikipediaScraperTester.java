package app.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import app.backend.scraper.engine.WikipediaScraper;

/**
 * This WikipediaScraperTester test the scraper.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class WikipediaScraperTester {
	
	/**
	 * Number of tests.
	 */
    private static final int TESTS_NUMBER = 3;

    /**
     * An example link.
     */
    private static final String EXAMPLE_LINK = "https://example.com";

    /**
     * The Romolo wikipedia web page link.
     */
    private static final String ROMOLO_LINK = "https://it.wikipedia.org/wiki/Romolo";

    /**
     * A representation of Romolo summary.
     */
    private static final String ROMOLO_SUMMARY = String.join("\n", 
        "Morte",
        "\tRoma, il 5: https://it.wikipedia.org/wiki/Roma_(citt%C3%A0_antica)",
        "Dinastia",
        "\tRe latino-sabini: https://it.wikipedia.org/wiki/Prima_monarchia_di_Roma",
        "Padre",
        "\tMarte: https://it.wikipedia.org/wiki/Marte_(divinit%C3%A0)",
        "Consorte",
        "\tErsilia: https://it.wikipedia.org/wiki/Ersilia_(mitologia)",
        "In carica",
        "\t753 a.C.",
        "Successore",
        "\tNuma Pompilio: https://it.wikipedia.org/wiki/Numa_Pompilio",
        "Casa reale",
        "\tdi Alba Longa: https://it.wikipedia.org/wiki/Alba_Longa",
        "Madre",
        "\tRea Silvia: https://it.wikipedia.org/wiki/Rea_Silvia",
        "Figli",
        "\tPrima e Avilio",
        "Nascita",
        "\tAlba Longa, 24 marzo del 771 a.C.: https://it.wikipedia.org/wiki/Alba_Longa",
        "Predecessore",
        "\tcarica creata",
        ""
    );

    
    /** 
     * @return The tests number.
     */
    public static int getTotalTests() {
        return TESTS_NUMBER;
    }

    
    /** 
     * Runs the tests.
     * @return The number of tests passed.
     */
    public static int runTests() {
        System.out.print("Testing HTML scraping with HTTP Requests... ");

        int passedTests = 0;

        if (testHTTPrequests()) {
            System.out.println("SUCCESS");

            passedTests++;
        } else {
            System.out.println("FAILED");
        }

        System.out.print("Testing HTML scraping with Selenium... ");

        if (testSelenium()) {
            System.out.println("SUCCESS");

            passedTests++;
        } else {
            System.out.println("FAILED");
        }

        System.out.print("Testing Wikipedia summary scraping... ");

        if (testWikipediaSummary()) {
            System.out.println("SUCCESS");

            passedTests++;
        } else {
            System.out.println("FAILED");
        }

        return passedTests;
    }

    
    /** 
     * Gets the HTML page with the HTTP request.
     * @return true if the HTTP request works, false if not.
     */
    private static boolean testHTTPrequests() {
        try {
            WikipediaScraper.getPageHTMLwithHTTP(EXAMPLE_LINK);
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }

        return true;
    }
    
    
    /** 
     * Gets the HTML page with Selenium.
     * @return true if Selenium works, false if not.
     */
    private static boolean testSelenium() {
        WikipediaScraper scraper = new WikipediaScraper();
        
        // Temporally disable console out and err streams to
        // remove driver runtime output from console
        PrintStream systemOutStream = System.out;
        PrintStream systemErrStream = System.err;

        System.setOut(new PrintStream(new NullOutputStream()));
        System.setErr(new PrintStream(new NullOutputStream()));

        try {
            scraper.start();
            scraper.getPageHTMLwithSelenium(EXAMPLE_LINK);
            scraper.kill();

            // Re-enable system outputs
            System.setOut(systemOutStream);
            System.setErr(systemErrStream);

        } catch (FileNotFoundException e) {

            System.setOut(systemOutStream);
            System.setErr(systemErrStream);
            System.err.println(e);
            return false;
        }
            
        return true;
    }

    
    /** 
     * Tests if the result of the scraping is equals as the expected.
     * @return true if works, false if not.
     */
    private static boolean testWikipediaSummary() {
        HashMap<String, HashMap<String, String>> summary;

        WikipediaScraper scraper = new WikipediaScraper();
        scraper.setSeleniumMode(false);
        
        try {
            scraper.start();
            summary = scraper.getWikipediaSummary(ROMOLO_LINK);
            scraper.kill();
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return false;
        }

        // Check results
        StringBuilder builder = new StringBuilder();

        for(String key : summary.keySet()) {
            HashMap<String, String> data = summary.get(key);
            
            builder.append(key + "\n");
            
            for(String field: data.keySet()) {
                String link = data.get(field);

                builder.append("\t" + field);
                
                if (!link.equals("")) {
                    builder.append(": " + link);
                }

                builder.append("\n");
            }
        }

        String scrapedSummary = builder.toString();

        return ROMOLO_SUMMARY.equals(scrapedSummary);
    }
}

// Fake output to disable system streams
/**
 * A class used to disable the system streams.
 */
class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) {
        return;
    }

    @Override
    public void write(byte[] b) {
        return;
    }

    @Override
    public void write(byte[] b, int off, int len) {
        return;
    }

    /**
     * The constructor of the class.
     */
    public NullOutputStream() {}
}