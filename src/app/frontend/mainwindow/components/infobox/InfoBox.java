package app.frontend.mainwindow.components.infobox;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.image.AntiAliasedImageLabel;
import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.QuattrocentoFont;

/**
 * This Infobox extends JPanel in order to create an info box panel.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class InfoBox extends JPanel {
    private static final long serialVersionUID = 18L;

    /**
     * An AntiAliasedImageLabel used for the mouseWheelLabel.
     */
    private AntiAliasedImageLabel mouseWheelLabel;
    
    /**
     * An AntiAliasedImageLabel used for the wasdLabel.
     */
    private AntiAliasedImageLabel wasdLabel;
    
    /**
     * An AntiAliasedTextLabel used for the wasdText.
     */
    private AntiAliasedTextLabel wasdText;
    
    /**
     * An AntiAliasedTextLabel used for the mouseWheelText.
     */
    private AntiAliasedTextLabel mouseWheelText;
    
    /**
     * Sets the InfoBpx up.
     */
    public InfoBox() {
        this.setup();
        this.createComponent();
    }
    
    /**
     * Sets up the InfoBox layout, bound, border and background.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(735, 570, 208, 125);
        this.setBorder(ComponentBorder.ALL_GOLD.get());
        this.setBackground(ComponentColor.WHITE.get());
    }
    
    /**
     * Creates the various component of the info box.
     */
    private void createComponent() {
        this.createWasdIcon();
        this.createMauseWheelIcon();
        this.createWasdText();
        this.createMauseWheelText();
    }
    
    
    /**
     * Creates an AntiAliasedTextLabel with text, coordinates and dimensions. Sets it the bounds and the project font.
     * @param text the text of the label.
     * @param x the x-coordinate of the label.
     * @param y the y-coordinate of the label.
     * @param width the width of the label.
     * @param height the height of the label.
     * @return the new AntiAliasedTextLabel.
     */
    private AntiAliasedTextLabel createTextLabel(String text, int x, int y, int width, int height) {
        AntiAliasedTextLabel label = new AntiAliasedTextLabel(text);

        label.setBounds(x, y, width, height);
        label.setFont(QuattrocentoFont.PLAIN_27.get());

        return label;
    }

    
    /** 
     * Creates an AntiAliasedImageLabel with icon (using the asImageIcon method from Image loader), coordinates and dimensions. Sets it the bounds and the project font.
     * @param icon the icon of the label.
     * @param x the x-coordinate of the label.
     * @param y the y-coordinate of the label.
     * @param width the width of the label.
     * @param height the height of the label.
     * @return the new AntiAliasedImageLabel.
     */
    private AntiAliasedImageLabel createImageLabel(String icon, int x, int y, int width, int height) {
        AntiAliasedImageLabel label = new AntiAliasedImageLabel();

        label.setBounds(x, y, width, height);
        label.setFont(QuattrocentoFont.PLAIN_27.get());
        label.setIcon(ImageLoader.asImageIcon(icon));

        return label;
    }
    
    /**
     * Initialize the wasdIcon using the createImageLabel method and adds it to the info box panel.
     */
    private void createWasdIcon() {
        this.wasdLabel = this.createImageLabel(ImagePath.WASD_ICON.get(), 30, 15, 40,40);
        this.add(this.wasdLabel);
    }
    
    /**
     * Initialize the mouseWheelIcon using the createImageLabel method and adds it to the info box panel.
     */
    private void createMauseWheelIcon() {
        this.mouseWheelLabel = this.createImageLabel(ImagePath.MOUSE_ICON.get(), 34, 72,32,32);
        this.add(this.mouseWheelLabel);
    }
    
    /**
     * Initialize the wasdText using the createImageLabel method and adds it to the info box panel.
     */
    private void createWasdText() {
        this.wasdText = this.createTextLabel("Naviga",95,16,100,40);
        this.add(wasdText);
    }
    
    /**
     * Initialize the mouseWheelText using the createImageLabel method and adds it to the info box panel.
     */
    private void createMauseWheelText() {
        this.mouseWheelText= this.createTextLabel("Zoom",100,70,100,40);
        this.add(mouseWheelText);
    }
}