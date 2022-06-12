package app.frontend.mainwindow.listeners.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.frontend.mainwindow.MainWindowManager;

public class SuggestionSearchBarActionListener implements ActionListener {
    private String suggestion;

    public SuggestionSearchBarActionListener(String suggestion) {
        this.suggestion = suggestion;
    }

    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BackendManager.printDebug("Query suggestion: " + suggestion);
        
        MainWindowManager.getWindowSearchBar().setText(this.suggestion);
        MainWindowManager.updateScollPane(suggestion);
    }
}