package app.frontend;

import java.awt.Image;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import app.frontend.launcher.LauncherManager;
import app.frontend.mainwindow.MainWindowManager;
import app.frontend.utils.FontLoader;
import app.frontend.utils.FontPath;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.ImageSaver;

/**
 * FrontendManager is the class that manage the user interface of the application.
 * 
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 * 
 */
public final class FrontendManager {
    /**
     * The logo of the application.
     */
    public final static Image APP_LOGO = ImageLoader.asImage(ImagePath.APP_LOGO.get());
    
    /**
     * The title of the application.
     */
    public final static String APP_TITLE = "BloodlineSeeker";
    
    /**
     * Sets up the GUI
     */
    public static void setupGUI() {
        // Sets the system anti-aliasing to true
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        FlatLightLaf.setup();
        
        // Sets the file chooser to read-only
        UIManager.put("FileChooser.readOnly", true); 
        FrontendManager.loadFonts();
    }

    /**
     * Loads the specific font.
     */
    public static void loadFonts() {
        FontLoader.loadFont(FontPath.QUATTROCENTO_FONT_REGULAR.get());
        FontLoader.loadFont(FontPath.QUATTROCENTO_FONT_BOLD.get());
    }

    /**
     * Closes the launcher window and opens the main window.
     */
    public static void switchToMainWindow() {
        LauncherManager.closeWindow();

        MainWindowManager.setupMainWindow();
        MainWindowManager.refresh();
    }

    /**
     * Creates the launcher window.
     */
    public static void createLauncherWindow() {
        LauncherManager.setupLauncher();
    }

    /**
     * Saves the dynasty graph image in a directory chosen by the user.
     */
    public static void saveDynastyGraph() {
        ImageSaver imagesaver = new ImageSaver();
        imagesaver.start();
    }

}