package app.frontend.mainwindow.listeners.resultcard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.frontend.mainwindow.MainWindowManager;

public class OpenMemberLinkActionListener implements ActionListener {
    private String link;

    public OpenMemberLinkActionListener(String link) {
        this.link = link;
    }
    
    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BackendManager.printDebug("Opened a Dynasty Wikipedia link: " + link);

        MainWindowManager.openLink(link);
    }
}