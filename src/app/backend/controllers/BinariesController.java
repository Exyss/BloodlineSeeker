package app.backend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import app.backend.BackendManager;
import app.backend.utils.OperatingSystem;
import app.backend.utils.SeleniumDriver;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizCmdLineEngine;

public class BinariesController {
	
	/**
	 * The temporary system directory path.
	 */
    public static final String TEMP_PATH = BinariesController.getTempPath();

    /**
     * The graphviz local zip path.
     */
    public static final String GRAPHVIZ_LOCAL_ZIP_PATH = "/assets/graphviz/graphviz.zip";
    
    /**
     * The graphviz directory in the temporary system directory path.
     */
    public static final String GRAPHVIZ_TEMP_PATH = TEMP_PATH + "graphviz";
    
    /**
     * The directory for the binary file path of graphviz in the graphviz directory.
     */
    public static final String GRAPHVIZ_TEMP_EXE_PATH = GRAPHVIZ_TEMP_PATH + "/dot.exe";
    
    /**
     * The path of the local drivers.
     */
    public static final String DRIVERS_LOCAL_PATH = "/assets/drivers/";
    
    /**
     * The path of the temporary drivers.
     */
    public static final String DRIVERS_TEMP_PATH = TEMP_PATH + "RomanEmperorsDrivers";

    
    /** 
     * @return the right temporary path depending by the Operative System.
     */
    private static String getTempPath() {
        String tmpPath = System.getProperty("java.io.tmpdir");

        if (!(OperatingSystem.get().equals(OperatingSystem.WINDOWS))) {
            tmpPath += "/";
        }

        return tmpPath;
    }

    /**
     * Extracts the graphviz binary files in GRAPHVIZ_TEMP_EXE_PATH.
     */
    public static void extractGraphVizBinaries() {
        //Run this feature only on Windows
        if (!(OperatingSystem.get().equals(OperatingSystem.WINDOWS))) {
            return;
        }
        
        BackendManager.printDebug("Extracting \"" + GRAPHVIZ_LOCAL_ZIP_PATH + "\" binaries in %TEMP% dir");
        
        try {
            //find zipped binaries
            InputStream inStream = BinariesController.class.getResourceAsStream(GRAPHVIZ_LOCAL_ZIP_PATH);

            //extract binaries
            extractZip(inStream, GRAPHVIZ_TEMP_PATH);
            
            //setup engine
            Graphviz.useEngine(new GraphvizCmdLineEngine(GRAPHVIZ_TEMP_EXE_PATH));
            
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to load GraphViz binaries");
        }
    }

    
    /** 
     * @param driver
     */
    public static void extractDriverBinary(SeleniumDriver driver) {
        String driverZipPath = driver.get(DRIVERS_LOCAL_PATH) + ".zip";
        
        BackendManager.printDebug("Extracting \"" + driverZipPath + "\" binary in %TEMP% dir");
        
        try {
            //find zipped binary
            InputStream inStream = BinariesController.class.getResourceAsStream(driverZipPath);

            //extract binary
            extractZip(inStream, DRIVERS_TEMP_PATH);

            File executable = new File(DRIVERS_TEMP_PATH + "/" + driver.get(""));

            executable.setExecutable(true);
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to load Driver binaries");
        }
    }
    
    
    /** 
     * 
     * @param zipInputStream
     * @param outputPath
     * @throws IOException
     */
    private static void extractZip(InputStream zipInputStream, String outputPath) throws IOException {
        ZipInputStream zipStream = new ZipInputStream(zipInputStream);
        
        File dir = new File(outputPath);

        //Create dirs recursively if not existing
        if (!dir.exists()) {
            Files.createDirectories(dir.toPath());
        }
        
        //Read zipped files and copy them directly
        byte[] buffer = new byte[4096];
        
        try {
            ZipEntry entry;
            FileOutputStream output;
            
            while ((entry = zipStream.getNextEntry()) != null) {

                int len = 0;
                output = new FileOutputStream(outputPath + "/" + entry.getName());
                
                while ((len = zipStream.read(buffer)) > 0) {
                    output.write(buffer, 0, len);
                }
                
                output.close();
            }
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to extract a zipped binary");
        } finally {
            zipStream.close();
            zipInputStream.close();
        }
    }
    
    
    /** 
     * Removes a specified directory.
     * @param folderToRemove the directory path to remove.
     */
    private static void removeTempDirectory(String folderToRemove) {
        File tempDirectory = new File(folderToRemove);

        if (tempDirectory.exists()) {
            for (File tempDriver : tempDirectory.listFiles()) {
                tempDriver.delete();
            }

            tempDirectory.delete();
        }
    }

    /**
     * Removes the temporary directories.
     */
    public static void clearTempDirectories() {
        removeTempDirectory(GRAPHVIZ_TEMP_PATH);
        removeTempDirectory(DRIVERS_TEMP_PATH);
    }
}