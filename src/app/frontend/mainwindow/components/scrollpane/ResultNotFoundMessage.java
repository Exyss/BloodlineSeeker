package app.frontend.mainwindow.components.scrollpane;

import java.awt.Dimension;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.QuattrocentoFont;

/**
 * This ResultNotFoundMessage extends JPanel in order to show a dedicated panel for the not found searches.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class ResultNotFoundMessage extends JPanel {
    private static final long serialVersionUID = 19L;
    
    /**
     * The default message shown in the panel.
     */
    private final String DEFAULT_LABEL = "Nessun risultato trovato";
    
    /**
     * An AntiAliasedTextLabel used for the suggestion.
     */
    private AntiAliasedTextLabel suggestionLabel;
    
    /**
     * Creates the ResultNotFoundMessage, sets it up and creates a label using the createNotFoundLabel method.
     */
    public ResultNotFoundMessage() {
        this.setup();
        this.createNotFoundLabel();
    }
    /**
     * Sets up the layout, the bounds the size and the background of the panel.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(0,0,600,50);
        this.setPreferredSize(new Dimension(60,30));
        this.setBackground(ComponentColor.WHITE.get());
    }
    
    /**
     * Creates an AntiAliasedTextLabel aligned by the center and sets it the project font and the bounds. Adds it to the panel.
     */
    private void createNotFoundLabel() {
        this.suggestionLabel = new AntiAliasedTextLabel(DEFAULT_LABEL);
        this.suggestionLabel.setAlignmentY(CENTER_ALIGNMENT);
        this.suggestionLabel.setFont(QuattrocentoFont.PLAIN_25.get());
        this.suggestionLabel.setBounds(20, 20, 500, 30);
        this.add(suggestionLabel);
    }
}