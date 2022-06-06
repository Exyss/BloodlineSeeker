package app.frontend.launcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.launcher.LauncherManager;

/**
 * This CheckBoxActionListener implements ActionListener in order to know when a check box is selected. When this happens, the Selenium check box is toggled.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class CheckBoxActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        LauncherManager.toggleSelenium();
    }
}