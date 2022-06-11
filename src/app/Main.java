package app;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import app.backend.BackendManager;
import app.backend.controllers.BinariesController;
import app.frontend.FrontendManager;
import app.tests.DynastyTester;
import app.tests.WikipediaScraperTester;

public class Main {
    private static final String VERSION = "1.0";

    public static void main(String[] args) {
        Main.programConfiguration();
        Main.programInitialization();

        boolean doRun = Main.parseArgs(args);

        if (doRun) {
            Main.run();
        }
    }

    private static void run() {
        FrontendManager.setupGUI();
        FrontendManager.createLauncherWindow();
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
    
    private static boolean parseArgs(String[] inputArgs) {
        List<String> args = Arrays.asList(inputArgs);
        
        if (args.contains("--help") || args.contains("-h")) {
            printHelp();

            return false;   // Close the program after running help
        }
        
        if (args.contains("--version") || args.contains("-v")) {
            printVersion();

            return false; // Close the program after running help
        }

        if (args.contains("--debug") || args.contains("-d")) {
            BackendManager.setDebugMode(true);
        }
        
        if (args.contains("--no-headless") || args.contains("-nh")) {
            BackendManager.setHeadlessMode(false);
        }
        
        if (args.contains("--run-tests") || args.contains("-t")) {
            Main.runTests();

            return false;   // Close the program after running help
        }

        return true;
    }

    private static void runTests() {
        System.out.println("Running Tests:\\n");

        int passedTests = 0;

        passedTests += DynastyTester.runTests();
        passedTests += WikipediaScraperTester.runTests();

        int totalTests = 0;

        totalTests += DynastyTester.getTotalTests();
        totalTests += WikipediaScraperTester.getTotalTests();

        System.out.println("\\nTests completed\\n");

        int successRate = (int) Math.round(((double) passedTests) / ((double) totalTests) * 100.0);

        System.out.println("Success rate: " + successRate + "%");
    }

    private static void printHelp() {
        String titleASCIIArt = String.join("\n",
            "  ____  _                 _ _ _             _____           _             ",
            " |  _ \\| |               | | (_)           / ____|         | |            ",
            " | |_) | | ___   ___   __| | |_ _ __   ___| (___   ___  ___| | _____ _ __ ",
            " |  _ <| |/ _ \\ / _ \\ / _` | | | '_ \\ / _ \\\\___ \\ / _ \\/ _ \\ |/ / _ \\ '__|",
            " | |_) | | (_) | (_) | (_| | | | | | |  __/____) |  __/  __/   <  __/ |   ",
            " |____/|_|\\___/ \\___/ \\__,_|_|_|_| |_|\\___|_____/ \\___|\\___|_|\\_\\___|_|   "
        );

        System.out.println(titleASCIIArt);

        System.out.println("");
        System.out.println("Welcome to " + FrontendManager.APP_TITLE + ", which is a program able to");
        System.out.println("scrape roman emperors' dynasties from Wikipedia.");

        System.out.println("");
        System.out.println("java BloodlineSeeker.jar [options]");
        System.out.println("");

        System.out.println("  -h,  --help:           shows this help message");
        System.out.println("  -v,  --version:        shows the version of the program");
        System.out.println("  -d,  --debug:          enables the debug mode");
        System.out.println("  -nh, --no-headless:    disables the headless mode for selenium, if used");
        System.out.println("  -t,  --run-tests:      runs the tests on the program");
    }

    private static void printVersion() {
        System.out.println("Version " + Main.VERSION);
    }
}