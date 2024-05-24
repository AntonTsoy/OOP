package fxlab.snake.model;

import java.util.List;

public interface Player {

    public boolean isGameOver();

    public int isEatenFood(List<Food> gameFood);

    public Point getSnakeHead();

    public List<Point> getSnakeBody();

    public int getScore();
}
