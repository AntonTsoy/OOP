package fxlab.snake;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import fxlab.snake.model.Food;
import fxlab.snake.model.Snake;


/**
 * Tests for model class - Food.
 */
public class TestFood {

    @Test
    public void testGenerateFood() {
        Snake snake = new Snake(100, 100);
        snake.getSnakeBody().add(new Point(0, 0)); 

        Food food = new Food(10, 10);

        food.generateFood(snake);

        assertNotEquals(snake.getSnakeBody().get(0).getX(), food.getFood().getX());
        assertNotEquals(snake.getSnakeBody().get(0).getY(), food.getFood().getY());
    }

    @Test
    public void testGetFood() {
        Snake snake = new Snake(10, 10);
        Food food = new Food(10, 10);
        food.generateFood(snake);
        Point foodPosition = food.getFood();

        assertTrue(foodPosition.getX() >= 0 && foodPosition.getX() < 10);
        assertTrue(foodPosition.getY() >= 0 && foodPosition.getY() < 10);
    }
}