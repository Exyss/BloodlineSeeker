package app.frontend.mainwindow.listeners.buttonpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.FrontendManager;

public class DownloadDynastyImageActionListener implements ActionListener {
    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FrontendManager.saveDynastyGraph();
    }
}