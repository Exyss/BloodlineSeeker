package app.frontend.mainwindow.components.imageholder;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

import app.frontend.elements.antialiased.image.AntiAliasedImageLabel;
import app.frontend.mainwindow.MainWindowManager;
import app.frontend.utils.ImageLoader;
import app.frontend.utils.ImagePath;
import app.frontend.utils.Point;

public class ImageController {
    private final static int VERTICAL_DISPLACEMENT = 30;
    private final static int HORIZONTAL_DISPLACEMENT = 10;
    private final static int RECTANGLE_BACKGROUND = -15571381;
    private final static int RECTANGLE_HEIGHT = 34;
    private final static int GRAPH_MARGIN = 245;
    private final static int UPPER_LEFT_X_SHIFT = 5;
    private final static int UPPER_LEFT_Y_SHIFT = 4;

    private final ImageEditor imageGenerator;
    private final ImageIcon defaultImage;

    private JTextField searchBarTextField;
    private AntiAliasedImageLabel label;

    private boolean canBrowse;
    
    public ImageController() {
        this.imageGenerator = new ImageEditor();
        this.defaultImage = ImageLoader.asImageIcon(ImagePath.DEFAULT_IMAGE.get());
        this.searchBarTextField =  MainWindowManager.getWindowSearchBar().getTextField();
        this.label = MainWindowManager.getWindowImageHolder().getLabel();
        this.setDefaultImage();
    }
    
    public void zoom(int zoom) {
        if (canBrowse()) {
            this.setImageToEdit(this.imageGenerator.editImage(zoom));
        }
    }
    
    public void move(int xChange , int yChange) {
        if (canBrowse()) {
            this.setImageToEdit(this.imageGenerator.editImage(xChange, yChange));
        }
    }
    
    private void setImageToEdit(ImageIcon image) {
        this.label.setIcon(image);
    }
    
    public void changeGraph(BufferedImage newImage) {
        this.imageGenerator.setImageToEdit(newImage);
        this.setImageToEdit(this.imageGenerator.editImage());
        this.canBrowse=true;
    }
    
    public void setDefaultImage() {
        this.label.setIcon(defaultImage);
        this.canBrowse=false;
    }
    
    private boolean canBrowse() {
        return canBrowse && (!searchBarTextField.hasFocus());
    }

    public BufferedImage getGraph() {
        return imageGenerator.getImage();
    }

    public boolean getBrowse() {
        return this.canBrowse;
    }

    public static Point findColoredRectangle(BufferedImage graphImage) {
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

    private static int findHorizontalRectBound(BufferedImage graphImage, int startX, int startY, int increment) {
        int x;
        int pixelColor = graphImage.getRGB(startX, startY);

        for (x = startX; pixelColor == RECTANGLE_BACKGROUND; x += increment) {
            pixelColor = graphImage.getRGB(x, startY);
        }

        return x - increment;
    }

    private static int findVerticalRectBound(BufferedImage graphImage, int startX, int startY, int increment) {
        int y;
        int pixelColor = graphImage.getRGB(startX, startY);

        for (y = startY; pixelColor == RECTANGLE_BACKGROUND; y += increment) {
            pixelColor = graphImage.getRGB(startX, y);
        }

        return y - increment;
    }

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
}