package app.backend.scraper.engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import app.backend.scraper.results.dynasty.Dynasty;

/**
 * This ScraperRunner extends Thread in order to run the scraping in a dedicated thread.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class ScraperRunner extends Thread {
    
    /**
     * The scraper object.
     */
    private DynastiesScraper scraper;
    
    /**
     * The ArrayList containing the scraper results.
     */
    private ArrayList<Dynasty> scrapedDynasties;

    /**
     * Initializes the scraper with the parameter given.
     * @param scraper The new scraper value.
     */
    public ScraperRunner(DynastiesScraper scraper) {
        this.scraper = scraper;
    }

    
    /** 
     * @return True if the scraper is running, false if not.
     */
    public boolean isScraperActive() {
        return this.scraper.isActive();
    }

    @Override
    public void run() {
        try {
            this.scraper.start();
            this.scrapedDynasties = this.scraper.scrapeDynasties();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getMessage());
        }
        
        this.scraper.kill();
    }

    
    /** 
     * @return The ArrayList with the scraper results.
     * @throws FileNotFoundException if drivers are not fond.
     */
    public ArrayList<Dynasty> getScraperDynasties() throws FileNotFoundException {
        return this.scrapedDynasties;
    }
}