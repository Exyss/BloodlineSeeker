package app.frontend.utils;

import java.awt.Color;

public enum ComponentColor {
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    WHITE(255, 255, 255),
    BLACK(0, 0, 0),
    GRAY(150, 150, 150),

    GOLD(212, 175, 55),
    CRAYOLA_YELLOW(241,202,125),
    PERSIAN_RED(126, 37, 38),
    LIME_GREEN(25,198, 4);

    private Color color;

    private ComponentColor(int red, int green, int blue) {
        this.color = new Color(red, green, blue);
    }
    
    public Color get() {
        return this.color;
    }
}