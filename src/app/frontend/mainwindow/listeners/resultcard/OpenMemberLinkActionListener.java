package app.frontend.mainwindow.listeners.resultcard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.frontend.mainwindow.MainWindowManager;

/** 
 * This OpenMemberLinkActionListener implements ActionListener in order to manage the opening wikipedia links.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class OpenMemberLinkActionListener implements ActionListener {
    /**
     * The wikipedia link to open.
     */
    private String link;

    /**
     * Initializes the link with the text given as parameter.
     *@param link the link to open.
     */
    public OpenMemberLinkActionListener(String link) {
        this.link = link;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        BackendManager.printDebug("Opened a Dynasty Wikipedia link: " + link);

        MainWindowManager.openLink(link);
    }
}