package fxlab.snake;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import fxlab.snake.model.BotSnake;
import fxlab.snake.model.Player;
import java.util.List;
import fxlab.snake.model.Food;
import fxlab.snake.model.Point;
import fxlab.snake.model.Snake;

import org.junit.jupiter.api.Test;

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

    @Test
    public void testBigGeneratingOfFood() {
        Snake mainSnake = new Snake(2, 2);
        List<Player> othersSnakes = new ArrayList<Player>();
        othersSnakes.add(new BotSnake(2, 2, 1, 0, 0));
        List<Food> gameFood = new ArrayList<>();
        Food oldFood = new Food(2, 2);
        oldFood.generateFood(mainSnake, gameFood, othersSnakes);
        gameFood.add(oldFood);
        Food newFood = new Food(2, 2);
        newFood.generateFood(mainSnake, gameFood, othersSnakes);
        assertNotEquals(0, newFood.getFood().getX() + newFood.getFood().getY());
    }
}