package app.frontend.mainwindow.components.imageholder;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import app.frontend.utils.Point;
import net.coobird.thumbnailator.Thumbnails;

public class ImageEditor {
    private final int LABEL_WIDTH = 508;
    private final int LABEL_HEIGHT = 508;
    private final int SHIFT_COMPUTE = 100;
    private final int ZOOM_COPMUTE = 30;
    private final int MIN_IMAGE_SIZE = 100;
    
    private BufferedImage image;
    
    private int imageWidth;
    private int imageHeight;
    
    private int imageMinDimension;
    
    private int startX;
    private int startY;
    
    private int shift;
    private int shiftValue;
    private int zoom;
    private int zoomValue;
    private int maxZoom;
    
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

    public ImageIcon editImage() {
        return this.toImageIcon(image.getSubimage(startX, startY, LABEL_WIDTH, LABEL_HEIGHT));
    }
    
    public ImageIcon editImage(int zoomChange) {
        this.zoomChange(zoomChange*zoomValue);
        
        if (zoom == 0) {
            return this.toImageIcon(image.getSubimage(startX, startY, LABEL_WIDTH, LABEL_HEIGHT));
        }else{
            BufferedImage temp = image.getSubimage(startX, startY, LABEL_WIDTH - zoom, LABEL_HEIGHT - zoom);
            return this.toImageIcon(resizeImage(temp, LABEL_WIDTH, LABEL_HEIGHT));
        }
    }
    
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
    
    private void zoomChange(int zoomChange) {
        int dim = LABEL_WIDTH - (this.zoom + zoomChange);

        if (dim > MIN_IMAGE_SIZE  && maxZoom > dim) {
            this.zoom += zoomChange;
            changeX(zoomChange/2);
            changeY(zoomChange/2);
        }
    }
    
    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        try {
            return Thumbnails.of(image).size(width, height).asBufferedImage();
        } catch (IOException e) {
            return null;
        }
    }
    
    private ImageIcon toImageIcon(BufferedImage image) {
        return new ImageIcon(image);
    }
    
    private void setImageSize(int width, int height) {
        this.imageWidth = width;
        this.imageHeight = height;
    }
    
    private int computeShift() {
        return (int) imageMinDimension / SHIFT_COMPUTE;    
    }
    
    private int computeZoom() {
        return (int) imageMinDimension / ZOOM_COPMUTE;
    }
    
    private int getMinDimension() {
        return Integer.min(imageHeight, imageWidth);
    }

    public BufferedImage getImage() {
        return this.image;
    }
}