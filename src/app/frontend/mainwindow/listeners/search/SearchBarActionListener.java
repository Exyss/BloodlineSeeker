package app.frontend.mainwindow.listeners.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.frontend.mainwindow.MainWindowManager;
import app.frontend.mainwindow.components.searchbar.SearchBar;

public class SearchBarActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SearchBar searchBar = MainWindowManager.getWindowSearchBar();
        String query = searchBar.getText().replaceAll("[^a-zA-Z]"," ").trim();

        searchBar.removeFocus();
        
        if (!query.equals("")) {
            searchBar.setText(query);
            BackendManager.printDebug("Search query: " + query);
            MainWindowManager.updateScollPane(query);
        } else {
            searchBar.setText(null);
        }
    }
}