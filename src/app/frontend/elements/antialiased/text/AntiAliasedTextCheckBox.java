package app.frontend.elements.antialiased.text;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JCheckBox;

/**
 * This AntiAliasedCheckBox extends JCheckBox to improve the quality of the text of the check box.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class AntiAliasedTextCheckBox extends JCheckBox {
    /**
     * ID 
     */
    private static final long serialVersionUID = 4L;

    /**
     * Creates an initially unselected check box button with no text.
     */
    public AntiAliasedTextCheckBox() {
        super();
    }

    /**
     * Creates an initially unselected check box with text.
     * @param text the text of the check box.
     */
    public AntiAliasedTextCheckBox(String text) {
        super(text);
    }

    /**
     * Creates a check box with text and specifies whether or not it is initially selected.
     * @param test the text of the check box.
     * @param bool a boolean value indicating the initial selection state. If true the check box is selected.
     */
    public AntiAliasedTextCheckBox(String test , boolean bool) {
        super(test,bool);
    }

    /**
     * Override of the paintComponent for the Swing element which enables the anti-aliasing for better resolution.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics2D.setRenderingHints(renderingHints);
        super.paintComponent(graphics2D);
    }
}