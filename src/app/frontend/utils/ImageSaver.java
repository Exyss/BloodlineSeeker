package app.frontend.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.JFileChooser;

import com.idrsolutions.image.JDeli;

import app.backend.BackendManager;
import app.frontend.elements.antialiased.text.AntialiasedTextFileChooser;

public class ImageSaver extends Thread{
    
    /**
     * Saves the dynasty graph image in a directory chosen by the user.
     * @throws Exception
     */
    public  void saveDynastyGraph()  {
        
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
            
            if (choice == JFileChooser.APPROVE_OPTION) {
                String folderName = fileChooser.getSelectedFile().getPath();
                
                String filename = this.createFileName(folderName, dynastyName, FILE_EXTENSION);
                
                File file = new File(filename);
                BufferedImage graph = BackendManager.loadedDynastyToBufferedImage();
                JDeli.write(graph,FILE_EXTENSION,file);
            }
        } catch (Exception e) {
            BackendManager.printDebug("An error occurred while trying to save the dynasty graph image");
        }
    }

    private String createFileName(String folderName,String fileName,String extension) throws FileNotFoundException {
        int i = 1; 
        extension = "."+extension;
        fileName= "/" + fileName;

        File directory = new File(folderName);

        if (directory.isDirectory()){
            
            File[] files = directory.listFiles();
            String possibleName = fileName + extension;

            while(Arrays.stream(files).anyMatch(new File (folderName + possibleName):: equals)){
                
                possibleName = fileName+ "(" + i + ")" +extension;
                i+=1;
            }
            return folderName + possibleName;
        }else{
            throw new FileNotFoundException("Directory not found");
        }
    }

    @Override
    public void run() {
        this.saveDynastyGraph();
    }
}
