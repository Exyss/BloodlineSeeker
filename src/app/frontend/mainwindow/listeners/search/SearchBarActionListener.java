package app.frontend.mainwindow.listeners.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.frontend.mainwindow.MainWindowManager;

/** 
 * This SearchBarActionListener implements ActionListener in order to manage the searching operation.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class SearchBarActionListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.searchMembers();
    }
}