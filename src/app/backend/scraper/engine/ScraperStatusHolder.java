package app.backend.scraper.engine;

/**
 * This ScraperStatusHolder updates a variable in order to consider the scraping status.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class ScraperStatusHolder{
	
	/**
	 * The current status of the Scraping.
	 */
    String status;

    /**
     * Initializes the status variable.
     */
    public ScraperStatusHolder() {
        this.status = "";
    }

    
    /** 
     * Updates the status variable.
     * @param status The new current status.
     */
    public synchronized void updateStatus(String status) {
        this.status = status;
    }

    
    /** 
     * @return The current status.
     */
    public synchronized String getStatus() {
        return this.status;
    }
}