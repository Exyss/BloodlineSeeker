package app.frontend.mainwindow.components.scrollpane;

import javax.swing.JButton;
import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextButton;
import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.mainwindow.listeners.search.SuggestionSearchBarActionListener;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.Margin;
import app.frontend.utils.QuattrocentoFont;

public final class SuggestionMessage extends JPanel {
    private static final long serialVersionUID = 22L;

    private final String defaultLabel = "Forse cercavi: ";

    private final String HTML_OPEN_TAG = "<html><a style='text-decoration:underline'>";
    private final String HTML_CLOSE_TAG = "</a></html>";

    private JPanel textPanel;
    private JPanel buttonPanel;

    public SuggestionMessage(String suggestion) {
        this.setup();
        this.createTextPanel();
        this.createButtonPanel(suggestion);
    }

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

    private void createButtonPanel(String suggestion) {
        AntiAliasedTextButton searchButton = createSearchButton(suggestion);

        this.buttonPanel = new JPanel();
        this.buttonPanel.add(searchButton);
        this.buttonPanel.setBounds(180, 16, searchButton.getPreferredSize().width, 35);
        this.buttonPanel.setOpaque(false);

        this.add(this.buttonPanel);
    }

    private AntiAliasedTextLabel createSuggestionLabel() {
        AntiAliasedTextLabel suggestionLabel = new AntiAliasedTextLabel(defaultLabel);

        suggestionLabel.setAlignmentY(CENTER_ALIGNMENT);
        suggestionLabel.setFont(QuattrocentoFont.PLAIN_25.get());
        suggestionLabel.setBounds(0, 0, 165, 30);
        
        return suggestionLabel;
    }

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