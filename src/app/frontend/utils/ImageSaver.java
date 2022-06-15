package app.frontend.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.JFileChooser;

import com.idrsolutions.image.JDeli;

import app.backend.BackendManager;
import app.frontend.elements.antialiased.text.AntialiasedTextFileChooser;

/**
 * This ImageSaver extends Thread in order to save an image within a specific thread.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class ImageSaver extends Thread {
    /**
     * Saves the dynasty graph image in a directory chosen by the user.
     */
    public void saveDynastyGraph()  {
        if (BackendManager.getLoadedDynasty() == null) {
            return;
        }

        final String FILE_EXTENSION = "png";

        AntialiasedTextFileChooser fileChooser = new AntialiasedTextFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(true);
        
        try {
            String dynastyName = BackendManager.getLoadedDynasty().getName();
            
            int choice = fileChooser.showSaveDialog(null);
            
            // If user clicked on the "save" button
            if (choice == JFileChooser.APPROVE_OPTION) {
                String folderName = fileChooser.getSelectedFile().getPath();
                
                String filename = this.createFileName(folderName, dynastyName, FILE_EXTENSION);
                
                File file = new File(filename);
                BufferedImage graph = BackendManager.loadedDynastyToBufferedImage();

                JDeli.write(graph, FILE_EXTENSION, file);
            }
        } catch (Exception e) {
            BackendManager.printDebug("An error occurred while trying to save the dynasty graph image");
        }
    }

    /**
     * Creates a file name, and if it already exists generates other file names procedurally.
     * @param folderName the folder where the file will be saved.
     * @param fileName the file name not processed.
     * @param extension the extension of the file.
     * @return the final file name.
     * @throws FileNotFoundException if the method don't find the directory. 
     */
    private String createFileName(String folderName,String fileName,String extension) throws FileNotFoundException {
        int i = 1; 

        extension = "."+extension;
        fileName= "/" + fileName;

        File directory = new File(folderName);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            String possibleName = fileName + extension;

            // Until there are any files called as "possibleName" in the given folder,
            // re-evaluate "possibleName" accordingly
            while (Arrays.stream(files).anyMatch(new File(folderName + possibleName)::equals)) {
                possibleName = fileName + "(" + i + ")" + extension;

                i+=1;
            }
            return folderName + possibleName;
        } else {
            throw new FileNotFoundException("Directory not found");
        }
    }

    @Override
    public void run() {
        this.saveDynastyGraph();
    }
}
