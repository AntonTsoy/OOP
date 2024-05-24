package fxlab.snake.model;

import java.util.List;

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
     * Generates a new position for the food that does not coincide with the snake's and another
     * food position.
     *
     * @param snake The snake object.
     * @param gameFood The food list of the game.
     */
    public void generateFood(Snake snake, List<Food> gameFood, List<Player> otherPlayers) {
        start: 
        while (true) {
            this.food.setX((int) (Math.random() * this.columns));
            this.food.setY((int) (Math.random() * this.rows));

            for (Point snakePoint : snake.getSnakeBody()) {
                if (snakePoint.getX() == food.getX() && snakePoint.getY() == food.getY()) {
                    continue start; 
                }
            }
            for (Food pieceFood : gameFood) {
                if (pieceFood.getFood().getX() == food.getX() 
                    && pieceFood.getFood().getY() == food.getY()) {
                    continue start;
                }
            }
            for (Player player : otherPlayers) {
                for (Point playerPoint : player.getSnakeBody()) {
                    if (playerPoint.getX() == food.getX() && playerPoint.getY() == food.getY()) {
                        continue start;
                    }
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
