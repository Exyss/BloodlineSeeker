package app.frontend.mainwindow.components.scrollpane;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;

public class ScrollPane extends JPanel{
    private static final long serialVersionUID = 21L;

    private static final int SCROLL_INCREMENT = 15;
    private static final int SCROLLBAR_WIDTH = 15;
    private static final int SCROLLBAR_ARC = 999;

    private JScrollPane scrollPane;
    private JPanel cardHolder;
    
    public ScrollPane() {
        this.setUi();
        this.setup();
        this.createCardHolder();
        this.createScrollPane();
    }
    
    private void setup() {
        this.setLayout(new BorderLayout());
        this.setBounds(25,130,685,565);
    }
    
    private void createCardHolder() {
        this.cardHolder= new JPanel(null);
        this.cardHolder.setBackground(ComponentColor.WHITE.get());
        this.cardHolder.setAutoscrolls(true);
    }
    
    private void createScrollPane() {
        this.scrollPane=new JScrollPane(this.cardHolder);
        this.scrollPane.setBorder(ComponentBorder.ALL_GOLD.get());
        this.scrollPane.setFocusable(true);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(this.scrollPane, BorderLayout.CENTER);
    }
    
    private void setUi() {
        UIManager.put("ScrollBar.width", SCROLLBAR_WIDTH);
        UIManager.put("ScrollBar.thumbArc", SCROLLBAR_ARC);
        
        UIManager.put("ScrollBar.thumb", ComponentColor.GRAY.get());
        UIManager.put("ScrollBar.hoverThumbColor", ComponentColor.GRAY.get());
        UIManager.put("ScrollBar.pressedThumbColor", ComponentColor.GRAY.get());
        
        UIManager.put("ScrollBar.track",ComponentColor.WHITE.get());
        UIManager.put("ScrollBar.hoverTrackColor",ComponentColor.WHITE.get());
    }

    public JPanel getCardHolder() {
        return this.cardHolder;
    }
    
    public void resetScrollBar() {
        scrollPane.getViewport().setViewPosition(new Point(0, 0));
    }
}