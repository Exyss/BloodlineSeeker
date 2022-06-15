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
     * True if the current page is the load page, false if not.
     */
    boolean isLoadPage;
    
    /**
     * Updates the isLoad value.
     * @param isLoadPage the new boolean value.
     */
    public SwitchPageActionListener(boolean isLoadPage){
        this.isLoadPage=isLoadPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean pageStatus = LauncherManager.getPageStatus();

        // If the page tried to be opened is already loaded, it won't switch pages
        if (isLoadPage != pageStatus){
            LauncherManager.switchPage();
        }
    }
}