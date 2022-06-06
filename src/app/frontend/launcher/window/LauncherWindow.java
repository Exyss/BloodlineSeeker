package app.frontend.launcher.window;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import app.frontend.FrontendManager;
import app.frontend.launcher.components.CreditPanel;
import app.frontend.launcher.components.PageButtonPanel;
import app.frontend.launcher.components.consolepage.ConsolePage;
import app.frontend.launcher.components.downloadpage.DownloadPage;
import app.frontend.launcher.components.loadpage.LoadPage;
import app.frontend.utils.ComponentBorder;
import app.frontend.utils.ComponentColor;

/**
 * This LauncherWindow extends JFrame to customize the launcher window.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class LauncherWindow extends JFrame {
	/**
	 * ID
	 */
    private static final long serialVersionUID = 15L;

    /**
     * The dimensions of the launcher window
     */
    private final static Dimension WINDOW_DIM = new Dimension(568, 405);

    /**
     * The background panel.
     */
    private JPanel background;

    /**
     * The content panel.
     */
    private JPanel contentPanel;

    /**
     * The page button panel.
     */
    private PageButtonPanel pageButtonPanel;

    /**
     * The load page panel.
     */
    private LoadPage loadPage;

    /**
     * The download page panel.
     */
    private DownloadPage downloadPage;

    /**
     * The console page panel.
     */
    private ConsolePage consolePanel;
    
    /**
     * Creates the Launcher window adding the components and setting them up.
     */
    public LauncherWindow() {
        this.addComponent();
        this.setup();
    }
    
    /**
     * Adds every windows components.
     */
    private void addComponent() {
        this.createBackground();
        this.createContentPanel();
        this.cratePages();
        this.cratePageButtonPanel();
        this.createCreditPanel();
    }
    
    /**
     * Sets up every option of the window.
     */
    private void setup() {
        this.getContentPane().setPreferredSize(WINDOW_DIM);
        this.pack();
        this.setTitle(FrontendManager.APP_TITLE);
        this.setIconImage(FrontendManager.APP_LOGO);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    /**
     * Creates a new load page and adds it in the content panel.
     */
    private void cratePages() {
        this.loadPage= new LoadPage();
        this.contentPanel.add(loadPage);
        this.downloadPage = new DownloadPage();
    }
    
    /**
     * Creates a new page button panel and adds it in the background panel.
     */
    private void cratePageButtonPanel() {
        this.pageButtonPanel = new PageButtonPanel();
        this.background.add(pageButtonPanel);
    }
    
    /**
     * Creates a new panel with specifics border, color , coordinates, width and height.
     * @param border the border of the panel.
     * @param color the color of the panel.
     * @param x the x-coordinate of the panel.
     * @param y the y-coordinate of the panel.
     * @param width the width of the panel.
     * @param height the height of the panel.
     * @return the new JPanel object
     */
    private JPanel createPanel(Border border, Color color, int x, int y, int width, int height) {
        JPanel panel = new JPanel();

        panel.setLayout(null);
        panel.setBackground(color);
        panel.setBounds(x, y, width, height);
        panel.setBorder(border);

        return panel;
    }
    
    /**
     * Creates a new content panel and adds it in the background panel.
     */
    private void createContentPanel() {
        this.contentPanel = createPanel(ComponentBorder.NO_TOP_ALL_GOLD.get(), ComponentColor.WHITE.get(), 25, 78, 518, 302);
        this.background.add(contentPanel);
    }
    
    /**
     * Creates a new credit panel and adds it in the background panel and adds it in the background panel.
     */
    private void createCreditPanel() {
        CreditPanel creditPanel = new CreditPanel();
        this.background.add(creditPanel);
    }
    
    /**
     * Creates the background panel and adds it in the window.
     */
    private void createBackground() {
        this.background= createPanel(null, ComponentColor.PERSIAN_RED.get(), 0, 0, 568, 405);
        this.add(background);

        JPanel smallTopBorder = createPanel(null, ComponentColor.GOLD.get(), 123, 72, 9, 6);
        this.background.add(smallTopBorder);

        JPanel longTopBorder = createPanel(null, ComponentColor.GOLD.get(), 276, 72, 267, 6);
        this.background.add(longTopBorder);
    }
    
    /**
     * Clears the background panel.
     */
    public void clear() {
        this.background.removeAll();
        this.background.revalidate();
        this.background.repaint();
    }
    
    /**
     * Create a console panel and adds it in the background.
     */
    public void showConsole() {
        this.clear();
        this.consolePanel = new ConsolePage();
        this.background.add(consolePanel);
    }
    
    /**
     * @return content panel.
     */
    public JPanel getContentPanel() {
        return this.contentPanel;
    }
    
    /**
     * @return the load page.
     */
    public LoadPage getLoadPage() {
        return this.loadPage;
    }
    
    /**
     * @return the download page.
     */
    public DownloadPage getDownloadPage() {
        return this.downloadPage;
    }
    
    /**
     * @return the console page.
     */
    public ConsolePage getConsolePage() {
        return this.consolePanel;
    }
    
    /**
     * @return the page button panel.
     */
    public PageButtonPanel getPageButtonPanel() {
        return this.pageButtonPanel;
    }
    
    /**
     * @return the window title.
     */
    public String getWindowTitle() {
        return this.getTitle();
    }
    
    /**
     * Sets the window title.
     * @param newWindowTitle the new window title.
     */
    public void setWindowTitle(String newWindowTitle) {
        this.setTitle(newWindowTitle);
    }
}