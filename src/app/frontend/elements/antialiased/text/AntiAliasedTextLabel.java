package app.frontend.elements.antialiased.text;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

/**
 * This AntiAliasedTextLabel extends JLabel to improve the quality of the text in the label.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class AntiAliasedTextLabel extends JLabel {
	/**
	 * ID
	 */
    private static final long serialVersionUID = 7L;

    /**
     * Creates a JLabel instance with no image and with an empty string for the title.The label is centered vertically in its display area.The label's contents, once set, will be displayed on the leading edge of the label's display area.
     */
    public AntiAliasedTextLabel() {
        super();
    }

    /**
     * Creates a JLabel instance with the specified text.The label is aligned against the leading edge of its display area,and centered vertically.
     * @param text The text to be displayed by the label.
     */
    public AntiAliasedTextLabel(String text) {
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