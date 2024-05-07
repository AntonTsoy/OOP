package fxlab.snake.model;

import fxlab.snake.Point;
import javafx.scene.image.Image;


public class Food {

    private final int COLUMNS;
    private final int ROWS;

    private Point food;

    public Food(int cols, int rows) {
        this.COLUMNS = cols;
        this.ROWS = rows;
    }
    
    public void generateFood(Snake snake) {
        start:
        while (true) {
            food.setX((int)(Math.random() * COLUMNS));
            food.setY((int)(Math.random() * ROWS));

            for (Point snakePoint : snake.getSankeBody()) {
                if (snakePoint.getX() == food.getX() && snakePoint.getY() == food.getY()) {
                    continue start;
                }
            }
            foodImage = new Image(FOOD_IMAGE);
            break;
        }
    }
}
