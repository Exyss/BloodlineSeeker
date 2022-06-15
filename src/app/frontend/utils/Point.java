package app.frontend.utils;

/**
 * The representation of a point with spatial coordinates.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class Point {
    
    /**
     * The x-coordinates of the point.
     */
    private int x;
    
    /**
     * The y-coordinates of the point.
     */
    private int y;
    
    /**
     * Initializes the x and y value using the parameters.
     * @param x The new x value.
     * @param y The new y value.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    /** 
     * @return The x-coordinate of the point.
     */
    public int getX() {
        return this.x;
    }

    
    /** 
     * @return The y-coordinate of the point.
     */
    public int getY() {
        return this.y;
    }
}