package fxlab.snake;

/**
 * Represents a point in 2D space with x and y coordinates.
 */
public class Point {
    
    private int x; // The x-coordinate of the point
    private int y; // The y-coordinate of the point

    /**
     * Constructs a new Point with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the y-coordinate of the point to the specified value.
     *
     * @param newY The new y-coordinate of the point.
     */
    public void setY(int newY) {
        this.y = newY;
    }

    /**
     * Sets the x-coordinate of the point to the specified value.
     *
     * @param newX The new x-coordinate of the point.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Gets the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gets the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    public int getX() {
        return this.x;
    }
}
