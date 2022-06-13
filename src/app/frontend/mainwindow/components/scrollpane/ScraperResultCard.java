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

/**
 * This ScraperResultCard extends JPanel in order to show the search results.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class ScraperResultCard extends JPanel {
    private static final long serialVersionUID = 20L;
    
    /**
     * An AntiAliasedTextLabel used as index for the search results.
     */
    private AntiAliasedTextLabel indexLabel;
    
    /**
     * An AntiAliasedTextLabel used to show the names of the search results.
     */
    private AntiAliasedTextLabel nameLabel;
    
    /**
     * An AntiAliasedTextLabel used to show the names of the Dynasties of the search results.
     */
    private AntiAliasedTextLabel dynastyLabel;
    
    /**
     * An AntiAliasedImageButton used to generate the link to Wikipedia.
     */
    private AntiAliasedImageButton wikipediaButton;
    
    /**
     * An AntiAliasedImageButton used to generate the graph image for the download.
     */
    private AntiAliasedImageButton graphButton;

    /**
     * A Dynasty object used as reference to the search result dynasties.
     */
    private Dynasty dynasty;
    
    /**
     * A Member object used as reference to the search result members.
     */
    private Member member;
    
    /**
     * A String used as Index for the indexLabel.
     */
    private String romanIndex;
    
    /**
     * Creates the ScraperResultCard specifying the y-coordinate, the index showed in the label, the Dynasty and the Member of the search results.
     * @param y the y-coordinate of the panel.
     * @param romanIndex the index showed in the panel.
     * @param dynasty the Dynasty of the search result.
     * @param member the Member of the search result.
     */
    public ScraperResultCard(int y, String romanIndex, Dynasty dynasty, Member member) {
        this.member = member;
        this.dynasty = dynasty;
        this.romanIndex = romanIndex;
        this.setUI();
        this.setup(y);
        this.createComponents();
    }
    
    
    /** 
     * Sets up the panel layout, bounds (specifying the y-coordinate), the size and the background.
     * @param y the y-coordinate of the panel.
     */
    private void setup(int y) {
        this.setLayout(null);
        this.setBounds(25,y,608,107);
        this.setPreferredSize(new Dimension(608,107));
        this.setBackground(ComponentColor.CRAYOLA_YELLOW.get());
    }
    
    /**
     * Initializes the indexLabel, the nameLabel, the dynastyLabel, the wikipediaLabel and creates the graph button using them creates methods.
     */
    private void createComponents() {
        this.createIndexLabel(this.romanIndex);
        this.createNameLabel(this.member.getName());
        this.createDynastyLabel(this.dynasty.getName());
        this.createWikipediaButton();
        this.createGraphButton();
    }
    
    
    /** 
     * Creates an AntiAliasedImageButton with icon, coordinates and dimensions. Sets the borderPainted as false, the border as null, the margin as empty and the contentAreaFilled as false.
     * @param icon the icon of the button.
     * @param pressedIcon the representation of the image when the button is pressed.
     * @param x the x-coordinate of the button.
     * @param y the y-coordinate of the button.
     * @param width the width of the button.
     * @param height the height of the button.
     * @return the new AntiAliasedImageButton.
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
     * Creates an AntiAliasedTextLabel with text, font, coordinates and dimensions.
     * @param text the text of the label.
     * @param font the font of the text label.
     * @param x the x-coordinate of the label.
     * @param y the y-coordinate of the label.
     * @param width the width of the label.
     * @param height the height of the label.
     * @return the new AntiAliasedTextLabel.
     */
    private AntiAliasedTextLabel createLabel(String text,Font font, int x, int y, int width, int height) {
        AntiAliasedTextLabel label = new AntiAliasedTextLabel(text);

        label.setBounds(x, y, width, height);
        label.setFont(font);

        return label;
    }

    
    /** 
     * Initializes the indexLabel specifying the index of the search result. Adds the label to the ScraperReultCard.
     * @param romanIndex the index of the label.
     */
    private void createIndexLabel(String romanIndex) {
        this.indexLabel = this.createLabel(romanIndex, QuattrocentoFont.PLAIN_16.get(), 20, 10, 110, 15);
        this.add(indexLabel);
    }
    
    
    /** 
     * Initializes the nameLabel using as text of the label the name of the member. If the member of the search result is an emperor it's label foreground color will be PERSIAN_RED, else it will have the default foreground color. Adds the label to the panel.
     * @param memberName the name of the member.
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
     * Initializes the dynastyLabel using as text of the label the name of the dynasty. Adds the label to the panel.
     * @param dynastyName the name of the dynasty.
     */
    private void createDynastyLabel(String dynastyName) {
        this.dynastyLabel = this.createLabel(dynastyName, QuattrocentoFont.PLAIN_20.get(), 20, 72, 500, 30);
        this.add(dynastyLabel);
    }
    
    /**
     * Initializes the wikipwdiaButton and adds to it an OpenMemberLinkActionListener. Adds the button to the panel.
     */
    private void createWikipediaButton() {
        this.wikipediaButton = this.createImageButton(ImagePath.SMALL_WIKIPEDIA_ICON.get(), ImagePath.PRESSED_SMALL_WIKIPEDIA_ICON.get(), 550,12,36,36);
        this.wikipediaButton.addActionListener( new OpenMemberLinkActionListener(this.member.getWikipediaLink()));
        this.add(this.wikipediaButton);
    }
    
    /**
     * Initializes the graphButton and adds to it an GenerateGraphActionListener. Adds the button button to the panel.
     */
    private void createGraphButton() {
        this.graphButton = this.createImageButton(ImagePath.ARROW_ICON.get(), ImagePath.PRESSED_ARROW_ICON.get(), 553,65,30,30);
        this.graphButton.addActionListener(new GenerateGraphActionListener(this.dynasty, this.member)); 
        this.add(this.graphButton);
    }
    
    /**
     * Improve visibility of the Panel rounding the corners.
     */
    private void setUI() {
        this.putClientProperty(FlatClientProperties.STYLE, "arc: 40");
    }
}