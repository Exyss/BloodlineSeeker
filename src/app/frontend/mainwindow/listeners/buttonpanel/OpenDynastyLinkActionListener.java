package app.frontend.mainwindow.listeners.buttonpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.backend.scraper.results.dynasty.Dynasty;
import app.frontend.mainwindow.MainWindowManager;

public class OpenDynastyLinkActionListener implements ActionListener{
    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Dynasty dynasty = BackendManager.getLoadedDynasty();

        if (dynasty != null) {
            String link = dynasty.getWikipediaLink();

            BackendManager.printDebug("Opened a Member Wikipedia page: " + link);
            
            MainWindowManager.openLink(link);
        }
    }
}