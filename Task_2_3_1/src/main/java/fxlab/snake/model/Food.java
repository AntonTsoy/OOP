package fxlab.snake.model;

import fxlab.snake.Point; 

/**
 * Represents the food in the game.
 */
public class Food {

    private final int columns; 
    private final int rows; 

    private Point food; 

    /**
     * Constructs a new Food object with the specified number of columns and rows.
     *
     * @param cols The number of columns.
     * @param rows The number of rows.
     */
    public Food(int cols, int rows) {
        this.columns = cols; 
        this.rows = rows; 
        this.food = new Point(-1, -1); 
    }
    
    /**
     * Generates a new position for the food that does not coincide with the snake's position.
     *
     * @param snake The snake object.
     */
    public void generateFood(Snake snake) {
        start: 
        while (true) {
            this.food.setX((int) (Math.random() * this.columns));
            this.food.setY((int) (Math.random() * this.rows));

            for (Point snakePoint : snake.getSnakeBody()) {
                if (snakePoint.getX() == food.getX() && snakePoint.getY() == food.getY()) {
                    continue start; 
                }
            }
            break; 
        }
    }

    /**
     * Gets the current position of the food.
     *
     * @return The position of the food.
     */
    public Point getFood() {
        return food;
    }
}
