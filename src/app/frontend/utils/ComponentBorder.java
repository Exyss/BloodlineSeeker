package app.frontend.utils;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * The borders that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum ComponentBorder {
    
    /**
     * The ALL_GOLD border.
     */
    ALL_GOLD(ComponentColor.GOLD.get(),6),
    
    /**
     * The NO_TOP_ALL_GOLD border.
     */
    NO_TOP_ALL_GOLD(ComponentColor.GOLD.get(),0,6,6,6),
    
    /**
     * The NO_LEFT_ALL_GOLD border.
     */
    NO_LEFT_ALL_GOLD(ComponentColor.GOLD.get(),6,0,6,6),
    
    /**
     * The NO_BOTTOM_ALL_GOLD border.
     */
    NO_BOTTOM_ALL_GOLD(ComponentColor.GOLD.get(),6,6,0,6),
    
    /**
     * The NO_RIGHT_ALL_GOLD border.
     */
    NO_RIGHT_ALL_GOLD(ComponentColor.GOLD.get(),6,6,6,0),

    /**
     * The TRANSPARENT_TOP border.
     */
    TRANSPARENT_TOP(10,0,0,0),
    
    /**
     * The TRANSPARENT_LEFT border.
     */
    TRANSPARENT_LEFT(0,10,0,0),
    
    /**
     * The TRANSPARENT_BOTTOM border.
     */
    TRANSPARENT_BOTTOM(0,0,10,0),
    
    /**
     * The TRANSPARENT_RIGHT border.
     */
    TRANSPARENT_RIGHT(0,0,0,10),

    /**
     * The ONLY_BOTTOM_WHITE border.
     */
    ONLY_BOTTOM_WHITE(ComponentColor.WHITE.get(),0,0,6,0),
    
    /**
     * The WHITE_BOTTOM_ALL_GOLD border.
     */
    WHITE_BOTTOM_ALL_GOLD(NO_BOTTOM_ALL_GOLD.get(), ONLY_BOTTOM_WHITE.get()),
    
    /**
     * The TRANSPARENT_LEFT_ALL_GOLD border.
     */
    TRANSPARENT_LEFT_ALL_GOLD(NO_RIGHT_ALL_GOLD.get(), TRANSPARENT_LEFT.get());

    /**
     * The border.
     */
    private Border border;

    /**
     * Creates a colored border with a size.
     * @param color The color of the border.
     * @param size The size of the border.
     */
    private ComponentBorder(Color color, int size) {
        this.border = BorderFactory.createLineBorder(color, size);
    }
    
    /**
     * Creates a colored border with a specified width for each side.
     * @param color The color of the border.
     * @param top The width of the top side.
     * @param left The width of the left side.
     * @param bottom The width of the bottom side.
     * @param right The width of the right side.
     */
    private ComponentBorder(Color color, int top, int left, int bottom, int right) {
        this.border = BorderFactory.createMatteBorder(top, left, bottom, right, color);
    }

    /**
     * Creates an empty border with a specified width for each side.
     * @param top The width of the top side.
     * @param left The width of the left side.
     * @param bottom The width of the bottom side.
     * @param right The width of the right side.
     */
    private ComponentBorder(int top, int left, int bottom, int right) {
        this.border = BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    /**
     * Creates a compound border specifying the border objects to use for the outside and inside edges.
     * @param outsideBorder The outside edge.
     * @param insideBorder The inside edje
     */
    private ComponentBorder(Border outsideBorder, Border insideBorder) {
        this.border = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
    }

    /**
     * 
     * @return The border.
     */
    public Border get() {
        return this.border;
    }
}