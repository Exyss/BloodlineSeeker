package app.frontend.mainwindow.components.scrollpane;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import app.backend.scraper.results.ScraperResult;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.frontend.mainwindow.MainWindowManager;
import app.frontend.utils.RomanLiteral;

public final class ScrollPaneController {
	
    private final int PANEL_WIDTH = 685;
    private final int PANEL_HEIGHT = 500;
    private final int VERTICAL_CARD_GAP = 25;
    private final int CARD_HEIGHT = 107;

    private JPanel cardHolder;
    private ScrollPane scrollPane;
    
    public ScrollPaneController() {
        this.scrollPane = MainWindowManager.getWindowScrollPane(); 
        this.cardHolder = this.scrollPane.getCardHolder();
    }

    
    /** 
     * @param suggestion
     */
    private void createSuggestion(String suggestion) {
        SuggestionMessage suggestionCard = new SuggestionMessage(suggestion);
        this.cardHolder.add(suggestionCard);
        this.cardHolder.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.cardHolder.revalidate();
        this.cardHolder.repaint();
    }

    
    /** 
     * @param data
     */
    private void createCards(ArrayList<ScraperResult> data) {
        int dataSize = data.size();

        for (int i = 0; i < dataSize; i++) {
            ScraperResultCard resultCard = createCard(i , data.get(i));
            this.cardHolder.add(resultCard);
        }

        this.cardHolder.setPreferredSize(new Dimension(PANEL_WIDTH, computeY(dataSize)));
        this.cardHolder.revalidate();
        this.cardHolder.repaint();
    }

    
    /** 
     * @param index
     * @param scraperResult
     * @return ScraperResultCard
     */
    private ScraperResultCard createCard(int index ,ScraperResult scraperResult) {
        Member member = scraperResult.getMember();
        Dynasty dynasty = scraperResult.getDynasty();
        ScraperResultCard resultCard = new ScraperResultCard(this.computeY(index), RomanLiteral.decimalToRoman(index + 1), dynasty, member);
        
        return resultCard;
    }

    private void removeAll() {
        this.cardHolder.removeAll();
    }

    
    /** 
     * @param data
     */
    public void showScrollPaneResults(ArrayList<ScraperResult> data) {
        this.removeAll();
        this.createCards(data);
        this.scrollPane.resetScrollBar();
    }
        
    
    /** 
     * @param suggestion
     */
    public void showScrollPaneSuggestion(String suggestion) {
        this.removeAll();
        this.createSuggestion(suggestion);
        this.scrollPane.resetScrollBar();
    }

    
    /** 
     * @param i
     * @return int
     */
    private int computeY(int i) {
        return ((i + 1) * VERTICAL_CARD_GAP) + (i * CARD_HEIGHT);
    }
    
    public void showResultNotFoundCard() {
        this.removeAll();

        ResultNotFoundMessage resultNotFoundCard = new ResultNotFoundMessage();

        this.cardHolder.add(resultNotFoundCard);
        this.cardHolder.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.cardHolder.revalidate();
        this.cardHolder.repaint();
    }
}