package app.frontend.launcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.launcher.LauncherManager;

/**
 * This SwitchToConsolePageActionListener implements ActionListener in order to know when a download button is selected. When this happens, there is a switch to a console page.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class SwitchToConsolePageActionListener implements ActionListener {
    
    /** 
     * @param arg0
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        LauncherManager.switchToConsolePage();
    }
}