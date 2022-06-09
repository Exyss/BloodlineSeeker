package app.frontend.launcher.components;

import app.frontend.elements.antialiased.text.AntiAliasedTextButton;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.QuattrocentoFont;

/**
 * This StartButton extends AntiAliasedTextButton to customize the text of the buttons.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class StartButton extends AntiAliasedTextButton {
    /**
     * ID
     */
    private static final long serialVersionUID = 10L;

    /**
     * The text of the button.
     */
    private final String TEXT = "Avvia";

    /**
     * Creates a StartButton and sets it up.
     */
    public StartButton() {
        this.setup();
    }

    /**
     * Sets up the settings of the buttons.
     */
    private void setup() {
        this.setText(TEXT);
        this.setBounds(183, 230, 140, 50);
        this.setBackground(ComponentColor.PERSIAN_RED.get());
        this.setBorder(ComponentBorder.ALL_GOLD.get());
        this.setForeground(ComponentColor.WHITE.get());
        this.setFont(QuattrocentoFont.PLAIN_30.get());
    }
}