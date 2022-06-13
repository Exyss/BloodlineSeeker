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
     * Sets up the Suggestion Layout as null, the
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(0, 0, 600, 100);
        this.setOpaque(false);
    }

    private void createTextPanel() {
        this.textPanel = new JPanel(null);
        this.textPanel.setBounds(20, 20, 165, 30);
        this.textPanel.setOpaque(false);

        this.textPanel.add(createSuggestionLabel());

        this.add(this.textPanel);
    }

    
    /** 
     * @param suggestion
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
     * @return AntiAliasedTextLabel
     */
    private AntiAliasedTextLabel createSuggestionLabel() {
        AntiAliasedTextLabel suggestionLabel = new AntiAliasedTextLabel(defaultLabel);

        suggestionLabel.setAlignmentY(CENTER_ALIGNMENT);
        suggestionLabel.setFont(QuattrocentoFont.PLAIN_25.get());
        suggestionLabel.setBounds(0, 0, 165, 30);
        
        return suggestionLabel;
    }

    
    /** 
     * @param suggestion
     * @return AntiAliasedTextButton
     */
    private AntiAliasedTextButton createSearchButton(String suggestion) {
        AntiAliasedTextButton searchButton = new AntiAliasedTextButton();

        searchButton.setText(HTML_OPEN_TAG + suggestion + HTML_CLOSE_TAG);
        searchButton.setForeground(ComponentColor.PERSIAN_RED.get());
        searchButton.setFont(QuattrocentoFont.PLAIN_25.get());
        searchButton.setHorizontalAlignment(JButton.LEFT);
        searchButton.setBorderPainted(false);
        searchButton.setBorder(null);
        searchButton.setMargin(Margin.EMPTY.get());
        searchButton.setContentAreaFilled(false);
        searchButton.addActionListener(new SuggestionSearchBarActionListener(suggestion));

        return searchButton;
    }
}