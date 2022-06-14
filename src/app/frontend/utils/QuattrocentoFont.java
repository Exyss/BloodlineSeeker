package app.frontend.utils;

import java.awt.Font;

/**
 * The QuattrocentoFont that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum QuattrocentoFont {
	
	/**
	 * The QuattroCento PLAIN_15
	 */
    PLAIN_15("Quattrocento", Font.PLAIN, 15),
    
    /**
	 * The QuattroCento PLAIN_16
	 */
    PLAIN_16("Quattrocento", Font.PLAIN, 16),
    
    /**
	 * The QuattroCento PLAIN_17
	 */
    PLAIN_17("Quattrocento", Font.PLAIN, 17),
    
    /**
	 * The QuattroCento PLAIN_18
	 */
    PLAIN_18("Quattrocento", Font.PLAIN, 18),
    
    /**
	 * The QuattroCento PLAIN_19
	 */
    PLAIN_19("Quattrocento", Font.PLAIN, 19),
    
    /**
	 * The QuattroCento PLAIN_20
	 */
    PLAIN_20("Quattrocento", Font.PLAIN, 20),
    
    /**
	 * The QuattroCento PLAIN_21
	 */
    PLAIN_21("Quattrocento", Font.PLAIN, 21),
    
    /**
	 * The QuattroCento PLAIN_22
	 */
    PLAIN_22("Quattrocento", Font.PLAIN, 22),
    
    /**
	 * The QuattroCento PLAIN_23
	 */
    PLAIN_23("Quattrocento", Font.PLAIN, 23),
    
    /**
	 * The QuattroCento PLAIN_24
	 */
    PLAIN_24("Quattrocento", Font.PLAIN, 24),
    
    /**
	 * The QuattroCento PLAIN_25
	 */
    PLAIN_25("Quattrocento", Font.PLAIN, 25),
    
    /**
	 * The QuattroCento PLAIN_26
	 */
    PLAIN_26("Quattrocento", Font.PLAIN, 26),
    
    /**
	 * The QuattroCento PLAIN_27
	 */
    PLAIN_27("Quattrocento", Font.PLAIN, 27),
    
    /**
	 * The QuattroCento PLAIN_28
	 */
    PLAIN_28("Quattrocento", Font.PLAIN, 28),
    
    /**
	 * The QuattroCento PLAIN_29
	 */
    PLAIN_29("Quattrocento", Font.PLAIN, 29),
    
    /**
	 * The QuattroCento PLAIN_30
	 */
    PLAIN_30("Quattrocento", Font.PLAIN, 30),
    
    /**
	 * The QuattroCento PLAIN_31
	 */
    PLAIN_31("Quattrocento", Font.PLAIN, 31),
    
    /**
	 * The QuattroCento PLAIN_32
	 */
    PLAIN_32("Quattrocento", Font.PLAIN, 32),

    /**
	 * The QuattroCento BOLD_15
	 */
    BOLD_15("Quattrocento", Font.BOLD, 15),
    
    /**
	 * The QuattroCento BOLD_16
	 */
    BOLD_16("Quattrocento", Font.BOLD, 16),
    
    /**
	 * The QuattroCento BOLD_17
	 */
    BOLD_17("Quattrocento", Font.BOLD, 17),
    
    /**
	 * The QuattroCento BOLD_18
	 */
    BOLD_18("Quattrocento", Font.BOLD, 18),
    
    /**
	 * The QuattroCento BOLD_19
	 */
    BOLD_19("Quattrocento", Font.BOLD, 19),
    
    /**
	 * The QuattroCento BOLD_20
	 */
    BOLD_20("Quattrocento", Font.BOLD, 20),
    
    /**
	 * The QuattroCento BOLD_21
	 */
    BOLD_21("Quattrocento", Font.BOLD, 21),
    
    /**
	 * The QuattroCento BOLD_22
	 */
    BOLD_22("Quattrocento", Font.BOLD, 22),
    
    /**
	 * The QuattroCento BOLD_23
	 */
    BOLD_23("Quattrocento", Font.BOLD, 23),
    
    /**
	 * The QuattroCento BOLD_24
	 */
    BOLD_24("Quattrocento", Font.BOLD, 24),
    
    /**
	 * The QuattroCento BOLD_25
	 */
    BOLD_25("Quattrocento", Font.BOLD, 25),
    
    /**
	 * The QuattroCento BOLD_26
	 */
    BOLD_26("Quattrocento", Font.BOLD, 26),
    
    /**
	 * The QuattroCento BOLD_27
	 */
    BOLD_27("Quattrocento", Font.BOLD, 27),
    
    /**
	 * The QuattroCento BOLD_28
	 */
    BOLD_28("Quattrocento", Font.BOLD, 28),
    
    /**
	 * The QuattroCento BOLD_29
	 */
    BOLD_29("Quattrocento", Font.BOLD, 29),
    
    /**
	 * The QuattroCento BOLD_30
	 */
    BOLD_30("Quattrocento", Font.BOLD, 30),
    
    /**
	 * The QuattroCento BOLD_31
	 */
    BOLD_31("Quattrocento", Font.BOLD, 31),
    
    /**
	 * The QuattroCento BOLD_32
	 */
    BOLD_32("Quattrocento", Font.BOLD, 32);
    
	/**
	 * The font.
	 */
    private Font font;

    /**
     * Initializes the font using the Font constructor with name, style and size.
     * @param fontName the name of the font.
     * @param fontStyle the style of the font.
     * @param fontSize the size of the font.
     */
    private QuattrocentoFont(String fontName, int fontStyle, int fontSize) {
        this.font = new Font(fontName, fontStyle, fontSize);
    }

    /**
     * 
     * @return the font.
     */
    public Font get() {
        return this.font;
    }
}