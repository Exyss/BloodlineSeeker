package app.frontend.launcher.components.downloadpage;

import java.util.Arrays;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextCheckBox;
import app.frontend.launcher.listeners.CheckBoxActionListener;
import app.frontend.utils.QuattrocentoFont;

/**
 * This CheckBoxPanel extends JPanel in order to customize the check box panel.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class CheckBoxPanel extends JPanel {
	/**
	 * ID
	 */
    private static final long serialVersionUID = 12L;
    
    /**
     * The space from the check box and the text.
     */
    private final int SPACING_AMOUNT = 10;

    /**
     * The char used for the spacing.
     */
    private final char SPACING_CHAR = ' ';
    
    /**
     * The text used for the Selenium check box.
     */
    private final String SELENIUM_CHECKBOX_MSG;

    /**
     * The text used for the HTTP check box.
     */
    private final String HTTP_CHECKBOX_MSG;
    
    /**
     * An AntiAliasedTextCheckBox used for the Selenium check box.
     */
    private AntiAliasedTextCheckBox seleniumCheckBox;

    /**
     * An AntiAliasedTextCheckBox used for the HTTP check box.
     */
    private AntiAliasedTextCheckBox httpCheckBox;

    /**
     * Creates a CheckBoxPanel, sets it up and adds the space at the check box message.
     */
    public CheckBoxPanel() {
        String spacing = buildRepeatedCharString(SPACING_CHAR, SPACING_AMOUNT);

        SELENIUM_CHECKBOX_MSG = spacing + "Scarica le dinastie con Selenium";
        HTTP_CHECKBOX_MSG = spacing + "Scarica le dinastie con richieste HTTP";
        
        this.setup();
        this.createCheckBox();
    }
    
    /**
     * Sets up the settings of the CheckBoxPanel.
     */
    private void setup() {
        this.setBounds(0,90,506,140);
        this.setLayout(null);
        this.setOpaque(false);
    }
    
    /**
     * Creates the Selenium and the HTTP check boxes. Adds the CheckBoxActionListener at both check boxes.
     */
    private void createCheckBox() {
        this.seleniumCheckBox = createCheckBox(SELENIUM_CHECKBOX_MSG, false, 75, 35, 506, 20);
        this.add(seleniumCheckBox);
        
        this.httpCheckBox = createCheckBox(HTTP_CHECKBOX_MSG, true, 75, 75, 506,20);
        this.httpCheckBox.setEnabled(false);
        
        this.add(httpCheckBox);
        
        this.seleniumCheckBox.addActionListener(new CheckBoxActionListener());
        this.httpCheckBox.addActionListener(new CheckBoxActionListener());
    }
    
    /**
     * Creates an AntiAliasedTextCheckBox with a text, a boolean to choose if it start selected or not, coordinates and dimensions.
     * @param checkboxMsg the text of the check box.
     * @param selected if true creates an already selected check box, else creates a not selected check box.
     * @param x the x-coordinate of the check box.
     * @param y the y-coordinate of the check box.
     * @param width the width of the check box.
     * @param height the height of the check box.
     * @return the AntiAliasedTextCheckBox check box.
     */
    private AntiAliasedTextCheckBox createCheckBox(String checkboxMsg, boolean selected, int x, int y, int width, int height) {
        AntiAliasedTextCheckBox checkBox = new AntiAliasedTextCheckBox(checkboxMsg, selected);

        checkBox.setFont(QuattrocentoFont.PLAIN_20.get());
        checkBox.setBounds(x, y, width, height);

        return checkBox;
    }
    
    /**
     * Create a string with the repetition of a char.
     * @param ch the char to use for the repetition.
     * @param amount the length of the string
     * @return the string
     */
    private String buildRepeatedCharString(char ch, int amount) {
        char[] charArray = new char[amount];
        Arrays.fill(charArray, ch);

        return new String(charArray);
    }
    
    /**
     * @return the HTTP check box.
     */
    public AntiAliasedTextCheckBox getHTTP() {
        return this.httpCheckBox;
    }

    /**
     * @return the Selenium check box.
     */
    public AntiAliasedTextCheckBox getSelenium() {
        return this.seleniumCheckBox;
    }
}