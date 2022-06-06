package app.frontend.launcher.components;

import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.QuattrocentoFont;

/**
 * This CreditPanel extends JPanel to customize the credit panel.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class CreditPanel extends JPanel {
	/**
	 * ID
	 */
    private static final long serialVersionUID = 8L;

    /**
     * This label will contain the name of one of the authors of the program.
     */
    private AntiAliasedTextLabel alessioBandieraLabel;

    /**
     * This label will contain the name of one of the authors of the program.
     */
    private AntiAliasedTextLabel matteoBenvenutiLabel;

    /**
     * This label will contain the name of one of the authors of the program.
     */
    private AntiAliasedTextLabel simoneBiancoLabel;

    /**
     * This label will contain the name of one of the authors of the program.
     */
    private AntiAliasedTextLabel andreaLadoganaLabel;

    /**
     * Creates a CreditPanel.
     */
    public CreditPanel() {
        this.setup();
        this.createNames();
    }

    /**
     * Sets up the setting of the CreditPanel.
     */
    private void setup() {
        this.setBounds(282,13,260,54);
        this.setLayout(null);
        this.setOpaque(false);
    }

    /**
     * Creates the name of the authors and puts them in in their respective labels.
     */
    private void createNames() {
        matteoBenvenutiLabel = this.createNameLabel("Matteo Benvenuti", 8, 14, 121, 15);
        this.add(matteoBenvenutiLabel);

        alessioBandieraLabel = this.createNameLabel("Alessio Bandiera", 13, 35, 112, 17);
        this.add(alessioBandieraLabel);

        andreaLadoganaLabel = this.createNameLabel("Andrea Ladogana", 137, 12, 119, 18);
        this.add(andreaLadoganaLabel);

        simoneBiancoLabel = this.createNameLabel("Simone Bianco", 145, 36, 106, 14);
        this.add(simoneBiancoLabel);
    }

    /**
     * Creates an AntiAliasedTextLabel named nameLabel with text, coordinates, dimensions with the project font and a white foreground.
     * @param name the text in the label.
     * @param x the x-coordinate of the label.
     * @param y the y-coordinate of the label.
     * @param width the width of the label.
     * @param height the height of the label.
     * @return the nameLabel.
     */
    private AntiAliasedTextLabel createNameLabel(String name, int x, int y, int width, int height) {
        AntiAliasedTextLabel nameLabel = new AntiAliasedTextLabel(name);

        nameLabel.setFont(QuattrocentoFont.PLAIN_15.get());
        nameLabel.setBounds(x, y, width, height);
        nameLabel.setForeground(ComponentColor.WHITE.get());

        return nameLabel;
    }
}