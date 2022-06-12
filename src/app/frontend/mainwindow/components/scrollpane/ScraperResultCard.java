package app.frontend.mainwindow.components.scrollpane;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.frontend.elements.antialiased.image.AntiAliasedImageButton;
import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.mainwindow.listeners.resultcard.GenerateGraphActionListener;
import app.frontend.mainwindow.listeners.resultcard.OpenMemberLinkActionListener;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.Margin;
import app.frontend.utils.QuattrocentoFont;

public class ScraperResultCard extends JPanel {
    private static final long serialVersionUID = 20L;

    private AntiAliasedTextLabel indexLabel;
    private AntiAliasedTextLabel nameLabel;
    private AntiAliasedTextLabel dynastyLabel;
    private AntiAliasedImageButton wikipediaButton;
    private AntiAliasedImageButton graphButton;

    private Dynasty dynasty;
    private Member member;
    private String romanIndex;
    
    public ScraperResultCard(int y, String romanIndex, Dynasty dynasty, Member member) {
        this.member = member;
        this.dynasty = dynasty;
        this.romanIndex = romanIndex;
        this.setUI();
        this.setup(y);
        this.createComponents();
    }
    
    
    /** 
     * @param y
     */
    private void setup(int y) {
        this.setLayout(null);
        this.setBounds(25,y,608,107);
        this.setPreferredSize(new Dimension(608,107));
        this.setBackground(ComponentColor.CRAYOLA_YELLOW.get());
    }

    private void createComponents() {
        this.createIndexLabel(this.romanIndex);
        this.createNameLabel(this.member.getName());
        this.createDynastyLabel(this.dynasty.getName());
        this.createWikipediaButton();
        this.createGraphButton();
    }
    
    
    /** 
     * @param icon
     * @param pressedIcon
     * @param x
     * @param y
     * @param width
     * @param height
     * @return AntiAliasedImageButton
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
     * @param text
     * @param font
     * @param x
     * @param y
     * @param width
     * @param height
     * @return AntiAliasedTextLabel
     */
    private AntiAliasedTextLabel createLabel(String text,Font font, int x, int y, int width, int height) {
        AntiAliasedTextLabel label = new AntiAliasedTextLabel(text);

        label.setBounds(x, y, width, height);
        label.setFont(font);

        return label;
    }

    
    /** 
     * @param romanIndex
     */
    private void createIndexLabel(String romanIndex) {
        this.indexLabel = this.createLabel(romanIndex, QuattrocentoFont.PLAIN_16.get(), 20, 10, 110, 15);
        this.add(indexLabel);
    }
    
    
    /** 
     * @param memberName
     */
    private void createNameLabel(String memberName) {
        if (member.isEmperor()) {
            this.nameLabel = createLabel(memberName, QuattrocentoFont.BOLD_30.get(), 20, 30, 500, 40);
            this.nameLabel.setForeground(ComponentColor.PERSIAN_RED.get());
        } else {
            this.nameLabel = createLabel(memberName, QuattrocentoFont.PLAIN_30.get(), 20, 30, 500, 40);
        }

        this.add(nameLabel);
    }
    
    
    /** 
     * @param dynastyName
     */
    private void createDynastyLabel(String dynastyName) {
        this.dynastyLabel = this.createLabel(dynastyName, QuattrocentoFont.PLAIN_20.get(), 20, 72, 500, 30);
        this.add(dynastyLabel);
    }
    
    private void createWikipediaButton() {
        this.wikipediaButton = this.createImageButton(ImagePath.SMALL_WIKIPEDIA_ICON.get(), ImagePath.PRESSED_SMALL_WIKIPEDIA_ICON.get(), 550,12,36,36);
        this.wikipediaButton.addActionListener( new OpenMemberLinkActionListener(this.member.getWikipediaLink()));
        this.add(this.wikipediaButton);
    }
    
    private void createGraphButton() {
        this.graphButton = this.createImageButton(ImagePath.ARROW_ICON.get(), ImagePath.PRESSED_ARROW_ICON.get(), 553,65,30,30);
        this.graphButton.addActionListener(new GenerateGraphActionListener(this.dynasty, this.member)); 
        this.add(this.graphButton);
    }
    
    private void setUI() {
        this.putClientProperty(FlatClientProperties.STYLE, "arc: 40");
    }
}