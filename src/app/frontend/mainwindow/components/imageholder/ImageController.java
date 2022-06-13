package app.frontend.mainwindow.components.imageholder;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

import app.frontend.elements.antialiased.image.AntiAliasedImageLabel;
import app.frontend.mainwindow.MainWindowManager;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.Point;

/**
 * This ImageController manage the operation on the image.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class ImageController {
    /**
     * The vertical displacement used to find the center node of the graph.
     */
    private final static int VERTICAL_DISPLACEMENT = 30;
    /**
     * The horizontal displacement used to find the center node of the graph.
     */
    private final static int HORIZONTAL_DISPLACEMENT = 10;
    /**
     * The rectangle (node) backgrund color.
     */
    private final static int RECTANGLE_BACKGROUND = -15571381;
    /**
     * The height of the rectangle (node).
     */
    private final static int RECTANGLE_HEIGHT = 34;
    /**
     * The graph margin.
     */
    private final static int GRAPH_MARGIN = 245;
    /**
     * The x shift.
     */
    private final static int UPPER_LEFT_X_SHIFT = 5;
    /**
     * The y shift.
     */
    private final static int UPPER_LEFT_Y_SHIFT = 4;

    /**
     * The image editor.
     */
    private final ImageEditor imageEditor;

    /**
     * The image icon.
     */
    private final ImageIcon defaultImage;

    /**
     * The JTextField used for the search bar.
     */
    private JTextField searchBarTextField;

    /**
     * An AntiAliasedImageLabel.
     */
    private AntiAliasedImageLabel label;

    /**
     * Specifies if the image can be browsed.
     */
    private boolean canBrowse;
    
    /**
     * Creates the ImageController and sets the imageEditor, the defaultImage, the searchBarTextField and the label.
     */
    public ImageController() {
        this.imageEditor = new ImageEditor();
        this.defaultImage = ImageLoader.asImageIcon(ImagePath.DEFAULT_IMAGE.get());
        this.searchBarTextField =  MainWindowManager.getWindowSearchBar().getTextField();
        this.label = MainWindowManager.getWindowImageHolder().getLabel();
        this.setDefaultImage();
    }
    
    /**
     * Calls the imageEditor in order to create the zoomed image.
     * @param zoom the amount of the zoom.
     */
    public void zoom(int zoom) {
        if (canBrowse()) {
            this.setImageToEdit(this.imageEditor.editImage(zoom));
        }
    }

    /**
     * Calls the imageEditor in order move the image.
     * @param xChange the shift amount of the x coordinate.
     * @param yChange the shift amount of the y coordinate.
     */
    public void move(int xChange , int yChange) {
        if (canBrowse()) {
            this.setImageToEdit(this.imageEditor.editImage(xChange, yChange));
        }
    }
    
    /**
     * Sets the icon of the label.
     */
    private void setImageToEdit(ImageIcon image) {
        this.label.setIcon(image);
    }

    /**
     * Changes the graph to visualize.
     */
    public void changeGraph(BufferedImage newImage) {
        this.imageEditor.setImageToEdit(newImage);
        this.setImageToEdit(this.imageEditor.editImage());
        this.canBrowse=true;
    }

    /**
     * Sets the label to the default image.
     */
    public void setDefaultImage() {
        this.label.setIcon(defaultImage);
        this.canBrowse=false;
    }
    
    /**
     @return True if canBrowse is true and if the searchBarTextField has not the focus. False in any other case.
     */
    private boolean canBrowse() {
        return canBrowse && (!searchBarTextField.hasFocus());
    }

    /**Finds the node which has the same background color of RECTANGLE_BACKGROUND.
     * 
     * @param graphImage the image where to find the node.
     * @return a point (the coordinates of the node) if find the node, null if the node doesen't exists.
    */
    private static Point findColoredRectangle(BufferedImage graphImage) {
        int graphWidth = graphImage.getWidth() - GRAPH_MARGIN;
        int graphHeight = graphImage.getHeight() - GRAPH_MARGIN;

        Point rectanglePixel = null;

        for (int y = GRAPH_MARGIN; y < graphHeight; y += VERTICAL_DISPLACEMENT) {
            for (int x = GRAPH_MARGIN; x < graphWidth; x += HORIZONTAL_DISPLACEMENT) {
                int pixelColorValue = graphImage.getRGB(x, y);
                
                if (pixelColorValue == RECTANGLE_BACKGROUND) {
                    rectanglePixel = new Point(x, y);

                    break;
                }
            }

            if (rectanglePixel != null) {
                break;
            }
        }
        
        return rectanglePixel;
    }

    /**
    * Slides to the right or the left (it depends by the value of the increment) the pixels of the image from the starting coordinates until it finds a pixel with a different color.
    * @param graphImage the image where to slide.
    * @param startX the x-coordinate of the starting pixel.
    * @param startY the y-coordinate of the starting pixel.
    * @return the x-coordinate of the left/right (it depends by the value of the increment) bound.
     */
    private static int findHorizontalRectBound(BufferedImage graphImage, int startX, int startY, int increment) {
        int x;
        int pixelColor = graphImage.getRGB(startX, startY);

        for (x = startX; pixelColor == RECTANGLE_BACKGROUND; x += increment) {
            pixelColor = graphImage.getRGB(x, startY);
        }

        return x - increment;
    }

    /** 
     *Slides to the top the pixels of the image from the starting coordinates until it finds a pixel with a different color.
    * @param graphImage the image where to slide.
    * @param startX the x-coordinate of the starting pixel.
    * @param startY the y-coordinate of the starting pixel.
    * @return the xycoordinate of the top bound.
     */
    private static int findVerticalRectBound(BufferedImage graphImage, int startX, int startY, int increment) {
        int y;
        int pixelColor = graphImage.getRGB(startX, startY);

        for (y = startY; pixelColor == RECTANGLE_BACKGROUND; y += increment) {
            pixelColor = graphImage.getRGB(startX, y);
        }

        return y - increment;
    }
    
    /** 
     * Finds the center of the image using findHorizontalRectBound and findVerticalRectBound.
     * @param graphImage the image in which to find the center.
     * @return The center pixel.
     */
    public static Point findCenter(BufferedImage graphImage) {
        Point rectanglePixel = findColoredRectangle(graphImage);

        int graphImageWidth = graphImage.getWidth();
        int graphImageHeight = graphImage.getHeight();

        Point meanPixel = new Point(graphImageWidth / 2, graphImageHeight / 2);

        if (rectanglePixel == null) {
            return meanPixel; // should never happen
        }

        int startX = rectanglePixel.getX();
        int startY = rectanglePixel.getY();

        int upperLeftX = findHorizontalRectBound(graphImage, startX, startY, -1);
        int upperLeftY = findVerticalRectBound(graphImage, startX, startY, -1);
        int upperRightX = findHorizontalRectBound(graphImage, upperLeftX + UPPER_LEFT_X_SHIFT, upperLeftY + UPPER_LEFT_Y_SHIFT, +1);

        int rectangleWidth = upperRightX - upperLeftX + UPPER_LEFT_X_SHIFT;

        meanPixel = new Point(upperLeftX + (rectangleWidth / 2), upperLeftY + (RECTANGLE_HEIGHT / 2));
        return meanPixel;
    }

    /**
     *@return the Image of the imageEditor.
     */
    public BufferedImage getGraph() {
        return imageEditor.getImage();
    }


    /** @return the canBrowse value.*/
    public boolean getBrowse() {
        return this.canBrowse;
    }
}