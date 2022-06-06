package app.frontend.utils;

import java.awt.Font;

public enum QuattrocentoFont {
    PLAIN_15("Quattrocento", Font.PLAIN, 15),
    PLAIN_16("Quattrocento", Font.PLAIN, 16),
    PLAIN_17("Quattrocento", Font.PLAIN, 17),
    PLAIN_18("Quattrocento", Font.PLAIN, 18),
    PLAIN_19("Quattrocento", Font.PLAIN, 19),
    PLAIN_20("Quattrocento", Font.PLAIN, 20),
    PLAIN_21("Quattrocento", Font.PLAIN, 21),
    PLAIN_22("Quattrocento", Font.PLAIN, 22),
    PLAIN_23("Quattrocento", Font.PLAIN, 23),
    PLAIN_24("Quattrocento", Font.PLAIN, 24),
    PLAIN_25("Quattrocento", Font.PLAIN, 24),
    PLAIN_26("Quattrocento", Font.PLAIN, 26),
    PLAIN_27("Quattrocento", Font.PLAIN, 27),
    PLAIN_28("Quattrocento", Font.PLAIN, 28),
    PLAIN_29("Quattrocento", Font.PLAIN, 29),
    PLAIN_30("Quattrocento", Font.PLAIN, 30),
    PLAIN_31("Quattrocento", Font.PLAIN, 31),
    PLAIN_32("Quattrocento", Font.PLAIN, 32),

    BOLD_15("Quattrocento", Font.BOLD, 15),
    BOLD_16("Quattrocento", Font.BOLD, 16),
    BOLD_17("Quattrocento", Font.BOLD, 17),
    BOLD_18("Quattrocento", Font.BOLD, 18),
    BOLD_19("Quattrocento", Font.BOLD, 19),
    BOLD_20("Quattrocento", Font.BOLD, 20),
    BOLD_21("Quattrocento", Font.BOLD, 21),
    BOLD_22("Quattrocento", Font.BOLD, 22),
    BOLD_23("Quattrocento", Font.BOLD, 23),
    BOLD_24("Quattrocento", Font.BOLD, 24),
    BOLD_25("Quattrocento", Font.BOLD, 24),
    BOLD_26("Quattrocento", Font.BOLD, 26),
    BOLD_27("Quattrocento", Font.BOLD, 27),
    BOLD_28("Quattrocento", Font.BOLD, 28),
    BOLD_29("Quattrocento", Font.BOLD, 29),
    BOLD_30("Quattrocento", Font.BOLD, 30),
    BOLD_31("Quattrocento", Font.BOLD, 31),
    BOLD_32("Quattrocento", Font.BOLD, 32);
    
    private Font font;

    private QuattrocentoFont(String fontName, int fontStyle, int fontSize) {
        this.font = new Font(fontName, fontStyle, fontSize);
    }

    public Font get() {
        return this.font;
    }
}