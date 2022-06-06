package app.backend.scraper.engine;

public final class ScraperStatusHolder{
    String status;

    public ScraperStatusHolder() {
        this.status = "";
    }

    public synchronized void updateStatus(String status) {
        this.status = status;
    }

    public synchronized String getStatus() {
        return this.status;
    }
}