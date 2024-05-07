package fxlab.snake;

import fxlab.snake.model.Food;
import fxlab.snake.model.Snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        Snake snake = new Snake(10, 10);
        Food food = new Food(10, 10);

        // Snake head at food position
        snake.changeDirection(Direction.RIGHT);
        snake.move();
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
        snake.move();
        snake.changeDirection(Direction.LEFT);
        snake.move();

        Point endCoords = snake.getSnakeHead();
        assertEquals(startX, endCoords.getX()-2);
        assertEquals(startY, endCoords.getY());
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
}