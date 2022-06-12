package app.frontend.launcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.launcher.LauncherManager;

/**
 * This SwitchPageActionListener implements ActionListener in order to know when a switch page button is selected. When this happens, there is a switch to another page.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class SwitchPageActionListener implements ActionListener {
    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        LauncherManager.switchPage();
    }
}