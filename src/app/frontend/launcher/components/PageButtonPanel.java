package app.frontend.launcher.components;

import javax.swing.JPanel;
import javax.swing.border.Border;

import app.frontend.elements.antialiased.text.AntiAliasedTextButton;
import app.frontend.launcher.listeners.SwitchPageActionListener;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.QuattrocentoFont;

/**
 * This PagButtonPanel extends JPanel to customize the panel of the buttons.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class PageButtonPanel extends JPanel {
    /**
     * ID
     */
    private static final long serialVersionUID = 9L;

    /**
     * A String used for the load message.
     */
    private final String LOAD_MSG = "Carica";

    /**
     * A String used for the download message.
     */
    private final String DOWNLOAD_MSG = "Download";

    /**
     * An AntiAliasedTextButton used for a load button.
     */
    private AntiAliasedTextButton loadButton;

    /**
     * An AntiAliasedTextButton used for a download button.
     */
    private AntiAliasedTextButton downloadButton;

    /**
     * Creates a PageButtonPanel.
     */
    public PageButtonPanel() {
        this.setup();
        this.createButtons();
    }

    /**
     * Sets up the settings of the PageButtonPanel.
     */
    public void setup() {
        this.setLayout(null);
        this.setBounds(25, 25, 251, 53);
        this.setOpaque(false);
    }

    /**
     * Creates the loadButton and the downloadButton and adds them in the PageButtonPanel. Adds a switchPageActionListener at the button.
     */
    public void createButtons() {
        this.loadButton = this.createButton(LOAD_MSG,ComponentBorder.WHITE_BOTTOM_ALL_GOLD.get(),0, 0, 98, 53);
        this.add(loadButton);
       
        this.downloadButton = this.createButton(DOWNLOAD_MSG,ComponentBorder.ALL_GOLD.get(),107,  0, 144, 53);
        this.add(downloadButton);
        
        loadButton.addActionListener(new SwitchPageActionListener(true));
        downloadButton.addActionListener(new SwitchPageActionListener(false));
    }

    /**
     * Creates an AntiAliasedTextButton named button with text, coordinates, dimensions and with the project font. the button is not focusable and the rollover is not enabled.
     * @param text the text of the button.
     * @param border the border of the button.
     * @param x the x-coordinate of the button.
     * @param y the y-coordinate of the button.
     * @param width the width of the button.
     * @param height the height of the button.
     * @return the button.
     */
    private AntiAliasedTextButton createButton(String text,Border border, int x, int y, int width, int height) {
        AntiAliasedTextButton button =  new AntiAliasedTextButton(text);

        button.setBorder(border);
        button.setBounds(x, y, width, height);
        button.setFont(QuattrocentoFont.PLAIN_20.get());
        button.setFocusable(false);
        button.setRolloverEnabled(false);

        return button;
    }

    /**
     * @return the loadButton
     */
    public AntiAliasedTextButton getLoadButton() {
        return this.loadButton;
    }

    /**
     * @return the downloadButton
     */
    public AntiAliasedTextButton getDownloadButton() {
        return this.downloadButton;
    }
}