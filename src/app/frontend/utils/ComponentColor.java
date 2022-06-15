package app.frontend.utils;

import java.awt.Color;

/**
 * The colors that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum ComponentColor {
    /**
     * The RED color.
     */
    RED(255, 0, 0),
    
    /**
     * The GREEN color.
     */
    GREEN(0, 255, 0),
    
    /**
     * The BLUE color.
     */
    BLUE(0, 0, 255),
    
    /**
     * The WHITE color.
     */
    WHITE(255, 255, 255),
    
    /**
     * The BLACK color.
     */
    BLACK(0, 0, 0),
    
    /**
     * The GREY color.
     */
    GRAY(150, 150, 150),

    /**
     * The GOLD color.
     */
    GOLD(212, 175, 55),
    
    /**
     * The CRAYOLA_YELLOW color.
     */
    CRAYOLA_YELLOW(241,202,125),
    
    /**
     * The PERSIAN_RED color.
     */
    PERSIAN_RED(126, 37, 38),
    
    /**
     * The LIME_GREEN color.
     */
    LIME_GREEN(25,198, 4);

    /**
     * The color.
     */
    private Color color;

    /**
     * Initializes the color using the Color(RGB) constructor.
     * @param red The value of red in the RGB color.
     * @param green The value of green in the RGB color.
     * @param blue The value of blue in the RGB color.
     */
    private ComponentColor(int red, int green, int blue) {
        this.color = new Color(red, green, blue);
    }
    
    /**
     * 
     * @return the color.
     */
    public Color get() {
        return this.color;
    }
}