package app.frontend.mainwindow.listeners.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.frontend.mainwindow.MainWindowManager;

/** 
 * This SuggestionSearchBarActionListener implements ActionListener in order to manage the suggetion event.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class SuggestionSearchBarActionListener implements ActionListener {

    /**
     * The suggestion to show.
     */
    private String suggestion;

    /**
     * Initializes the suggestion value.
     * @param suggestion the suggestion which must be showed.
     */
    public SuggestionSearchBarActionListener(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BackendManager.printDebug("Query suggestion: " + suggestion);
        
        MainWindowManager.getWindowSearchBar().setText(this.suggestion);
        MainWindowManager.updateScollPane(suggestion);
    }
}