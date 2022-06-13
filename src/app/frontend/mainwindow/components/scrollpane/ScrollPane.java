package app.frontend.mainwindow.components.scrollpane;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;

/** 
 * This ScrollPane extends JPanel and it's used to display a scroll pane for the search results.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class ScrollPane extends JPanel{
    private static final long serialVersionUID = 21L;

    /**
     * The scroll increment.
     */
    private static final int SCROLL_INCREMENT = 15;
    
    /**
     * The scroll bar width.
     */
    private static final int SCROLLBAR_WIDTH = 15;
    
    /**
     * The angle degrees to make the corners rounded.
     */
    private static final int SCROLLBAR_ARC = 999;

    /**
     * The scroll bar origin point.
     */
    private static final Point ORIGIN_POINT = new Point(0,0);

    /**
     * A JScrollPane used as scroll pane.
     */
    private JScrollPane scrollPane;
    
    /**
     * A JPanel used as card holder.
     */
    private JPanel cardHolder;
    
    /**
     * Creates the ScrollPane and sets it up.
     */
    public ScrollPane() {
        this.setUI();
        this.setup();
        this.createCardHolder();
        this.createScrollPane();
    }
    
    /**
     * Sets up the ScrollPane layout and bounds.
     */
    private void setup() {
        this.setLayout(new BorderLayout());
        this.setBounds(25,130,685,565);
    }
    
    /**
     * Initializes the cardHolder with a null layout, a white background and sets the auto scrolls false.
     */
    private void createCardHolder() {
        this.cardHolder= new JPanel(null);
        this.cardHolder.setBackground(ComponentColor.WHITE.get());
        this.cardHolder.setAutoscrolls(true);
    }
    
    /**
     * Initializes the scrollPane displaying the cardHolder, sets ALL_GOLD the border color, sets focusable as true, sets SCROLL_INCREMENT as unit increment of the vertical scroll bar,
     * set the the vertical scroll bar policy so that vertical scroll bar is always displayed, set the the horizontal scroll bar policy so that horizontal scroll bar is never displayed and adds the scrollPane to the panel.
     */
    private void createScrollPane() {
        this.scrollPane=new JScrollPane(this.cardHolder);
        this.scrollPane.setBorder(ComponentBorder.ALL_GOLD.get());
        this.scrollPane.setFocusable(true);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(this.scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Improve visibility of the Panel rounding the corners and managing the colors components of the ScrollPane.
     */
    private void setUI() {
        UIManager.put("ScrollBar.width", SCROLLBAR_WIDTH);
        UIManager.put("ScrollBar.thumbArc", SCROLLBAR_ARC);
        
        UIManager.put("ScrollBar.thumb", ComponentColor.GRAY.get());
        UIManager.put("ScrollBar.hoverThumbColor", ComponentColor.GRAY.get());
        UIManager.put("ScrollBar.pressedThumbColor", ComponentColor.GRAY.get());
        
        UIManager.put("ScrollBar.track",ComponentColor.WHITE.get());
        UIManager.put("ScrollBar.hoverTrackColor",ComponentColor.WHITE.get());
    }

    
    /** 
     * @return the cardHolder.
     */
    public JPanel getCardHolder() {
        return this.cardHolder;
    }
    
    /**
     * Resets the ScrolBar.
     */
    public void resetScrollBar() {
        scrollPane.getViewport().setViewPosition(ORIGIN_POINT);
    }
}