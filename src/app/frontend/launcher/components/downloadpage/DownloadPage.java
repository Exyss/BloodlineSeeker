package app.frontend.launcher.components.downloadpage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.frontend.elements.antialiased.text.AntiAliasedTextLabel;
import app.frontend.launcher.components.StartButton;
import app.frontend.launcher.listeners.SwitchToConsolePageActionListener;
import app.frontend.utils.QuattrocentoFont;

/**
 * This DownloadPage extends JPanel in order to customize the download page.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class DownloadPage extends JPanel {
    /**
     * ID
     */
    private static final long serialVersionUID = 13L;

    /**
     * The upper description message.
     */
    private static final String UPPER_DESC_LABEL_MSG = "Scarica le dinastie degli imperatori";
    /**
     * The lower description message.
     */
    private static final String LOWER_DESC_LABEL_MSG = "romani da Wikipedia";
    
    /**
     * The check box panel of the download page.
     */
    private CheckBoxPanel checkBoxPanel;
    
    /**
     * Creates a download page and sets it up with a description message, a start button and a check box panel.
     */
    public DownloadPage() {
        this.setup();
        this.setDescriptionLabel();
        this.setStartButton();
        this.createCheckBoxPanel();
    }

    /**
     * Sets up the settings of the download page.
     */
    private void setup() {
        this.setLayout(null);
        this.setBounds(6,0,506,296);
        this.setOpaque(false);
    }
    
    /**
     * Sets the description messages in a label.
     */
    private void setDescriptionLabel() {
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
     * Creates a check box panel and adds it in the downolad page.
     */
    private void createCheckBoxPanel() {
        this.checkBoxPanel= new CheckBoxPanel();
        this.add(checkBoxPanel);
    }

    /**
     * Creates a start button, adds the SwitchToConsolePageActionListener to it and adds it in the download page.
     */
    public void setStartButton() {
        StartButton startButton = new StartButton();

        startButton.addActionListener(new SwitchToConsolePageActionListener());

        this.add(startButton);
    }
    
    /**
     * @return the check box panel.
     */
    public CheckBoxPanel getCheckBoxPanel() {
        return checkBoxPanel;
    }
}