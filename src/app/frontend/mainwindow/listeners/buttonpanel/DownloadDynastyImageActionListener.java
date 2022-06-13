package app.frontend.mainwindow.listeners.buttonpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.frontend.FrontendManager;

/** 
 * This DownloadDynastyImageActionListener implements ActionListener in order to manage pressing the download button. When it occurs, it's invoked the Frontend method saveDinatyGraph.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class DownloadDynastyImageActionListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        BackendManager.printDebug("Download button pressed");
        FrontendManager.saveDynastyGraph();
    }
}