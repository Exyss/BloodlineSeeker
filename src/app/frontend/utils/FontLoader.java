package app.frontend.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import app.backend.BackendManager;

/**
 * This FontLoader it's used to load the fonts.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class FontLoader {
    
    /** 
     * Loads the font.
     * @param fontFile the font file path.
     */
    public static void loadFont(String fontFile) {
        try {
            InputStream FONT_FILE = FontLoader.class.getResource(fontFile).openStream();
            Font font = Font.createFont(Font.TRUETYPE_FONT, FONT_FILE);
            
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(font);

        } catch (FontFormatException | IOException e) {
            BackendManager.printDebug("An error occurred while trying to load \"" + fontFile + "\"");
        }
    }
}