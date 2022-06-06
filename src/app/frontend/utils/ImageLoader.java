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
    public static ImageIcon asImageIcon(String path) {
        BufferedImage bimg = asBufferedImage(path);
        ImageIcon icon = new ImageIcon(bimg);

        return icon;
    }

    public static Image asImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(getImageURL(path));
    }

    public static URL getImageURL(String path) {
        return ImageLoader.class.getResource(path);
    }

    public static BufferedImage asBufferedImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            BackendManager.printDebug("An error occurred while trying to load \"" + path + "\"");
        }

        return null;
    }
}