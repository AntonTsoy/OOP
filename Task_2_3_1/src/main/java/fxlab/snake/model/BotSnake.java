package fxlab.snake.model;

import java.util.ArrayList;
import java.util.List;
import fxlab.snake.Direction;

/**
 * Represents the snake in the game.
 */
public class BotSnake implements Player {

    private final int columns;
    private final int rows;

    private Direction dir;
    private List<Point> snakeBody;
    private Point snakeHead;
    private boolean gameContinue = true;
    private int score = 0;

    /**
     * Constructs a new Snake object with the specified number of columns and rows.
     *
     * @param cols The number of columns.
     * @param rows The number of rows.
     * @param startLength The initial length of the snake.
     * @param startX The initial X position of the snake.
     * @param startY The initial Y position of the snake.
     */
    public BotSnake(int cols, int rows, int startLength, int startX, int startY) {
        this.columns = cols;
        this.rows = rows;
        this.dir = Direction.UP;

        snakeBody = new ArrayList<Point>();
        for (int snakePointId = 0; snakePointId < startLength; snakePointId++) {
            snakeBody.add(new Point(startX + snakePointId, startY));
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
     * @param gameFood The list of food objects.
     * @return the index of the food eaten, or -1 if no food is eaten.
     */
    @Override
    public int isEatenFood(List<Food> gameFood) {
        if (isGameOver()) {
            return -1;
        }

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
     * Moves the snake one step in its current direction.
     *
     * @param target The target point to move towards.
     * @param enemies The list of enemy players.
     */
    public void move(Point target, List<Player> enemies) {
        if (isGameOver()) {
            return;
        }
        if (target == null) {
            return;
        }

        // Двигаем всю змею, кроме головы.
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            this.snakeBody.get(i).setX(snakeBody.get(i - 1).getX());
            this.snakeBody.get(i).setY(snakeBody.get(i - 1).getY());
        }

        // Составили список возможных вариантов куда можно сдвинуть голову.
        int headX = this.snakeHead.getX();
        int headY = this.snakeHead.getY();
        List<Point> possibleWays = new ArrayList<Point>();
        possibleWays.add(new Point((headX + 1) % this.columns, headY));
        possibleWays.add(new Point((headX - 1 + this.columns) % this.columns, headY));
        possibleWays.add(new Point(headX, (headY + 1) % this.rows));
        possibleWays.add(new Point(headX, (headY - 1 + this.rows) % this.rows));

        int pointId = 0;
        while (pointId < possibleWays.size()) {
            List<Player> players = new ArrayList<Player>(enemies);
            players.add(this);
            if (isBadPosition(possibleWays.get(pointId), players)) {
                possibleWays.remove(pointId);
            } else {
                pointId++;
            }
        }

        int targetX = target.getX();
        int targetY = target.getY();
        int bestWayId = -1;
        double bestDistance = 100000000;
        for (int wayId = 0; wayId < possibleWays.size(); wayId++) {
            int wayX = possibleWays.get(wayId).getX();
            int wayY = possibleWays.get(wayId).getY();
            double sum = Math.pow(targetX - wayX, 2) + Math.pow(targetY - wayY, 2);
            double distance = Math.sqrt(sum);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestWayId = wayId;
            }
        }

        if (bestWayId >= 0) {
            int bestX = possibleWays.get(bestWayId).getX();
            int bestY = possibleWays.get(bestWayId).getY();
            int deltaX = bestX - headX;
            int deltaY = bestY - headY;
            if (deltaX == 1) {
                this.dir = Direction.RIGHT;
            } else if (deltaX == -1) {
                this.dir = Direction.LEFT;
            } else if (deltaY == 1) {
                this.dir = Direction.DOWN;
            } else if (deltaY == -1) {
                this.dir = Direction.UP;
            }

            snakeHead.setX(bestX);
            snakeHead.setY(bestY);
        } else {
            if (this.dir == Direction.RIGHT) {
                snakeHead.setX((snakeHead.getX() + 1) % this.columns);
            } else if (this.dir == Direction.LEFT) {
                snakeHead.setX((snakeHead.getX() - 1 + this.columns) % this.columns);
            } else if (this.dir == Direction.UP) {
                snakeHead.setY((snakeHead.getY() - 1 + this.rows) % this.rows);
            } else if (this.dir == Direction.DOWN) {
                snakeHead.setY((snakeHead.getY() + 1) % this.rows);
            }
        }

        checkGame(enemies);
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
}
