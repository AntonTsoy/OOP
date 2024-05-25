package fxlab.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fxlab.snake.model.Food;
import fxlab.snake.model.Point;
import fxlab.snake.model.Player;
import fxlab.snake.model.Snake;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Tests for model class - Snake.
 */
public class TestSnake {

    @Test
    public void testIsGameOver() {
        Snake snake = new Snake(10, 10);
        assertFalse(snake.isGameOver());
    }

    @Test
    public void testIsEatenFood() {
        List<Player> enemies = new ArrayList<>();
        Snake snake = new Snake(10, 10);
        Food food = new Food(10, 10);

        // Snake head at food position
        snake.changeDirection(Direction.RIGHT);
        snake.move(enemies);
        food.getFood().setX(snake.getSnakeHead().getX());
        food.getFood().setY(snake.getSnakeHead().getY());

        assertTrue(snake.isEatenFood(food));
    }

    @Test
    public void testChangeDirection() {
        Snake snake = new Snake(10, 10);
        Point startCoords = snake.getSnakeHead();
        int startX = startCoords.getX();
        int startY = startCoords.getY();
        snake.changeDirection(Direction.RIGHT);
        assertEquals(startX, snake.getSnakeHead().getX());
        assertEquals(startY, snake.getSnakeHead().getY());
    }

    @Test
    public void testIsGameOverHard() {
        List<Player> enemies = new ArrayList<>();
        Snake snake = new Snake(10, 10);

        snake.changeDirection(Direction.RIGHT);
        snake.move(enemies);
        snake.changeDirection(Direction.LEFT);
        snake.move(enemies);
        snake.changeDirection(Direction.UP);
        snake.move(enemies);
        snake.changeDirection(Direction.DOWN);
        snake.move(enemies);
        snake.changeDirection(Direction.LEFT);
        snake.move(enemies);
        snake.changeDirection(Direction.DOWN);
        snake.move(enemies);

        assertFalse(snake.isGameOver());
    }


    @Test
    public void testGetSnakeHead() {
        Snake snake = new Snake(10, 10);
        assertNotNull(snake.getSnakeHead());
    }

    @Test
    public void testGetSnakeBody() {
        Snake snake = new Snake(10, 10);
        assertNotNull(snake.getSnakeBody());
    }

    @Test
    public void testGetScore() {
        Snake snake = new Snake(10, 10);
        assertEquals(0, snake.getScore());
    }

    @Test
    public void testSnakeEats5FoodsThenHitsItself() {
        List<Player> enemies = new ArrayList<>();
        Snake snake = new Snake(10, 10);
        Food food = new Food(10, 10);

        snake.changeDirection(Direction.UP);
        for (int i = 0; i < 5; i++) {
            snake.move(enemies);
            food.getFood().setX(snake.getSnakeHead().getX());
            food.getFood().setY(snake.getSnakeHead().getY());
            snake.isEatenFood(food);
        }

        // Change direction to hit itself
        snake.changeDirection(Direction.RIGHT);
        snake.move(enemies);
        snake.changeDirection(Direction.DOWN);
        snake.move(enemies);
        snake.changeDirection(Direction.LEFT);
        snake.move(enemies);
        snake.move(enemies);
        assertTrue(snake.isGameOver());
    }

    @Test
    public void testAfterExtraTask() {
        Snake newPlayer = new Snake(1, 1);
        newPlayer.move(new ArrayList<Player>());
        assertEquals(null, newPlayer.getDirection());
    }

    @Test
    public void testEatingBigLunch() {
        List<Food> gameFood = new ArrayList<>();
        Snake player = new Snake(2, 2);
        gameFood.add(new Food(2, 2));
        gameFood.get(0).generateFood(player);
        assertEquals(-1, player.isEatenFood(gameFood));
    }
}