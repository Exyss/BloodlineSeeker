package app.frontend.mainwindow.components.imageholder;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.image.AntiAliasedImageLabel;
import app.frontend.mainwindow.listeners.imageholder.ImageHolderFocusMouseListener;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;

/**
 * This ImageHolder extends JPanel in order to create a panel in which the image is shown.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class ImageHolder extends JPanel {
    private static final long serialVersionUID = 17L;
    
    /**
     * An AntiAliasedImageLabel.
     */
    private AntiAliasedImageLabel label;
    
    /**
     * Creates an ImageHolder with a label and sets it up.
     */
    public ImageHolder() {
        this.setup();
        this.createLabel();
    }
       
    /**
     * Sets up the ImageHolder layout, bounds, border and background.
     */
    private void setup() {
        this.setLayout(new BorderLayout());
        this.setBounds(735,25,520,520);
        this.setBorder(ComponentBorder.ALL_GOLD.get());
        this.setBackground(ComponentColor.WHITE.get());
    }
    
    /**
     * Inizializes the label setting it focusable, setting an action listener by the setMouseListener method and adding it to the ImageHolder.
     */
    private void createLabel() {
        this.label = new AntiAliasedImageLabel();
        this.label.setFocusable(true);
        this.setMouseListener();
        this.add(this.label);
    }

    
    /** 
     * @return the AntiAliasedImageLabel.
     */
    public AntiAliasedImageLabel getLabel() {
        return this.label;
    }
    
    /**
     * Adds to the label an ImageHolderFocusMouseListener.
     */
    private void setMouseListener() {
        this.label.addMouseListener(new ImageHolderFocusMouseListener());
    }
}