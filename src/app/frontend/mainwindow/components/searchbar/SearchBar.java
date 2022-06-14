package app.frontend.mainwindow.components.searchbar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import app.frontend.elements.antialiased.image.AntiAliasedImageButton;
import app.frontend.elements.antialiased.text.AntiAliasedTextField;
import app.frontend.mainwindow.listeners.search.SearchBarActionListener;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.Margin;
import app.frontend.utils.QuattrocentoFont;

/** 
 * This SearchBar extends JPanel in order to create a search bar component.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class SearchBar extends JPanel {
    private static final long serialVersionUID = 23L;
    
    /**
     * The default search icon.
     */
    private final ImageIcon searchIcon = ImageLoader.asImageIcon(ImagePath.SEARCH_ICON.get());
    
    /**
     * The default search icon when pressed.
     */
    private final ImageIcon activeSearchIcon = ImageLoader.asImageIcon(ImagePath.PRESSED_SEARCH_ICON.get());
    
    /**
     * The default text used when the search bar is initialized.
     */
    private static String DEFAULT_TEXT = "Cerca una persona...";

    /**
     * An SearchBarActionListener.
     */
    private SearchBarActionListener searchActionListener;
    
    /**
     * An AntiAliasedTextField.
     */
    private AntiAliasedTextField textField; 
    
    /**
     * An AntiAliasedImageButton.
     */
    private AntiAliasedImageButton button;
    
    /**
     * A JPanel.
     */
    private JPanel panel;
    
    
    /**
     * Creates a searchBar, sets it up and calls the createTextField, the createPanel, the createButton, the addActionListener and the setUI methods.
     */
    public SearchBar() {
        this.setup();
        this.createTextField();
        this.createPanel();
        this.createButton();
        this.addActionListener();
        this.setUI();
    }

    /**
     * Sets the SearchBar layout as null, the bounds and a white background.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(25, 25, 685, 80);
        this.setBackground(ComponentColor.WHITE.get());
    }

    /**
     * Initialize the textField, sets its bounds, border as TRANSPARENT_LEFT_ALL_GOLD, background as WHITE and font as project default, add the searchActionListener to the text field
     * and adds it to the SearchBar.
     */
    private void createTextField() {
        this.textField = new AntiAliasedTextField();
        this.textField.setBounds(0,0,605,80);
        this.textField.setBorder(ComponentBorder.TRANSPARENT_LEFT_ALL_GOLD.get());
        this.textField.setBackground(ComponentColor.WHITE.get());
        this.textField.setFont(QuattrocentoFont.PLAIN_32.get());
        this.textField.addActionListener(this.searchActionListener);
        this.add(this.textField);
    }
    
    /**
     * Initializes the JPanel, sets the bounds, the border as NO_LEFT_ALL_GOLD and the background as WHITE, then adds the panel to the SearchBar.
     */
    private void createPanel() {
        this.panel = new JPanel(null);
        this.panel.setBounds(605, 0, 80, 80);
        this.panel.setBorder(ComponentBorder.NO_LEFT_ALL_GOLD.get());
        this.panel.setBackground(ComponentColor.WHITE.get());
        this.add(this.panel);
    }
    
    /**
     * Initializes the AntiAliasedImageButton, sets the bounds, the BorderPainted as False, the border as null, the margin as empty, the contentAreaFilled as false,
     * the icon as searchIcon and the pressedIcon as activeSearchIcon, then adds the button to the panel.
     */
    private void createButton() {
        this.button = new AntiAliasedImageButton();
        this.button.setBounds(5,17,45,45);
        this.button.setBorderPainted(false);
        this.button.setBorder(null);
        this.button.setMargin(Margin.EMPTY.get());
        this.button.setContentAreaFilled(false);
        this.button.setIcon(this.searchIcon);
        this.button.setPressedIcon(activeSearchIcon);
        this.panel.add(this.button);
    }

    
    /** 
     * @return the String text.
     */
    public String getText() {
        return this.textField.getText();
    }
    
    /**
     * Remove temporary the focus from the SearchBar to allow the navigation on the graph image by using the WASD buttons.
     */
    public void removeFocus() {
        this.textField.setFocusable(false);
        this.textField.setFocusable(true);
    }

    
    /** 
     * Sets the value of the text.
     * @param string the new value of the text.
     */
    public void setText(String string) {
        textField.setText(string);
    }

    
    /** 
     * @return return the AntiAliasedTextField textField.
     */
    public AntiAliasedTextField getTextField() {
        return textField;
    }

    /**
     * Initializes the searchActionListener and adds it to the textField and the button.
     */
    private void addActionListener() {
        this.searchActionListener = new SearchBarActionListener();
        this.textField.addActionListener(this.searchActionListener);
        this.button.addActionListener(this.searchActionListener);
    }
    
    /**
     * Sets the placeholer text in the searchbar.
     */
    private void setUI() {
        textField.putClientProperty("JTextField.placeholderText", DEFAULT_TEXT);
    }    
}