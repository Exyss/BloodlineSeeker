package app.frontend.launcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.FrontendManager;

/**
 * This StartMainWindowActionListener implements ActionListener in order to know when a start button is selected. When this happens, there is a switch to the main window.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class StartMainWindowActionListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        FrontendManager.switchToMainWindow();
    }
}