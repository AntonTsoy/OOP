package fxlab.snake.model;

import fxlab.snake.Direction;
import fxlab.snake.Point;
import java.util.ArrayList;
import java.util.List;


public class Snake {

    private final static int START_LENGTH = 1;

    private final int COLUMNS;
    private final int ROWS;

    private List<Point> snakeBody;
    private Point snakeHead;
    private Direction dir;
    private boolean gameContinue = true;
    private int score = 0;

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
        return !this.gameContinue;
    }

    public boolean isEatenFood(Food food) {
        Point foodCoords = food.getFood();
        if (snakeHead.getX() == foodCoords.getX() && snakeHead.getY() == foodCoords.getY()) {
            snakeBody.add(new Point(-1, -1));
            this.score += 5;
            return true;
        }

        return false;
    }

    public void changeDirection(Direction newDirection) {
        if (newDirection == Direction.RIGHT && this.dir != Direction.LEFT) {
            this.dir = Direction.RIGHT;
        } else if (newDirection == Direction.LEFT && this.dir != Direction.RIGHT) {
            this.dir = Direction.LEFT;
        } else if (newDirection == Direction.UP && this.dir != Direction.DOWN) {
            this.dir = Direction.UP;
        } else if (newDirection == Direction.DOWN && this.dir != Direction.UP) {
            this.dir = Direction.DOWN;
        }
    }

    public void move() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            this.snakeBody.get(i).setX(snakeBody.get(i - 1).getX());
            this.snakeBody.get(i).setY(snakeBody.get(i - 1).getY());
        }

        switch (dir) {  // Мб лучше сначала голова, потом остальное тело
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
        checkGame();
    }

    public Point getSnakeHead() {
        return this.snakeHead;
    }

    public List<Point> getSnakeBody() {
        return this.snakeBody;
    }

    public int getScore() {
        return this.score;
    }

    private void checkGame() {
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.getX() == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                this.gameContinue = false;
                return;
            }
        }
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
