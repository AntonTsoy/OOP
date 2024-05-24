package fxlab.snake.model;

import java.util.List;

/**
 * Interface describes common properties of snake game players.
 */
public interface Player {

    boolean isGameOver();

    int isEatenFood(List<Food> gameFood);

    Point getSnakeHead();

    List<Point> getSnakeBody();

    int getScore();
}
