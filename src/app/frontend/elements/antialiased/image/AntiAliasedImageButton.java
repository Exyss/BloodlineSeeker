package app.frontend.elements.antialiased.image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

/**
 * This AntiAliasedImageButton extends JButton to improve the quality of the image of the button.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class AntiAliasedImageButton extends JButton {
    /**
	 * ID 
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Override of the paintComponent for the Swing element which enables the anti-aliasing for better resolution.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        graphics2D.setRenderingHints(renderingHints);
        super.paintComponent(graphics2D);
    }
}