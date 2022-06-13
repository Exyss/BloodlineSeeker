package app.frontend.mainwindow.components.scrollpane;

import javax.swing.JButton;
import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextButton;
import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.mainwindow.listeners.search.SuggestionSearchBarActionListener;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.Margin;
import app.frontend.utils.QuattrocentoFont;

/** 
 * This SuggestionMessage extends JPanel in order to give a suggestion to the user if necessary.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public final class SuggestionMessage extends JPanel {
    private static final long serialVersionUID = 22L;
    
    /**
     * A string used as defaultLabel.
     */
    private final String defaultLabel = "Forse cercavi: ";
    
    /**
     * The open tag for each suggestion.
     */
    private final String HTML_OPEN_TAG = "<html><a style='text-decoration:underline'>";
    
    /**
     * The close tag for each suggestion.
     */
    private final String HTML_CLOSE_TAG = "</a></html>";
    
    /**
     * A JPanel used as textPanel.
     */
    private JPanel textPanel;
    
    /**
     * A JPanel used as buttonPanel.
     */
    private JPanel buttonPanel;

    /**
     * Creates SuggestionMessage panel with text, sets it up, initializes the textPanel using the createTextPanel method and the buttonPanel using the createButtonPanel method.
     * @param suggestion the text of the panel.
     */
    public SuggestionMessage(String suggestion) {
        this.setup();
        this.createTextPanel();
        this.createButtonPanel(suggestion);
    }

    /**
     * Sets the Suggestion Layout as null, the bounds of the card and sets the opaque option as false.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(0, 0, 600, 100);
        this.setOpaque(false);
    }
    
    /**
     * Initializes the textPanel, then sets its bounds and the opaque option as false. Adds thesuggestionLabelto the textPanel and the textPanel to the SuggestionMessage panel.
     */
    private void createTextPanel() {
        this.textPanel = new JPanel(null);
        this.textPanel.setBounds(20, 20, 165, 30);
        this.textPanel.setOpaque(false);

        this.textPanel.add(createSuggestionLabel());

        this.add(this.textPanel);
    }

    
    /** 
     * Creates an AntiAliasedTextButton with text, Initializes the buttonPanel, adds the button to the buttonPanel then sets the bounds and the opaque option as false. Adds the buttonPanel to the SuggestionMessage panel.
     * @param suggestion the text of the searchButton.
     */
    private void createButtonPanel(String suggestion) {
        AntiAliasedTextButton searchButton = createSearchButton(suggestion);

        this.buttonPanel = new JPanel();
        this.buttonPanel.add(searchButton);
        this.buttonPanel.setBounds(180, 16, searchButton.getPreferredSize().width, 35);
        this.buttonPanel.setOpaque(false);

        this.add(this.buttonPanel);
    }

    
    /** 
     * Creates an AntiAliasedTextLabel with the defaultLabel as text, aligns the y-coordinates of the component by the center. Then sets the default font and the bounds. 
     * @return the new AntiAliasedTextLabel.
     */
    private AntiAliasedTextLabel createSuggestionLabel() {
        AntiAliasedTextLabel suggestionLabel = new AntiAliasedTextLabel(defaultLabel);

        suggestionLabel.setAlignmentY(CENTER_ALIGNMENT);
        suggestionLabel.setFont(QuattrocentoFont.PLAIN_24.get());
        suggestionLabel.setBounds(0, 0, 165, 30);
        
        return suggestionLabel;
    }

    
    /** 
     * Creates an AntiAliasedTextButton used as search button, set the text of the button as the union of the open tag, suggestion and close tag,
     * sets the foreground color as PERSIAN_RED, sets the project default font, set the Horizontal alignment to the left, set the BorderPainted as false,
     * sets the border as null, the margin as empty, the ContentAreaFilled as false and adds to the button an SuggestionSearchBarActionListener.
     * @param suggestion the String which is part of the searchButton.
     * @return the new AntiAliasedTextButton search button.
     */
    private AntiAliasedTextButton createSearchButton(String suggestion) {
        AntiAliasedTextButton searchButton = new AntiAliasedTextButton();

        searchButton.setText(HTML_OPEN_TAG + suggestion + HTML_CLOSE_TAG);
        searchButton.setForeground(ComponentColor.PERSIAN_RED.get());
        searchButton.setFont(QuattrocentoFont.PLAIN_24.get());
        searchButton.setHorizontalAlignment(JButton.LEFT);
        searchButton.setBorderPainted(false);
        searchButton.setBorder(null);
        searchButton.setMargin(Margin.EMPTY.get());
        searchButton.setContentAreaFilled(false);
        searchButton.addActionListener(new SuggestionSearchBarActionListener(suggestion));

        return searchButton;
    }
}