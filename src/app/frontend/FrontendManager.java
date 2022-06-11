package app.frontend;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import app.backend.BackendManager;
import app.frontend.elements.antialiased.text.AntialiasedTextFileChooser;
import app.frontend.launcher.LauncherManager;
import app.frontend.mainwindow.MainWindowManager;
import app.frontend.utils.FontLoader;
import app.frontend.utils.FontPath;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;

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
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        FlatLightLaf.setup();
        
        UIManager.put("FileChooser.readOnly", true); 
        loadFonts();
    }

    /**
     * Loads the specific font.
     */
    public static void loadFonts() {
        FontLoader.loadFont(FontPath.QUATTROCENTO_FONT_REGULAR.get());
        FontLoader.loadFont(FontPath.QUATTROCENTO_FONT_BOLD.get());
    }

    /**
     * Close the launch window and open the main window.
     */
    public static void switchToMainWindow() {
        LauncherManager.closeWindow();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            //Do nothing
        }

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
     * Saves a dynasty graph image in a directory chosen by the user.
     */
    public static void saveDynastyGraph() {
        final String FILE_EXTENTION = "png";

        String name = BackendManager.getLoadedDynasty().getName() + ".png";

        AntialiasedTextFileChooser fileChooser = new AntialiasedTextFileChooser();
        BufferedImage graph;
        BackendManager.printDebug("Download button pressed");
        
        if (BackendManager.getLoadedDynasty() == null) {
            return;
        }
        
        try {
            graph = BackendManager.loadedDynastyToBufferedImage();
            
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(true);
            int choice = fileChooser.showSaveDialog(null);

            if (choice == JFileChooser.APPROVE_OPTION) {
                String folderName = fileChooser.getSelectedFile().getPath();
                File folder = new File(folderName);

                if (folder.isDirectory()) {
                    String filename = folderName + "/" + name;
                    File file = new File(filename);

                    ImageIO.write(graph, FILE_EXTENTION,  file);
                } else {
                    throw new FileNotFoundException();
                }
            }
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to save the dynasty graph image");
        }
    }
}