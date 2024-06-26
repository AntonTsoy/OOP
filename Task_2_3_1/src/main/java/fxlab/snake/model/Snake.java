package fxlab.snake.model;

import fxlab.snake.Direction;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the snake in the game.
 */
public class Snake implements Player {

    private static final int startLength = 1;

    private final int columns;
    private final int rows;

    private List<Point> snakeBody;
    private Point snakeHead;
    private Direction dir;
    private boolean gameContinue = true;
    private int score = 0;

    /**
     * Constructs a new Snake object with the specified number of columns and rows.
     *
     * @param cols The number of columns.
     * @param rows The number of rows.
     */
    public Snake(int cols, int rows) {
        this.columns = cols;
        this.rows = rows;

        snakeBody = new ArrayList<Point>();
        for (int snakePointId = 0; snakePointId < startLength; snakePointId++) {
            snakeBody.add(new Point(this.columns / 2, this.rows / 2));
        }
        snakeHead = snakeBody.get(0);
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    @Override
    public boolean isGameOver() {
        return !this.gameContinue;
    }

    /**
     * Checks if the snake has eaten the food.
     *
     * @param food The food object.
     * @return true if the snake has eaten the food, false otherwise.
     */
    public boolean isEatenFood(Food food) {
        Point foodCoords = food.getFood();
        if (snakeHead.getX() == foodCoords.getX() && snakeHead.getY() == foodCoords.getY()) {
            snakeBody.add(new Point(-1, -1));
            this.score += 5;
            return true;
        }

        return false;
    }

    /**
     * Checks if the snake has eaten the food.
     *
     * @param gameFood The list of food objects.
     * @return true if the snake has eaten the food, false otherwise.
     */
    @Override
    public int isEatenFood(List<Food> gameFood) {
        Point foodCoords;
        for (int foodId = 0; foodId < gameFood.size(); foodId++) {
            foodCoords = gameFood.get(foodId).getFood();

            if (snakeHead.getX() == foodCoords.getX() && snakeHead.getY() == foodCoords.getY()) {
                snakeBody.add(new Point(-1, -1));
                this.score += 5;
                return foodId;
            }
        }

        return -1;
    }

    /**
     * Changes the direction of the snake.
     *
     * @param newDirection The new direction.
     */
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

    /**
     * Moves the snake one step in its current direction.
     */
    public void move(List<Player> enemies) {
        checkGame(enemies);
        if (this.dir == null) {
            return;
        }

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            this.snakeBody.get(i).setX(snakeBody.get(i - 1).getX());
            this.snakeBody.get(i).setY(snakeBody.get(i - 1).getY());
        }

        switch (dir) {
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
            default:
                break;
        }
    }

    /**
     * Gets the position of the snake's head.
     *
     * @return The position of the snake's head.
     */
    @Override
    public Point getSnakeHead() {
        return this.snakeHead;
    }

    /**
     * Gets the list of points representing the snake's body.
     *
     * @return The list of points representing the snake's body.
     */
    @Override
    public List<Point> getSnakeBody() {
        return this.snakeBody;
    }

    /**
     * Gets the current score of the snake.
     *
     * @return The current score of the snake.
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * Function return a snake moving direction.
     *
     * @return snake direction.
     */
    public Direction getDirection() {
        return this.dir;
    }

    private boolean isBadPosition(Point position, List<Player> players) {
        for (Player player : players) {
            List<Point> playerBody = player.getSnakeBody();
            for (int bodyPointId = 0; bodyPointId < playerBody.size(); bodyPointId++) {
                if (position.getX() == playerBody.get(bodyPointId).getX()
                    && position.getY() == playerBody.get(bodyPointId).getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkGame(List<Player> enemies) {
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.getX() == snakeBody.get(i).getX() 
                && snakeHead.getY() == snakeBody.get(i).getY()) {
                this.gameContinue = false;
                return;
            }
        }
        if (isBadPosition(snakeHead, enemies)) {
            this.gameContinue = false;
        }
    }

    private void moveRight() {
        snakeHead.setX((snakeHead.getX() + 1) % this.columns);
    }

    private void moveLeft() {
        snakeHead.setX((snakeHead.getX() - 1 + this.columns) % this.columns);
    }

    private void moveUp() {
        snakeHead.setY((snakeHead.getY() - 1 + this.rows) % this.rows);
    }

    private void moveDown() {
        snakeHead.setY((snakeHead.getY() + 1) % this.rows);
    }
}
