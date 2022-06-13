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

/**
 * MainWindow extends JFrame in order to to create the main window where visualize the various components.
 * 
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 * 
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 29L;

    /**
     * The window dimwnsions.
     */ 
    private final static Dimension WINDOW_DIM = new Dimension(1280, 720);
    
    /**
     * The ScrollPaneController.
     */
    private ScrollPaneController scrollPaneController;

    /**
     * The ImageController.
     */
    private ImageController imageController;

    /**
     * The ImageHolder.
     */
    private ImageHolder imageHolder;

    /**
     * The ScrollPane.
     */
    private ScrollPane scrollPane;

    /**
     * The SearchBar.
     */
    private SearchBar searchbar;

    /**
     * The ButtonBox.
     */
    private ButtonBox buttonBox;

    /**
     * The InfoBox.
     */
    private InfoBox infoBox;

    /**
     * The JPanel used as background.
     */
    private JPanel background;
    
    /**
     * Creates the window, sets it up and creates the window components.
     */
    public MainWindow() {
        this.setup();
        this.createStaticComponents();
    }

    /**
     * Sets the window title as APP_TITLE, the size, the icon as APP_LOGO, the Resizable as false, the LocationRelativeTo as null, the DefaultCloseOperation as EXIT_ON_CLOSE and visile as true.
     */
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
    
    /**
     * Creates the static components of the window.
     */
    private void createStaticComponents() {
        this.createBackgroundPanel();
        this.createInfoBox();
        this.createButtonBox();
        this.createSearchBar();
        this.createScrollPane();
        this.createImageHolder();
    }
    
    /**
     * Creates the dinamic components of the window.
     */
    public void createDynamicComponents() {
        this.createImageController();
        this.createScrollPaneController();
        this.setActionListener();
    }
    
    /**
     * Initializes the background panel, sets the layout as null, the bounds and the bacground as PERSIAN_RED, then adds the panel to the window.
     */
    private void createBackgroundPanel() {
        this.background = new JPanel();
        this.background.setLayout(null);
        this.background.setBounds(0,0,1280,720);
        this.background.setBackground(ComponentColor.PERSIAN_RED.get());
        this.add(this.background);
    }
    /**
     * Initializes the searchbar and adds it to the window.
     */
    private void createSearchBar() {
        this.searchbar = new SearchBar();
        this.background.add(searchbar);
    }
    
    /**
     * Initializes the infobox and adds it to the window.
     */
    private void createInfoBox() {
        this.infoBox = new InfoBox();
        this.background.add(infoBox);
    }

    /**
     * Initializes the buttonBox and adds it to the window.
     */
    private void createButtonBox() {
        this.buttonBox = new ButtonBox();
        this.background.add(buttonBox);
    }
    
    /**
     * Initializes the imageHolder and adds it to the window.
     */
    private void createImageHolder() {
        this.imageHolder = new ImageHolder();
        this.background.add(imageHolder);
    }
    
    /**
     * Initializes the scrollPane and adds it to the window.
     */
    private void createScrollPane() {
        this.scrollPane = new ScrollPane();
        this.background.add(scrollPane);
    }
    
    /**
     * Initializes the imageController and adds it to the window.
     */
    private void createImageController() {
        this.imageController = new ImageController();
    }

    /**
     * Initializes the scrollPaneController and adds it to the window.
     */
    private void createScrollPaneController() {
        this.scrollPaneController = new ScrollPaneController();
    }

    /**
     * Stops the programs for an certain amount of time.
     */
    private void sleep(){   
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            //Do nothing
        }
    }

    /**
     * Adds the ImageHolderMouseWheelListener to the window and sets the keyBindingController.
     */
    private void setActionListener() {
        this.addMouseWheelListener(new ImageHolderMouseWheelListener());
        this.setKeyBindingController();
    }
    
    /**
     * Creates a KeyBindingController and adds the key binding functions by addKeyBindingController method.
     */
    private void setKeyBindingController() {
        KeyBindingController keyBinding = new KeyBindingController();
        keyBinding.addKeyBindingController();
    }
    
    
    /** 
     * @return The ScrollPaneController.
     */
    public ScrollPaneController getScrollPaneController() {
        return this.scrollPaneController;
    }

    
    /** 
     * @return The ImageController.
     */
    public ImageController getImageController() {
        return this.imageController;
    }

    
    /** 
     * @return The ImageHolder.
     */
    public ImageHolder getImageHolder() {
        return this.imageHolder;
    }

    
    /** 
     * @return The ScrollPane.
     */
    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    
    /** 
     * @return The ButtonBox.
     */
    public ButtonBox getButtonBox() {
        return this.buttonBox;
    }

    
    /** 
     * @return The InfoBox.
     */
    public InfoBox getInfoBox() {
        return this.infoBox;
    }

    
    /** 
     * @return The SearchBar.
     */
    public SearchBar getSearchBar() {
        return this.searchbar;
    }
}