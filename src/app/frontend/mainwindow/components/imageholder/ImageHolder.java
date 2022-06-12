package app.frontend.mainwindow.components.imageholder;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.image.AntiAliasedImageLabel;
import app.frontend.mainwindow.listeners.imageholder.ImageHolderFocusMouseListener;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;

public class ImageHolder extends JPanel {
    private static final long serialVersionUID = 17L;

    private AntiAliasedImageLabel label;
    
    public ImageHolder() {
        this.setup();
        this.createLabel();
    }
        
    private void setup() {
        this.setLayout(new BorderLayout());
        this.setBounds(735,25,520,520);
        this.setBorder(ComponentBorder.ALL_GOLD.get());
        this.setBackground(ComponentColor.WHITE.get());
    }
    
    private void createLabel() {
        this.label = new AntiAliasedImageLabel();
        this.label.setFocusable(true);
        this.setMouseListener();
        this.add(this.label);
    }

    
    /** 
     * @return AntiAliasedImageLabel
     */
    public AntiAliasedImageLabel getLabel() {
        return this.label;
    }
    
    private void setMouseListener() {
        this.label.addMouseListener(new ImageHolderFocusMouseListener());
    }
}