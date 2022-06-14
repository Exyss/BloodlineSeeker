package app.frontend.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import app.backend.BackendManager;

/**
 * This ImageLoader it's used to load the images.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class ImageLoader {
    
    /** 
     * Converts an image path to an ImageIcon.
     * @param path The image path.
     * @return the ImageIcon.
     */
    public static ImageIcon asImageIcon(String path) {
        BufferedImage bimg = asBufferedImage(path);
        ImageIcon icon = new ImageIcon(bimg);

        return icon;
    }

    
    /** 
     * Converts an image path to an Image.
     * @param path The image path.
     * @return the Image. 
     */
    public static Image asImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(getImageURL(path));
    }

    
    /** 
     * Transforms an image path to an URL.
     * @param path The path to transform.
     * @return the URL.
     */
    public static URL getImageURL(String path) {
        return ImageLoader.class.getResource(path);
    }

    
    /** 
     * Converts an image path to a BufferedImage.
     * @param path The path to converts.
     * @return the BufferedImage.
     */
    public static BufferedImage asBufferedImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to load \"" + path + "\"");
        }

        return null;
    }
}