package app.frontend.launcher.components.consolepage;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.QuattrocentoFont;

/**
 * This ConsolePage extends JPanel in order to create a customized panel for a console.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class ConsolePage extends JPanel {
	/**
	 * ID
	 */
    private static final long serialVersionUID = 11L;

    /**
     * The maxinum number of lines showed by the ConsolePage
     */
    private final int CONSOLE_MAX_LINES = 17;

    /**
     * The AntiAliasedTextLabel used to show the console lines.
     */
    private AntiAliasedTextLabel consoleOutput;

    /**
     * The data structure used to implements the console
     */
    private Queue<String> linesQueue;

    /**
     * The last line of the console.
     */
    private String lastLine;

    /**
     * Creates a console page and sets it up.
     */
    public ConsolePage() {
        linesQueue = new LinkedList<String>();
        lastLine = "";

        this.setup();
        this.createConsoleOutputLabel();
    }

    /**
     * Sets up the console page.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(25, 25, 518, 355);
        this.setBackground(ComponentColor.WHITE.get());
        this.setBorder(ComponentBorder.ALL_GOLD.get());
    }

    /**
     * Create the consoleOutputLabel, sets the alignment, the bounce and the project font and adds it to the consolePage panel. 
     */
    private void createConsoleOutputLabel() {
        consoleOutput = new AntiAliasedTextLabel();
        consoleOutput.setVerticalAlignment(JLabel.TOP);
        consoleOutput.setBounds(25, 25, 468, 315);
        consoleOutput.setOpaque(false);
        consoleOutput.setFont(QuattrocentoFont.PLAIN_15.get());
        this.add(consoleOutput);
    }

    /**
     * @return the String builder used to show the lines like a console.
     */
    private String formatOutput() {
        StringBuilder formattedOutput = new StringBuilder();

        formattedOutput.append("<html>");

        for (String queueString : linesQueue) {
            formattedOutput.append(queueString);
            formattedOutput.append("<br>");
        }

        formattedOutput.append("</html>");

        return formattedOutput.toString();
    }

    /**
     * Update the console.
     * @param newLine the new line to be showed.
     */
    public void updateConsole(String newLine) {
        if (!lastLine.equals(newLine)) {
            lastLine = newLine;

            if (linesQueue.size() > CONSOLE_MAX_LINES) {
                linesQueue.poll();
            }

            linesQueue.add(lastLine);
            
            consoleOutput.setText(formatOutput());
        }
    }
}