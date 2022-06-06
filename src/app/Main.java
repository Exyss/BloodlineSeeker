package app;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import app.backend.BackendManager;
import app.backend.controllers.BinariesController;
import app.frontend.FrontendManager;

public class Main {
    public static void main(String[] args) {
        Main.programConfiguration();
        Main.programInitialization();

        boolean doRun = Main.parseArgs(args);

        if (doRun) {
            Main.run();
        }
    }
    
    private static void programConfiguration() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        System.setProperty("java.awt.headless", "false");
    }
    
    private static void programInitialization() {
        BinariesController.clearTempDirectories();
        BinariesController.extractGraphVizBinaries();
    }

    private static void run() {
        FrontendManager.setupGUI();
        FrontendManager.createLauncherWindow();
    }
    
    private static boolean parseArgs(String[] inputArgs) {
        List<String> args = Arrays.asList(inputArgs);
        
        if (args.contains("--help") || args.contains("-h")) {
            printHelp();

            return false;   //close the program after running help
        }
        
        if (args.contains("--debug") || args.contains("-d")) {
            BackendManager.setDebugMode(true);
        }
        
        if (args.contains("--no-headless") || args.contains("-nh")) {
            BackendManager.setHeadlessMode(false);
        }
        
        if (args.contains("--run-tests") || args.contains("-t")) {
            Main.runTests();
        }

        return true;
    }

    private static void runTests() {}

    private static void printHelp() {
        System.out.println("");
        System.out.println("Welcome to " + FrontendManager.APP_TITLE + ", which is a program able to");
        System.out.println("scrape roman emperor' dynasties from Wikipedia");
            
        String juliusCeasar = String.join("\n",
            "                              ",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣶⣿⣿⢿⡶⠆⠀⠀⠀⢀⡀⠀⠀⠀⠀⠀ " ,
            "⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⣿⣿⡿⠻⠋⣠⠀⢀⣶⠇⢠⣾⡿⠁⠀⠀⠀⠀⠀ " ,
            "⠀⠀⠀⠀⠀⠀⢀⣼⠟⠋⠻⢁⣴⠀⣾⣿⠀⠾⠟⠀⠈⣉⣠⣦⡤⠀⠀⠀⠀ " ,
            "⠀⠀⠀⠀⠀⠀⠸⠃⣠⡆⠀⣿⡟⠀⠛⠃⠀⠀⣶⣶⣦⣄⠉⢁⡄⠀⠀⠀⠀ " ,
            " ⠀⠀⠀⠀⣰⡀⢰⣿⠇⠀⢉⣀⣀⠛⠿⠿⠦⠀⢀⣠⣤⣴⣾⡇⠀⠀⠀⠀ " ,
            "⠀⠀⠀⠀⠀⣿⠃⠀⠠⣴⣦⡈⠙⠛⠓⠀⢰⣶⣶⣿⣿⣿⣿⣿⣧⡀⠀⠀⠀ " ,
            "⠀⠀⢀⣤⠦⡀⠰⢷⣦⠈⠉⠉⠀⣰⣶⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀  " ,
            "⠀⠀⠈⠁⠀⠘⣶⣤⣄⣀⣨⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠃⠀  " ,
            "⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀ " ,
            "⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⣿⣯⡈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀ " ,
            "⠀⠀⠀⠀⠀⠀⠀⠀⢨⣿⣿⣿⣷⣤⣈⡉⠛⠛⠛⠛⠻⠟⠛⠛⠛⠀⠀⠀⠀ " ,
            "⠀⠀⠀⠀⠀⠀⠀⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" ,
            "⠀⠀⠀⠀⠀⠀⠀⠙⠻⠿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" ,
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 
        );

        System.out.println(juliusCeasar);
    }
}