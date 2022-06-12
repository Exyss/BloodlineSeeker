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

public class SearchBar extends JPanel {
    private static final long serialVersionUID = 23L;

    private final ImageIcon searchIcon = ImageLoader.asImageIcon(ImagePath.SEARCH_ICON.get());
    private final ImageIcon activeSearchIcon = ImageLoader.asImageIcon(ImagePath.PRESSED_SEARCH_ICON.get());
    
    private static String DEFAULT_TEXT = "Cerca una persona...";

    private SearchBarActionListener searchActionListener;
    private AntiAliasedTextField textField; 
    private AntiAliasedImageButton button;
    private JPanel panel;
    
    public SearchBar() {
        this.setup();
        this.createTextField();
        this.createPanel();
        this.createButton();
        this.addActionListener();
        this.setUi();
    }

    private void setup() {
        this.setLayout(null);
        this.setBounds(25, 25, 685, 80);
        this.setBackground(ComponentColor.WHITE.get());
    }

    private void createTextField() {
        this.textField = new AntiAliasedTextField();
        this.textField.setBounds(0,0,605,80);
        this.textField.setBorder(ComponentBorder.TRANSPARENT_LEFT_ALL_GOLD.get());
        this.textField.setBackground(ComponentColor.WHITE.get());
        this.textField.setFont(QuattrocentoFont.PLAIN_32.get());
        this.textField.addActionListener(this.searchActionListener);
        this.add(this.textField);
    }
    
    private void createPanel() {
        this.panel = new JPanel(null);
        this.panel.setBounds(605, 0, 80, 80);
        this.panel.setBorder(ComponentBorder.NO_LEFT_ALL_GOLD.get());
        this.panel.setBackground(ComponentColor.WHITE.get());
        this.add(this.panel);
    }
    
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
     * @return String
     */
    public String getText() {
        return this.textField.getText();
    }
    
    public void removeFocus() {
        this.textField.setFocusable(false);
        this.textField.setFocusable(true);
    }

    
    /** 
     * @param string
     */
    public void setText(String string) {
        textField.setText(string);
    }

    
    /** 
     * @return AntiAliasedTextField
     */
    public AntiAliasedTextField getTextField() {
        return textField;
    }

    private void addActionListener() {
        this.searchActionListener = new SearchBarActionListener();
        this.textField.addActionListener(this.searchActionListener);
        this.button.addActionListener(this.searchActionListener);
    }
    
    private void setUi() {
        textField.putClientProperty("JTextField.placeholderText", DEFAULT_TEXT);
    }    
}