package app.frontend.utils;

import java.awt.Insets;

/**
 * The margins that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum Margin {
    /**
     * The EMPTY margin.
     */
    EMPTY(0, 0, 0, 0);

    /**
     * The insets used as margin.
     */
    private Insets inset;

    /**
     * Creates a Margin with the specified top, left, bottom and right insets.
     * @param top The value of the top insets.
     * @param left The value of the left insets.
     * @param bottom The value of the bottom insets.
     * @param right The value of the right insets.
     */
    private Margin(int top, int left, int bottom, int right) {
        this.inset = new Insets(top, left, bottom, right);
    }

    /**
     * 
     * @return the insets.
     */
    public Insets get() {
        return this.inset;
    }
}