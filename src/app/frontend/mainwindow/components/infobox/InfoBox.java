package app.frontend.mainwindow.components.infobox;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.image.AntiAliasedImageLabel;
import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.QuattrocentoFont;

public class InfoBox extends JPanel {
    private static final long serialVersionUID = 18L;

    private AntiAliasedImageLabel mouseWheelLabel;
    private AntiAliasedImageLabel wasdLabel;
    private AntiAliasedTextLabel wasdText;
    private AntiAliasedTextLabel mouseWheelText;
    
    public InfoBox() {
        this.setup();
        this.createComponent();
    }
    
    private void setup() {
        this.setLayout(null);
        this.setBounds(735, 570, 208, 125);
        this.setBorder(ComponentBorder.ALL_GOLD.get());
        this.setBackground(ComponentColor.WHITE.get());
    }

    private void createComponent() {
        this.createWasdIcon();
        this.createMauseWheelIcon();
        this.createWasdText();
        this.createMauseWheelText();
    }
    
    private AntiAliasedTextLabel createTextLabel(String text, int x, int y, int width, int height) {
        AntiAliasedTextLabel label = new AntiAliasedTextLabel(text);

        label.setBounds(x, y, width, height);
        label.setFont(QuattrocentoFont.PLAIN_27.get());

        return label;
    }

    private AntiAliasedImageLabel createImageLabel(String icon, int x, int y, int width, int height) {
        AntiAliasedImageLabel label = new AntiAliasedImageLabel();

        label.setBounds(x, y, width, height);
        label.setFont(QuattrocentoFont.PLAIN_27.get());
        label.setIcon(ImageLoader.asImageIcon(icon));

        return label;
    }

    private void createWasdIcon() {
        this.wasdLabel = this.createImageLabel(ImagePath.WASD_ICON.get(), 30, 15, 40,40);
        this.add(this.wasdLabel);
    }
    
    private void createMauseWheelIcon() {
        this.mouseWheelLabel = this.createImageLabel(ImagePath.MOUSE_ICON.get(), 34, 72,32,32);
        this.add(this.mouseWheelLabel);
    }
    
    private void createWasdText() {
        this.wasdText = this.createTextLabel("Naviga",95,16,100,40);
        this.add(wasdText);
    }
    
    private void createMauseWheelText() {
        this.mouseWheelText= this.createTextLabel("Zoom",100,70,100,40);
        this.add(mouseWheelText);
    }
}