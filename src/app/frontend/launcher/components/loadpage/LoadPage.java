package app.frontend.launcher.components.loadpage;

import java.io.FileNotFoundException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONException;

import app.backend.BackendManager;
import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.launcher.components.StartButton;
import app.frontend.launcher.listeners.StartMainWindowActionListener;
import app.frontend.utils.ComponentColor;
import app.frontend.utils.QuattrocentoFont;

/**
 * This LoadPage extends JPanel in order to customize the load page.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class LoadPage extends JPanel {
	/**
	 * ID
	 */
    private static final long serialVersionUID = 14L;

    /**
     * The upper description message.
     */
    private final String UPPER_DESC_LABEL_MSG = "Carica le dinastie degli imperatori";

    /**
     * The lower description message.
     */
    private final String LOWER_DESC_LABEL_MSG = "romani salvate in locale";
    
    /**
     * The empty load message.
     */
    private final String EMPTY_LOAD_MSG =  "Non sono state trovate dinastie da caricare";

    /**
     * The success load message.
     */
    private final String SUCCESS_LOAD_MSG ="Dati dinastie caricati correttamente";

    /**
     * The fail load message.
     */
    private final String FAIL_LOAD_MSG = "Dati dinastie corrotti, effettuare il download";

    /**
     * If true the download is been completed yet, if false have to download the dynasty.
     */
    private boolean scrapingStatus = false;

    /**
     * Creates a download page and sets it up with a description label, a scraping result label and a start button.
     */
    public LoadPage() {
        this.setup();
        this.createDescriptionLabel();
        this.setScrapingStatusLabel();
        this.setStartButton();
    }

    /**
     * Sets up the settings of the load page.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(6,0,506,296);
        this.setOpaque(false);

    }
    
    /**
     * Sets the description messages in a label.
     */
    private void createDescriptionLabel() {
        AntiAliasedTextLabel upperDescriptionLabel = this.createLabel(UPPER_DESC_LABEL_MSG, 0, 25, 506, 40);
        this.add(upperDescriptionLabel);
        
        AntiAliasedTextLabel lowerDescriptionLabel = this.createLabel(LOWER_DESC_LABEL_MSG, 0, 55, 506, 40);
        this.add(lowerDescriptionLabel);
    }

    /**
     * Creates an AntiAliasedTextLabel label with text, coordinates and dimensions. Sets it the project font and the horizontal alignment to the center.
     * @param text the text of the label.
     * @param x the x-coordinate of the label.
     * @param y the y-coordinate of the label.
     * @param width the width of the label.
     * @param height the height of the label.
     * @return the label.
     */
    private AntiAliasedTextLabel createLabel(String text, int x, int y, int width, int height) {
        AntiAliasedTextLabel label = new AntiAliasedTextLabel(text);

        label.setBounds(x, y, width, height);
        label.setFont(QuattrocentoFont.PLAIN_26.get());
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    /**
     * Creates an AntiAliasedTextLabel named scrapingStatusLabel with the project font. If scapingStatus is true the label gets the success load message, 
     * if the method catches an error the label gets the fail load message, else the label gets the empty load message.
     */
    public void setScrapingStatusLabel() {
        AntiAliasedTextLabel scrapingStatusLabel = createLabel("", 0, 200, 506, 60);

        scrapingStatusLabel.setForeground(ComponentColor.RED.get());
        scrapingStatusLabel.setFont(QuattrocentoFont.PLAIN_24.get());

        String scrapingStatusLabelText;
        
        try {
            scrapingStatus = BackendManager.loadFromFiles();

            if (scrapingStatus) {
                scrapingStatusLabelText = SUCCESS_LOAD_MSG;

                scrapingStatusLabel.setBounds(0, 120, 512, 60);
                scrapingStatusLabel.setForeground(ComponentColor.LIME_GREEN.get());
            } else {
                scrapingStatusLabelText = EMPTY_LOAD_MSG;
            }

        } catch (FileNotFoundException | JSONException | NullPointerException e) {
            scrapingStatusLabelText = FAIL_LOAD_MSG;
        }

        scrapingStatusLabel.setText(scrapingStatusLabelText);

        this.add(scrapingStatusLabel);
    }
    
    /**
     * Sets the start button, adds the StartMainWindowActionListener to it and adds it to the load page, if scrapingStatus is true.
     */
    private void setStartButton() {
        if (scrapingStatus) {
            StartButton startButton = new StartButton();
            startButton.addActionListener(new StartMainWindowActionListener());
            this.add(startButton);
        }
    }
}