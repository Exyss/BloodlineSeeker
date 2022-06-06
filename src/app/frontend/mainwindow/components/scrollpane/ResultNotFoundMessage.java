package app.frontend.mainwindow.components.scrollpane;

import java.awt.Dimension;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.QuattrocentoFont;

public final class ResultNotFoundMessage extends JPanel {
    private static final long serialVersionUID = 19L;

    private final String DEFAULT_LABEL = "Nessun risultato trovato";

    private AntiAliasedTextLabel suggestionLabel;

    public ResultNotFoundMessage() {
        this.setup();
        this.createNotFoundLabel();
    }

    private void setup() {
        this.setLayout(null);
        this.setBounds(0,0,600,50);
        this.setPreferredSize(new Dimension(60,30));
        this.setBackground(ComponentColor.WHITE.get());
    }

    private void createNotFoundLabel() {
        this.suggestionLabel = new AntiAliasedTextLabel(DEFAULT_LABEL);
        this.suggestionLabel.setAlignmentY(CENTER_ALIGNMENT);
        this.suggestionLabel.setFont(QuattrocentoFont.PLAIN_25.get());
        this.suggestionLabel.setBounds(20, 20, 500, 30);
        this.add(suggestionLabel);
    }
}