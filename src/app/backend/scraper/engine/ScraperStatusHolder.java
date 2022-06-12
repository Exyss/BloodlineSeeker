package app.backend.scraper.engine;

public final class ScraperStatusHolder{
    String status;

    public ScraperStatusHolder() {
        this.status = "";
    }

    
    /** 
     * @param status
     */
    public synchronized void updateStatus(String status) {
        this.status = status;
    }

    
    /** 
     * @return String
     */
    public synchronized String getStatus() {
        return this.status;
    }
}