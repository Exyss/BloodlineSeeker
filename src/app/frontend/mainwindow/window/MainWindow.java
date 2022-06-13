package app.frontend.mainwindow.window;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import app.frontend.FrontendManager;
import app.frontend.mainwindow.components.buttonbox.ButtonBox;
import app.frontend.mainwindow.components.imageholder.ImageController;
import app.frontend.mainwindow.components.imageholder.ImageHolder;
import app.frontend.mainwindow.components.infobox.InfoBox;
import app.frontend.mainwindow.components.scrollpane.ScrollPane;
import app.frontend.mainwindow.components.scrollpane.ScrollPaneController;
import app.frontend.mainwindow.components.searchbar.SearchBar;
import app.frontend.mainwindow.listeners.imageholder.ImageHolderMouseWheelListener;
import app.frontend.mainwindow.listeners.keybindings.KeyBindingController;
import app.frontend.utils.ComponentColor;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 29L;

    private final static Dimension WINDOW_DIM = new Dimension(1280, 720);
    
    private ScrollPaneController scrollPaneController;
    private ImageController imageController;
    private ImageHolder imageHolder;
    private ScrollPane scrollPane;
    private SearchBar searchbar;
    private ButtonBox buttonBox;
    private InfoBox infoBox;
    private JPanel background;
    
    public MainWindow() {
        this.setup();
        this.createStaticComponents();
    }

    private void setup() {
        this.setTitle(FrontendManager.APP_TITLE);
        this.getContentPane().setPreferredSize(WINDOW_DIM);
        this.pack();
        this.setIconImage(FrontendManager.APP_LOGO);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.sleep();
        this.setVisible(true);
    }
    
    private void createStaticComponents() {
        this.createBackgroundPanel();
        this.createInfoBox();
        this.createButtonBox();
        this.createSearchBar();
        this.createScrollPane();
        this.createImageHolder();
    }
    
    public void createDynamicComponents() {
        this.createImageController();
        this.createScrollPaneController();
        this.setActionListener();
    }

    private void createBackgroundPanel() {
        this.background = new JPanel();
        this.background.setLayout(null);
        this.background.setBounds(0,0,1280,720);
        this.background.setBackground(ComponentColor.PERSIAN_RED.get());
        this.add(this.background);
    }

    private void createSearchBar() {
        this.searchbar = new SearchBar();
        this.background.add(searchbar);
    }
    
    private void createInfoBox() {
        this.infoBox = new InfoBox();
        this.background.add(infoBox);
    }

    private void createButtonBox() {
        this.buttonBox = new ButtonBox();
        this.background.add(buttonBox);
    }
    
    private void createImageHolder() {
        this.imageHolder = new ImageHolder();
        this.background.add(imageHolder);
    }
    
    private void createScrollPane() {
        this.scrollPane = new ScrollPane();
        this.background.add(scrollPane);
    }
    
    private void createImageController() {
        this.imageController = new ImageController();
    }

    private void createScrollPaneController() {
        this.scrollPaneController = new ScrollPaneController();
    }

    private void sleep(){   
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            //Do nothing
        }
    }

    private void setActionListener() {
        this.addMouseWheelListener(new ImageHolderMouseWheelListener());
        this.setKeyBindingController();
    }
    
    private void setKeyBindingController() {
        KeyBindingController keyBinding = new KeyBindingController();
        keyBinding.addKeyBindingController();
    }
    
    
    /** 
     * @return ScrollPaneController
     */
    public ScrollPaneController getScrollPaneController() {
        return this.scrollPaneController;
    }

    
    /** 
     * @return ImageController
     */
    public ImageController getImageController() {
        return this.imageController;
    }

    
    /** 
     * @return ImageHolder
     */
    public ImageHolder getImageHolder() {
        return this.imageHolder;
    }

    
    /** 
     * @return ScrollPane
     */
    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    
    /** 
     * @return ButtonBox
     */
    public ButtonBox getButtonBox() {
        return this.buttonBox;
    }

    
    /** 
     * @return InfoBox
     */
    public InfoBox getInfoBox() {
        return this.infoBox;
    }

    
    /** 
     * @return SearchBar
     */
    public SearchBar getSearchBar() {
        return this.searchbar;
    }
}