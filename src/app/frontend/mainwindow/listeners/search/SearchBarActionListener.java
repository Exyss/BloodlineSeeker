package app.frontend.mainwindow.listeners.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.mainwindow.MainWindowManager;

public class SearchBarActionListener implements ActionListener {
    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.searchMembers();
    }
}