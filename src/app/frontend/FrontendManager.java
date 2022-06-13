package app.frontend;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

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
        BackendManager.printDebug("Download button pressed");
        
        if (BackendManager.getLoadedDynasty() == null) {
            return;
        }

        final String FILE_EXTENSION = "png";

        AntialiasedTextFileChooser fileChooser = new AntialiasedTextFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(true);
        
        try {
            BufferedImage graph = BackendManager.loadedDynastyToBufferedImage(); ////////////
            
            String dynastyName = BackendManager.getLoadedDynasty().getName();
            
            int choice = fileChooser.showSaveDialog(null);

            if (choice == JFileChooser.APPROVE_OPTION) {
                String folderName = fileChooser.getSelectedFile().getPath();
                
                String filename = FrontendManager.createFileName(folderName, dynastyName, FILE_EXTENSION);
                System.out.println(filename);

                File file = new File(filename);
                ImageIO.write(graph, FILE_EXTENSION,  file);
            }
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to save the dynasty graph image");
        }
    }

    private static String createFileName(String folderName,String fileName,String extension) throws FileNotFoundException {
        int i = 1; 
        extension = "."+extension;
        fileName= "/" + fileName  +extension;

        File directory = new File(folderName);

        if (directory.isDirectory()){
        
            File[] files = directory.listFiles();
            
            while(Arrays.stream(files).anyMatch(new File (folderName + fileName):: equals)){
                fileName = fileName+ "(" + i + ")" +extension;
                i+=1;
            }
        }else{
            throw new FileNotFoundException("Directory not found");
        }
        return folderName + fileName;
    }
}