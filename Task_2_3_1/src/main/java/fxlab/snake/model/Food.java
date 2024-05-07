package fxlab.snake.model;

import fxlab.snake.Point;


public class Food {

    private final int COLUMNS;
    private final int ROWS;

    private Point food;

    public Food(int cols, int rows) {
        this.COLUMNS = cols;
        this.ROWS = rows;
        this.food = new Point(-1, -1);
    }
    
    public void generateFood(Snake snake) {
        start:
        while (true) {
            this.food.setX((int)(Math.random() * this.COLUMNS));
            this.food.setY((int)(Math.random() * this.ROWS));

            for (Point snakePoint : snake.getSnakeBody()) {
                if (snakePoint.getX() == food.getX() && snakePoint.getY() == food.getY()) {
                    continue start;
                }
            }
            break;
        }
    }

    public Point getFood() {
        return food;
    }
}
