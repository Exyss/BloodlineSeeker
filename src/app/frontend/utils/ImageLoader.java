package app.frontend.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import app.backend.BackendManager;

public final class ImageLoader {
    
    /** 
     * @param path
     * @return ImageIcon
     */
    public static ImageIcon asImageIcon(String path) {
        BufferedImage bimg = asBufferedImage(path);
        ImageIcon icon = new ImageIcon(bimg);

        return icon;
    }

    
    /** 
     * @param path
     * @return Image
     */
    public static Image asImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(getImageURL(path));
    }

    
    /** 
     * @param path
     * @return URL
     */
    public static URL getImageURL(String path) {
        return ImageLoader.class.getResource(path);
    }

    
    /** 
     * @param path
     * @return BufferedImage
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