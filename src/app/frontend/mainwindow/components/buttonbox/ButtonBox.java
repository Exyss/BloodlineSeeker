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

public class ButtonBox extends JPanel {
    private static final long serialVersionUID = 16L;

    private AntiAliasedImageButton downloadButton;
    private AntiAliasedImageButton wikipediaButton;
    private AntiAliasedTextLabel downloadText;
    private AntiAliasedTextLabel wikipediaText;

    public ButtonBox() {
        this.setup();
        this.addComponent();
    }
    
    private void setup() {
        this.setLayout(null);
        this.setBounds(964,570,291,125);
        this.setBorder(ComponentBorder.ALL_GOLD.get());
        this.setBackground(ComponentColor.WHITE.get());
    }
    
    private void addComponent() {
        this.createDownloadButton();
        this.createWikipediaButton();
        this.createDownloadText();
        this.createWikipediaText();
    }
    
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

    private AntiAliasedTextLabel createLabel(String text, int x, int y, int width, int height) {
        AntiAliasedTextLabel label = new AntiAliasedTextLabel(text);

        label.setBounds(x, y, width, height);
        label.setFont(QuattrocentoFont.PLAIN_20.get());

        return label;
    }

    private void createDownloadButton() {
        this.downloadButton = this.createImageButton(ImagePath.DOWNLOAD_ICON.get(), ImagePath.PRESSED_DOWNLOAD_ICON.get(), 63,28,42,42);
        this.downloadButton.addActionListener(new DownloadDynastyImageActionListener());
        this.add(this.downloadButton);
    }
    
    private void createWikipediaButton() {
        this.wikipediaButton = this.createImageButton(ImagePath.WIKIPEDIA_ICON.get(), ImagePath.PRESSED_WIKIPEDIA_ICON.get(), 186,26,48,48);
        this.wikipediaButton.addActionListener(new OpenDynastyLinkActionListener());
        this.add(this.wikipediaButton);
    }

    private void createDownloadText() {
        this.downloadText = this.createLabel("Download", 35,65,100,40);
        this.add(this.downloadText);
    }
    
    private void createWikipediaText() {
        this.wikipediaText= this.createLabel("Dinastia", 173,65,100,40);
        this.add(wikipediaText);
    }
}