package fxlab.snake.model;

import fxlab.snake.Point;
import java.util.ArrayList;
import java.util.List;


public class Snake {

    private final static int START_LENGTH = 1;

    private final int COLUMNS;
    private final int ROWS;

    private List<Point> snakeBody;
    private Point snakeHead;

    public Snake(int cols, int rows) {
        this.COLUMNS = cols;
        this.ROWS = rows;

        snakeBody = new ArrayList<Point>();
        for (int snakePointId = 0; snakePointId < START_LENGTH; snakePointId++) {
            snakeBody.add(new Point(this.COLUMNS / 2, this.ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
    }
    
    public boolean isGameOver() {
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.getX() == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                return true;
            }
        }

        return false;
    }

    public void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 5;
        }
    }

    public void snakeMove() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            this.snakeBody.get(i).setX(snakeBody.get(i - 1).getX());
            this.snakeBody.get(i).setY(snakeBody.get(i - 1).getY());
        }

        switch (currentDirection) {  // Мб лучше сначала голова, потом остальное тело
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }
         = isGameOver();
        eatFood();
    }

    public List<Point> getSankeBody() {
        return this.snakeBody;
    }

    private void moveRight() {
        snakeHead.setX((snakeHead.getX() + 1) % this.COLUMNS);
    }

    private void moveLeft() {
        snakeHead.setX((snakeHead.getX() - 1 + this.COLUMNS) % this.COLUMNS);
    }

    private void moveUp() {
        snakeHead.setY((snakeHead.getY() - 1 + this.ROWS) % this.ROWS);
    }

    private void moveDown() {
        snakeHead.setY((snakeHead.getY() + 1) % this.ROWS);
    }
}
