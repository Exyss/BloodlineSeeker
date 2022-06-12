package app.backend.scraper.engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import app.backend.scraper.results.dynasty.Dynasty;

public final class ScraperRunner extends Thread {
    private DynastiesScraper scraper;
    private ArrayList<Dynasty> scrapedDynasties;

    public ScraperRunner(DynastiesScraper scraper) {
        this.scraper = scraper;
    }

    
    /** 
     * @return boolean
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
     * @return ArrayList<Dynasty>
     * @throws FileNotFoundException
     */
    public ArrayList<Dynasty> getScraperDynasties() throws FileNotFoundException {
        return this.scrapedDynasties;
    }
}