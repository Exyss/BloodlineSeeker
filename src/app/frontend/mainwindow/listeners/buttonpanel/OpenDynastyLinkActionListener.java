package app.frontend.mainwindow.listeners.buttonpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.backend.scraper.results.dynasty.Dynasty;
import app.frontend.mainwindow.MainWindowManager;

/** 
 * This OpenDynastyLinkActionListener implements ActionListener in order to manage pressing the wikipedia button near the members showed in the scroll bar. When it occurs if the dynasty of the member isn't null, using the
 * MainWindowManager method openLink will be opened the member wikipedia page.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class OpenDynastyLinkActionListener implements ActionListener{
    
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