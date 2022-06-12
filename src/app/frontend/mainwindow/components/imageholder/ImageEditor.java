package app.frontend.mainwindow.components.imageholder;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import app.frontend.utils.Point;
import net.coobird.thumbnailator.Thumbnails;

/**
 * This ImageEditor edit the image 
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class ImageEditor {
    /**
     * The width of the label.
     */
    private final int LABEL_WIDTH = 508;

    /**
     * The height of the label.
     */
    private final int LABEL_HEIGHT = 508;

    /**
     * The value used to compute the shift amount.
     */
    private final int SHIFT_COMPUTE = 100;

    /**
     * The value used to compute the zoom amount.
     */
    private final int ZOOM_COPMUTE = 30;

    /**
     * The minimum size of a zoomed image.
     */
    private final int MIN_ZOOM_IMAGE_SIZE = 100;
    
    /**
     * The image to be used.
     */
    private BufferedImage image;
    
    /**
     * The width of the image.
     */
    private int imageWidth;

    /**
     * The height of the image.
     */
    private int imageHeight;
    
    /**
     * The minimum dimentions of the image.
     */
    private int imageMinDimension;
    
    /**
     * The upper_left x-coordinate of the image.
     */
    private int startX;

    /**
     * The upper_left y-coordinate of the image.
     */
    private int startY;
    
    /**
     * The current shift amount.
     */
    private int shift;

    /**
     * The new shift amount.
     */
    private int shiftValue;

    /**
     * The current zoom amount.
     */
    private int zoom;

    /**
     * The new zoom amount.
     */
    private int zoomValue;

    /**
     * The maximum zoom amount.
     */
    private int maxZoom;
    
    /** 
     * Sets the image dimensions from the image taken as a parameter, then inizialize startX, startY, imageMinDImwnsions, shiftValue, shift, zoomValue, zoom and maxZoom.
     * @param newImage The image to be taken as a reference
     */
    public void setImageToEdit(BufferedImage newImage) {
        this.image = adjustImage(newImage);
        this.setImageSize(this.image.getWidth(), this.image.getHeight());

        Point center = ImageController.findCenter(this.image);

        this.startX = center.getX() - LABEL_WIDTH / 2;
        this.startY = center.getY() - LABEL_HEIGHT / 2;

        this.imageMinDimension = this.getMinDimension();
        this.shiftValue = this.computeShift();
        this.shift = this.shiftValue;
        this.zoomValue = this.computeZoom();
        this.zoom = 0;
        this.maxZoom=imageMinDimension;
    }
    
    /** 
     * @return a subimage with the upperleft pixel that has the startX and startY coordinates and the LABEL_WIDTH and LABEL_HEIGHT dimensions.
     */
    public ImageIcon editImage() {
        return this.toImageIcon(image.getSubimage(startX, startY, LABEL_WIDTH, LABEL_HEIGHT));
    }
    
    /** 
     * @param zoomChange The zoom to apply.
     * @return a subimage with the upperleft pixel that has the startX and startY coordinates and the LABEL_WIDTH and LABEL_HEIGHT dimensions with zoom applied.
     */
    public ImageIcon editImage(int zoomChange) {
        this.zoomChange(zoomChange*zoomValue);
        
        if (zoom == 0) {
            return this.toImageIcon(image.getSubimage(startX, startY, LABEL_WIDTH, LABEL_HEIGHT));
        }else{
            BufferedImage temp = image.getSubimage(startX, startY, LABEL_WIDTH - zoom, LABEL_HEIGHT - zoom);
            return this.toImageIcon(resizeImage(temp, LABEL_WIDTH, LABEL_HEIGHT));
        }
    }
    
    /** 
     * @param xChange The shift amount of the x-coordinate.
     * @param yChange The shift amount of the y-coordinate.
     * @return a subimage with the upperleft pixel that has the startX and startY coordinates and the LABEL_WIDTH and LABEL_HEIGHT dimensions with zoom and shift applied.
     */
    public ImageIcon editImage(int xChange, int yChange) {
        if (xChange != 0) {
            changeX(xChange * shift);
        }
        if (yChange != 0) {
            changeY(yChange * shift);
        }
        
        if (zoom == 0) {
            return this.toImageIcon(image.getSubimage(startX, startY, LABEL_WIDTH, LABEL_HEIGHT));
        }else{
            BufferedImage temp = image.getSubimage(startX, startY, LABEL_WIDTH - zoom, LABEL_HEIGHT - zoom);
            return this.toImageIcon(resizeImage(temp, LABEL_WIDTH, LABEL_HEIGHT));
        }
    }
    
    /** 
     * Checks the image and makes sure that is no smaller than the label.
     * @param image the image to check.
     * @return the image if it is big enough, else a resizeImage.
     */
    private BufferedImage adjustImage(BufferedImage image) {
        double zoomValue = image.getWidth();
        double height = image.getHeight();

        if (zoomValue >= LABEL_WIDTH && height >= LABEL_HEIGHT) {
            return image;
        }
        
        if (zoomValue < height) {
            int newHeight = (int) (LABEL_HEIGHT * (height / zoomValue));
            return resizeImage(image, LABEL_WIDTH, newHeight);
        }
        else{
            int newWidth = (int) (LABEL_HEIGHT * (zoomValue / height));
            return resizeImage(image, newWidth, LABEL_HEIGHT);
        }  
    }
    
    /** 
     * Changes the x-coordinate and makes sure that it won't go below the zero or over the image width.
     * @param xChange the value to add/sub (it depends by the sign) at the current x-coordinate.
     */
    private void changeX(int xChange) {
        int x = startX + xChange;
        int realWidth = LABEL_WIDTH - zoom;

        if (x <= 0) {
            this.startX = 0;
        }else if (x + realWidth >= imageWidth) {
            this.startX = imageWidth - realWidth - 1;
        }else{
            this.startX = x;
        }
    }
    
    /** 
     * Changes the y-coordinate and makes sure that it won't go below the zero or over the image height.
     * @param yChange the value to add/sub (it depends by the sign) at the current y-coordinate.
     */
    private void changeY(int yChange) {
        int y = startY + yChange;
        int realHeight = LABEL_HEIGHT - zoom;

        if (y <= 0) {
            this.startY = 0;
        }else if (y + realHeight >= imageHeight) {
            this.startY = imageHeight - realHeight - 1;
        }else{
            this.startY = y;
        }
    }
    
    /** 
     * Changes the zoom and checks if it not makes the dimensions go below the MIN_ZOOM_IMAGE_SIZE or over the maxZoom.
     * @param zoomChange the new value to add/sub (it depends by the sign) at the current zoom.
     */
    private void zoomChange(int zoomChange) {
        int dim = LABEL_WIDTH - (this.zoom + zoomChange);

        if (dim > MIN_ZOOM_IMAGE_SIZE  && maxZoom > dim) {
            this.zoom += zoomChange;
            changeX(zoomChange/2);
            changeY(zoomChange/2);
        }
    }
    
    /** 
     * Modifies the dimensions of an image.
     * @param image the image to be resized.
     * @param width the new width.
     * @param height the new height.
     * @return BufferedImage
     */
    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        try {
            return Thumbnails.of(image).size(width, height).asBufferedImage();
        } catch (IOException e) {
            return null;
        }
    }
    
    /** 
     * Transforms an image to an icon.
     * @param image the image to transform.
     * @return the new ImageIcon.
     */
    private ImageIcon toImageIcon(BufferedImage image) {
        return new ImageIcon(image);
    }
    
    /** 
     * Sets the image dimensions.
     * @param width the new width.
     * @param height the new height.
     */
    private void setImageSize(int width, int height) {
        this.imageWidth = width;
        this.imageHeight = height;
    }
    
    /** 
     * Generate the shift by the division of the imageMinDimension and the SHIFT_COMPUTE.
     * @return the shift.
     */
    private int computeShift() {
        return (int) imageMinDimension / SHIFT_COMPUTE;    
    }
    
    /** 
     * Generate the zoom by the division of the imageMinDimension and the ZOOM_COMPUTE.
     * @return int
     */
    private int computeZoom() {
        return (int) imageMinDimension / ZOOM_COPMUTE;
    }
    
    /** 
     * @return The minimum between height and width.
     */
    private int getMinDimension() {
        return Integer.min(imageHeight, imageWidth);
    }
    
    /** 
     * @return The image.
     */
    public BufferedImage getImage() {
        return this.image;
    }
}