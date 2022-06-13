package app.frontend.mainwindow;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import app.backend.BackendManager;
import app.backend.controllers.SearchQueryController;
import app.backend.scraper.results.ScraperResult;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.frontend.FrontendManager;
import app.frontend.mainwindow.components.buttonbox.ButtonBox;
import app.frontend.mainwindow.components.imageholder.ImageController;
import app.frontend.mainwindow.components.imageholder.ImageHolder;
import app.frontend.mainwindow.components.infobox.InfoBox;
import app.frontend.mainwindow.components.scrollpane.ScrollPane;
import app.frontend.mainwindow.components.scrollpane.ScrollPaneController;
import app.frontend.mainwindow.components.searchbar.SearchBar;
import app.frontend.mainwindow.window.MainWindow;

/**
 * This MainWindowManager manage the main window.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class MainWindowManager {
    /**
     * The main window.
     */
    private static MainWindow window;
    /**
     * If is true the window 
     */
    private static boolean isSetup = false;
    
    /**
     * Checks the isSetup boolean value, if is false creates the main window.
     * @return false if the main window is already existing, true if the method creates for the first time the main window.
     */
    public static boolean setupMainWindow() {
        if (isSetup) {
            return false;
        }

        setLookAndFeel();
        createMainWindow();
        
        return true;
    }
    
    /**
     * Creates a main window, sets true isSetup and creates the dynamic components.
     */
    private static void createMainWindow() {
        window = new MainWindow();
        isSetup = true;
        window.createDynamicComponents();
    }

    /**
     * Sets the look and feel in order to improve the quality of the UI components and add some features on it. 
     * @see <a href="https://www.formdev.com/flatlaf/">FlatLightLaf website</a>
     */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            BackendManager.printDebug("An error occurred while trying to set FlatLightLaf");
        }
    }

    /**
     * @return the main window.
     * @throws IllegalStateException if isSetup is false.
     */
    private static MainWindow getWindow() {
        if (!isSetup) {
            throw new IllegalStateException("The MainWindow must first be setup");
        }

        return window;
    }
    
    /**
     * Changes the graph in the window image.
     * @param dynasty the new dynasty of the graph.
     * @param member the member of the dynasty chosen.
     */
    public static void changeGraph(Dynasty dynasty, Member member) {
        BackendManager.setLoadedDynasty(dynasty);
        BackendManager.setLoadedMember(member);

        try {
            BufferedImage graphImage = BackendManager.loadedMemberToBufferedImage();

            getWindowImageController().changeGraph(graphImage);
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to update graph");
        }
    }
    
    
    /** 
     * @return the current graph image.
     */
    public static BufferedImage getImage() {
        if (getWindowImageController().getBrowse()) {
            return getWindowImageController().getGraph();            
        } else {
            return null;
        }
    }
    /**
     * Shows the search results by updating the ScollPane.
     */
    public static void searchMembers() {
        SearchBar searchBar = MainWindowManager.getWindowSearchBar();

        String searchBarText = searchBar.getText();

        String query = searchBarText.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ").trim();

        searchBar.removeFocus();

        if (searchBarText.replaceAll(" ","").equals("")) { // Case when the input is only spaces
            searchBar.setText(null);

            return;
        } else if (query.equals("")) { 
            MainWindowManager.showResultNotFound();
        } else {
            searchBar.setText(query);
            BackendManager.printDebug("Search query: " + query);
            MainWindowManager.updateScollPane(query);
        }
    }
    
    
    /** 
     * Updates the ScollPane with the query results.
     * @param query the search made by the user.
     */
    public static void updateScollPane(String query) {
        ArrayList<ScraperResult> results = SearchQueryController.findMatchingMembersAsResults(query);
        SearchQueryController.sortResults(results);

        if (results.size() == 0) {
            results = SearchQueryController.findMatchingDynastyAsResults(query);
            SearchQueryController.sortResults(results);
        }

        // If there are no results again
        if (results.size() == 0) {
            String suggestion = SearchQueryController.findSuggestion(query);
            showSuggestion(suggestion);

            return;
        }

        getWindowScrollPaneController().showScrollPaneResults(results);
    }

    
    /** 
     * Show a suggestion when possible.
     * @param suggestion the suggestion to be showed.
     */
    public static void showSuggestion(String suggestion) {
        if (suggestion != null) {
            getWindowScrollPaneController().showScrollPaneSuggestion(suggestion);
        } else {
            showResultNotFound();
        }
    }
    
    /**
     * Shows the result not found panel.
     */
    public static void showResultNotFound() {
        getWindowScrollPaneController().showResultNotFoundCard();
    }

    
    /** 
     * Opens a link in the default Browser.
     * @param link the link to be browsed.
     */
    public static void openLink(String link) {
        try {
            URI uri = new URI(link);

            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException e) {
            BackendManager.printDebug("An error occurred while trying to open a Wikipedia page");
        }
    }

    
    /** 
     * Builds a new string using a member name and a Dynasty name.
     * @param member the member to use the name from.
     * @param dynasty the dynasty to use the name from.
     * @return String the built string
     */
    public static String buildNewTitle(Member member, Dynasty dynasty) {
        return " - " + member.getName() + " (" + dynasty.getName() + ")";
    }

    
    /** 
     * Updates the window titles with a new String.
     * @param toAppend the new string that must be showed in the title.
     */
    public static void updateWindowTitle(String toAppend) {
        getWindow().setTitle(FrontendManager.APP_TITLE + toAppend);
    }
    
    
    /** 
     * @return the Window ScrollPaneController.
     */
    public static ScrollPaneController getWindowScrollPaneController() {
        return getWindow().getScrollPaneController();
    }
    
    
    /** 
     * @return the window ImageController.
     */
    public static ImageController getWindowImageController() {
        return getWindow().getImageController();
    }

    
    /** 
     * @return the window ImageHolder.
     */
    public static ImageHolder getWindowImageHolder() {
        return getWindow().getImageHolder();
    }
    
     
     /** 
      * @return the window ScrollPane.
      */
     public static ScrollPane getWindowScrollPane() {
        return getWindow().getScrollPane();
    }

    
    /** 
     * @return the window SearchBar.
     */
    public static SearchBar getWindowSearchBar() {
        return getWindow().getSearchBar();
    }

    
    /** 
     * @return the window ButtonBox.
     */
    public static ButtonBox getWindowButtoBox() {
        return getWindow().getButtonBox();
    }

    
    /** 
     * @return the window InfoBox.
     */
    public static InfoBox getWindowInfoBox() {
        return getWindow().getInfoBox();
    }

    
    /** 
     * @return the window ContentPane
     */
    public static JComponent getWindowContentPane() {
        return (JComponent) getWindow().getContentPane();
    }
    
    /**
     * refreshes the window revalidating it and repainting it.
     */
    public static void refresh() {
        getWindow().revalidate();
        getWindow().repaint();
    }
}