package app.frontend.mainwindow.components.buttonbox;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.image.AntiAliasedImageButton;
import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.mainwindow.listeners.buttonpanel.DownloadDynastyImageActionListener;
import app.frontend.mainwindow.listeners.buttonpanel.OpenDynastyLinkActionListener;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.Margin;
import app.frontend.utils.QuattrocentoFont;
/**
 * This ButtonBox extends JPanel in order to creates the panel under the graph umage, which contains the Dynasty Wikipedia button and the Download Dynasty Graph Image.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class ButtonBox extends JPanel {
    private static final long serialVersionUID = 16L;

    /**
     * The Download Button.
     */
    private AntiAliasedImageButton downloadButton;
    /**
     * The wikipedia Button.
     */
    private AntiAliasedImageButton wikipediaButton;
    /**
     * The label of the Download Button.
     */
    private AntiAliasedTextLabel downloadText;
    /**
     * The label of the Wikipedia Button.
     */
    private AntiAliasedTextLabel wikipediaText;

    /**
     * Creates the ButtonBox panel and sets it up.
     */
    public ButtonBox() {
        this.setup();
        this.addComponent();
    }
    
    /**
     * Set the ButtonBox up.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(968,570,287,125);
        this.setBorder(ComponentBorder.ALL_GOLD.get());
        this.setBackground(ComponentColor.WHITE.get());
    }
    
    /**
     * Adds the downloadButton, the wikipediaButton, the downloadText and the wikipediaText to the ButtonBox panel.    
     */
    private void addComponent() {
        this.createDownloadButton();
        this.createWikipediaButton();
        this.createDownloadText();
        this.createWikipediaText();
    }
    
    /**
     * Creates an AntiAliasedImageButton with coordinates, dimensions  and sets a given icon.
     * @param icon the path of the image of the icon
     * @param pressedIcon the path of the image of the icon when the button is pressed.
     * @param x the x-coordinates of the button.
     * @param y the y-coordinates of the button.
     * @param width the width of the button.
     * @param height the height of the button.
     * @return an AntiAliasedImageButton.
     */ 
    private AntiAliasedImageButton createImageButton(String icon, String pressedIcon, int x, int y, int width, int height) {
        AntiAliasedImageButton button = new AntiAliasedImageButton();

        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);
        button.setBorder(null);
        button.setMargin(Margin.EMPTY.get());
        button.setContentAreaFilled(false);
        button.setIcon(ImageLoader.asImageIcon(icon));
        button.setPressedIcon(ImageLoader.asImageIcon(pressedIcon));

        return button;
    }
    /**
     * Creates an AntiAliasedTextLabel with the specified text, coordinates and dimensions.
     * @param text the text of the label.
     * @param x the x-coordinate of the label.
     * @param y the y-coordinate of the label. 
     * @param width the width of the label. 
     * @param height the height of the label.
     * @return the AntiAliasedTextLabel.
     */
    private AntiAliasedTextLabel createLabel(String text, int x, int y, int width, int height) {
        AntiAliasedTextLabel label = new AntiAliasedTextLabel(text);

        label.setBounds(x, y, width, height);
        label.setFont(QuattrocentoFont.PLAIN_20.get());

        return label;
    }
    /**
     * Creates the downloadButton using the createImageButton method and adds it to the ButtonBoxPanel. Adds the DownloadDynastyImageActionListener to the button.
     */
    private void createDownloadButton() {
        this.downloadButton = this.createImageButton(ImagePath.DOWNLOAD_ICON.get(), ImagePath.PRESSED_DOWNLOAD_ICON.get(), 63,28,42,42);
        this.downloadButton.addActionListener(new DownloadDynastyImageActionListener());
        this.add(this.downloadButton);
    }
    
     /**
     * Creates the wikipediaButton using the createImageButton method and adds it to the ButtonBoxPanel. Adds the DownloadDynastyImageActionListener to the button.
     */
    private void createWikipediaButton() {
        this.wikipediaButton = this.createImageButton(ImagePath.WIKIPEDIA_ICON.get(), ImagePath.PRESSED_WIKIPEDIA_ICON.get(), 186,26,48,48);
        this.wikipediaButton.addActionListener(new OpenDynastyLinkActionListener());
        this.add(this.wikipediaButton);
    }
    /**
     * Creates the downloadText using the createLabel method and adds it to the ButtonBoxPanel.
     */
    private void createDownloadText() {
        this.downloadText = this.createLabel("Download", 35,65,100,40);
        this.add(this.downloadText);
    }
    /**
     * Creates the wikipediaText using the createLabel method and adds it to the ButtonBoxPanel.
     */
    private void createWikipediaText() {
        this.wikipediaText= this.createLabel("Dinastia", 172,65,100,40);
        this.add(wikipediaText);
    }
}