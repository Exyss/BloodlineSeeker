package app.frontend.mainwindow.components.scrollpane;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import app.backend.scraper.results.ScraperResult;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.frontend.mainwindow.MainWindowManager;
import app.frontend.utils.RomanLiteral;

/** 
 * This ScrollPaneController it's used to manage the ScrollPane operations.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public final class ScrollPaneController {
    
    /**
     * The width of the panel.
     */
    private final int PANEL_WIDTH = 650;
    
    /**
     * The panel height.
     */
    private final int PANEL_HEIGHT = 500;
    
    /**
     * The vertical gap between the cards.
     */
    private final int VERTICAL_CARD_GAP = 25;
    
    /**
     * The card height.
     */
    private final int CARD_HEIGHT = 107;

    /**
     * A JPanel used as card holder.
     */
    private JPanel cardHolder;
    
    /**
     * The ScrollPane.
     */
    private ScrollPane scrollPane;
    
    /**
     * Initializes the scrollPane getting the scrollPane from the MainWindowManager and the cardHolder getting it from the scrollPane just initialized.
     */
    public ScrollPaneController() {
        this.scrollPane = MainWindowManager.getWindowScrollPane(); 
        this.cardHolder = this.scrollPane.getCardHolder();
    }

    
    /** 
     * Creates a suggestionCard as SuggestionMessage with text, adds the card to the cardHolder, sets the size of the cardHolder and revalidate and repaint it
     * @param suggestion the text of the card.
     */
    private void createSuggestion(String suggestion) {
        SuggestionMessage suggestionCard = new SuggestionMessage(suggestion);
        this.cardHolder.add(suggestionCard);
        this.cardHolder.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.cardHolder.revalidate();
        this.cardHolder.repaint();
    }

    
    /** 
     * Creates a ScraperResultCard for each search result contained in the ArrayList data.
     * @param data the ArrayList which contains the search results.
     */
    private void createCards(ArrayList<ScraperResult> data) {
        int dataSize = data.size();

        // Generates the cards of every result, with the name of the person,
        // the dynasty and the corresponding roman literal
        for (int i = 0; i < dataSize; i++) {
            ScraperResultCard resultCard = createCard(i, data.get(i));

            this.cardHolder.add(resultCard);
        }

        // Makes the card holder big enough to hold every result card
        this.cardHolder.setPreferredSize(new Dimension(PANEL_WIDTH, computeY(dataSize)));

        this.cardHolder.revalidate();
        this.cardHolder.repaint();
    }

    
    /** 
     * Creates a ScraperResultCard.
     * @param index of the resultCard.
     * @param scraperResult the object which contains both member and dynasty informations.
     * @return the new ScraperResultCard.
     */
    private ScraperResultCard createCard(int index ,ScraperResult scraperResult) {
        Member member = scraperResult.getMember();
        Dynasty dynasty = scraperResult.getDynasty();
        ScraperResultCard resultCard = new ScraperResultCard(this.computeY(index), RomanLiteral.decimalToRoman(index + 1), dynasty, member);
        
        return resultCard;
    }

    /**
     * Remove all the components from the cardHolder.
     */
    private void removeAll() {
        this.cardHolder.removeAll();
    }

    
    /** 
     * Shows up in the ScrollPane the cards containing the search results.
     * @param data the ArrayList containing the search results.
     */
    public void showScrollPaneResults(ArrayList<ScraperResult> data) {
        this.removeAll();
        this.createCards(data);
        this.scrollPane.resetScrollBar();
    }
        
    
    /** 
     * Shows up in the ScrollPane the SuggestionCard.
     * @param suggestion the text containing the suggestion.
     */
    public void showScrollPaneSuggestion(String suggestion) {
        this.removeAll();
        this.createSuggestion(suggestion);
        this.scrollPane.resetScrollBar();
    }

    
    /** 
     * Compute the y-coordinate in order to have the same vertical space between the cards in the ScrollPane.
     * @param i the current index of the card.
     * @return int the new y-coordinate.
     */
    private int computeY(int i) {
        return ((i + 1) * VERTICAL_CARD_GAP) + (i * CARD_HEIGHT);
    }
    
    /**
     * Clears up the ScrollPane, creates a card as  ResultNotFoundMessage, adds it to the cardHolder, sets the card dimensions and rivalidates and repaints the cardHolder.
     */
    public void showResultNotFoundCard() {
        this.removeAll();

        ResultNotFoundMessage resultNotFoundCard = new ResultNotFoundMessage();

        this.cardHolder.add(resultNotFoundCard);
        this.cardHolder.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.cardHolder.revalidate();
        this.cardHolder.repaint();
    }
}