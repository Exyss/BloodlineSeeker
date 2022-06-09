package app.frontend.elements.antialiased.text;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

/**
 * This AntiAliasedTextButton extends JButton to improve the quality of the text in the button.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class AntiAliasedTextButton extends JButton {
    /**
     * ID 
     */
    private static final long serialVersionUID = 3L;

    /**
     * Creates a button with no set text.
     */
    public AntiAliasedTextButton() {
        super();
    }

    /**
     * Creates a button with text.
     * @param text the text of the button.
     */
    public AntiAliasedTextButton(String text) {
        super(text);
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