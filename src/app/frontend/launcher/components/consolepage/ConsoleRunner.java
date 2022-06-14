package app.frontend.launcher.components.consolepage;

import app.backend.BackendManager;
import app.frontend.FrontendManager;
import app.frontend.launcher.LauncherManager;
/**
 * This ConsoleRunner extends Thread in order to run the console.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class ConsoleRunner extends Thread{
    /**
     * The starting message.
     */
    private final String DOWNLOAD_START_MSG = "Initializing download...";
    /**
     * The download complete message.
     */
    private final String DOWNLOAD_COMPLETE_MSG = "Download completed.";
    /**
     * The saving complete message.
     */
    private final String SAVING_COMPLETE_MSG = "Dynasties saved successfully";
    
    @Override
    public void run() {
        BackendManager.loadFromScraper();

        updateConsoleAndSleep(DOWNLOAD_START_MSG, 1000);

        while (BackendManager.isScraperRunnerActive()) {
            String newStatus = BackendManager.getScraperStatus();
            
            // Ignores the status if it's a link
            if (!newStatus.contains("/")) {
                LauncherManager.updateConsole(newStatus);
            }
        }

        updateConsoleAndSleep(DOWNLOAD_COMPLETE_MSG, 200);

        BackendManager.saveSession();
        updateConsoleAndSleep(SAVING_COMPLETE_MSG, 500);
        
        FrontendManager.switchToMainWindow();
    }

    /**
     * Update the console and causes the currently executing thread to sleep.
     * @param consoleMsg the new status of the console.
     * @param timeSleep the time of the thread sleeping.
     */
    private void updateConsoleAndSleep(String consoleMsg, long timeSleep) {
        LauncherManager.updateConsole(consoleMsg);

        try {
            sleep(timeSleep);
        } catch (InterruptedException e) {
            //Do nothing
        }
    }
}