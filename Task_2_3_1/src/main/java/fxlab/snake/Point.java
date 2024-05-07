package fxlab.snake;

/**
 * Represents a point in 2D space with x and y coordinates.
 */
public class Point {
    
    private int xCoord; // The x-coordinate of the point
    private int yCoord; // The y-coordinate of the point

    /**
     * Constructs a new Point with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    /**
     * Sets the y-coordinate of the point to the specified value.
     *
     * @param newY The new y-coordinate of the point.
     */
    public void setY(int newY) {
        this.yCoord = newY;
    }

    /**
     * Sets the x-coordinate of the point to the specified value.
     *
     * @param newX The new x-coordinate of the point.
     */
    public void setX(int newX) {
        this.xCoord = newX;
    }

    /**
     * Gets the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
    public int getY() {
        return this.yCoord;
    }

    /**
     * Gets the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    public int getX() {
        return this.xCoord;
    }
}
