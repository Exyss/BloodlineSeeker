package app.frontend.utils;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * 
 */
public enum ComponentBorder {
    ALL_GOLD(ComponentColor.GOLD.get(),6),
    NO_TOP_ALL_GOLD(ComponentColor.GOLD.get(),0,6,6,6),
    NO_LEFT_ALL_GOLD(ComponentColor.GOLD.get(),6,0,6,6),
    NO_BOTTOM_ALL_GOLD(ComponentColor.GOLD.get(),6,6,0,6),
    NO_RIGHT_ALL_GOLD(ComponentColor.GOLD.get(),6,6,6,0),

    TRANSPARENT_TOP(10,0,0,0),
    TRANSPARENT_LEFT(0,10,0,0),
    TRANSPARENT_BOTTOM(0,0,10,0),
    TRANSPARENT_RIGHT(0,0,0,10),

    ONLY_BOTTOM_WHITE(ComponentColor.WHITE.get(),0,0,6,0),
    WHITE_BOTTOM_ALL_GOLD(NO_BOTTOM_ALL_GOLD.get(), ONLY_BOTTOM_WHITE.get()),
    TRANSPARENT_LEFT_ALL_GOLD(NO_RIGHT_ALL_GOLD.get(), TRANSPARENT_LEFT.get());

    private Border border;

    private ComponentBorder(Color color, int size) {
        this.border = BorderFactory.createLineBorder(color, size);
    }

    private ComponentBorder(Color color, int top, int left, int bottom, int right) {
        this.border = BorderFactory.createMatteBorder(top, left, bottom, right, color);
    }

    private ComponentBorder(int top, int left, int bottom, int right) {
        this.border = BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    private ComponentBorder(Border outsideBorder, Border insideBorder) {
        this.border = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
    }

    public Border get() {
        return this.border;
    }
}