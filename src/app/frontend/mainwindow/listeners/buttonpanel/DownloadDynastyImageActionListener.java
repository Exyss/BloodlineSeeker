package app.frontend.mainwindow.listeners.buttonpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.FrontendManager;

public class DownloadDynastyImageActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        FrontendManager.saveDynastyGraph();
    }
}