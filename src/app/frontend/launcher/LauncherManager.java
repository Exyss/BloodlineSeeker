package app.frontend.launcher;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import app.backend.BackendManager;
import app.frontend.elements.antialiased.text.AntiAliasedTextButton;
import app.frontend.elements.antialiased.text.AntiAliasedTextCheckBox;
import app.frontend.launcher.components.PageButtonPanel;
import app.frontend.launcher.components.consolepage.ConsoleRunner;
import app.frontend.launcher.components.downloadpage.CheckBoxPanel;
import app.frontend.launcher.components.downloadpage.DownloadPage;
import app.frontend.launcher.components.loadpage.LoadPage;
import app.frontend.launcher.window.LauncherWindow;
import app.frontend.utils.ComponentBorder;

/**
 * This LauncherManager manage the execution of a launcher window.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class LauncherManager {
	/**
	 * A LauncherWindow object named window.
	 */
    private static LauncherWindow window;

    /**
     * A boolean variable which is true if the window is 
     */
    private static boolean isSetup = false;

    /**
     * A boolean variable which saves which page is currently loaded in the launcher.
     */
    private static boolean pageStatus = true;

    /**
     * @return true if it was able to create the launcher window, false if it wasn't.
     */
    public static boolean setupLauncher() {
        if (isSetup) {
            return false;
        }

        setLookAndFeel();
        createLauncherWindow();

        isSetup = true;

        return true;
    }

    /**
     * Creates a LauncherWindow.
     */
    private static void createLauncherWindow() {
        window = new LauncherWindow();
    }
    
    /**
     * Sets the look-and-feel of the launcher window.
     */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            BackendManager.printDebug("An error occurred while trying to set FlatLightLaf");
        }
    }

    /**
     * @throws IllegalStateException if isSetup is false.
     * @return the LauncherWindow.
     */
    private static LauncherWindow getWindow() {
        if (!isSetup) {
            throw new IllegalStateException("The Launcher must first be setup");
        }

        return window;
    }

    /**
     * @return the PageButtonPanel of the window.
     */
    public static PageButtonPanel getWindowPageButtonPanel() {
        return getWindow().getPageButtonPanel();
    }

    /**
     * @return the contentPanle of the window.
     */
    public static JPanel getWindowContentPanel() {
        return getWindow().getContentPanel();
    }

    /**
     * @return the loadPage of the window
     */
    public static LoadPage getWindowLoadPage() {
        return getWindow().getLoadPage();
    }

    /**
     * @return the downloadPage of the window.
     */
    public static DownloadPage getWindowDownloadPage() {
        return getWindow().getDownloadPage();
    }

    /**
     * Dispose the window.
     */
    public static void closeWindow() {
        getWindow().dispose();
    }

    /**
     * Sets the window title.
     * @param newWindowTitle the String of the new title.
     */
    public static void setWindowTitle(String newWindowTitle) {
        getWindow().setWindowTitle(newWindowTitle);
    }

    /**
     * Shows up the console and starts the ConsoleRunner.
     */
    public static void switchToConsolePage() {
        final String DOWNLOADING_STATE_TITLE = " - Download in corso...";

        getWindow().showConsole();

        LauncherManager.setWindowTitle(getWindowTitle() + DOWNLOADING_STATE_TITLE);
        
        ConsoleRunner consoleRunner = new ConsoleRunner();
        consoleRunner.start();
    }

    /**
     * @return the window title.
     */
    public static String getWindowTitle() {
        return getWindow().getWindowTitle();
    }

    /**
     * Toggles the use of Selenium for the scraping.
     */
    public static void toggleSelenium() {
        CheckBoxPanel checkBoxPanel = getWindowDownloadPage().getCheckBoxPanel();

        AntiAliasedTextCheckBox HTTPCheckBox = checkBoxPanel.getHTTP();
        AntiAliasedTextCheckBox SeleniumCheckBox = checkBoxPanel.getSelenium();

        boolean seleniumMode = BackendManager.getSeleniumMode();

        if (seleniumMode) {
            SeleniumCheckBox.setEnabled(true);
            SeleniumCheckBox.setSelected(false);

            HTTPCheckBox.setEnabled(false);
        } else {
            HTTPCheckBox.setEnabled(true);
            HTTPCheckBox.setSelected(false);

            SeleniumCheckBox.setEnabled(false);
        }

        BackendManager.setSeleniumMode(!seleniumMode);
    }

    /**
     * Switches the current loaded page in the launcher window.
     */
    public static void switchPage() {
        JPanel contentPanel = getWindowContentPanel();
        contentPanel.removeAll();
        
        AntiAliasedTextButton switchToLoadPageButton = getWindowPageButtonPanel().getLoadButton();
        AntiAliasedTextButton switchToDownloadPageButton = getWindowPageButtonPanel().getDownloadButton();
        
        if (pageStatus) {
            contentPanel.add(getWindowDownloadPage());

            contentPanel.revalidate();
            contentPanel.repaint();

            switchToLoadPageButton.setBorder(ComponentBorder.ALL_GOLD.get());
            switchToDownloadPageButton.setBorder(ComponentBorder.WHITE_BOTTOM_ALL_GOLD.get());

        } else {
            contentPanel.add(getWindowLoadPage());
            
            contentPanel.revalidate();
            contentPanel.repaint();
            
            switchToLoadPageButton.setBorder(ComponentBorder.WHITE_BOTTOM_ALL_GOLD.get());
            switchToDownloadPageButton.setBorder(ComponentBorder.ALL_GOLD.get());
        }

        pageStatus = !pageStatus;
    }

    /**
     * Updates the console to a new status.
     * @param newStatus the new status of the console.
     */
    public static void updateConsole(String newStatus) {
        getWindow().getConsolePage().updateConsole(newStatus);
    }
}