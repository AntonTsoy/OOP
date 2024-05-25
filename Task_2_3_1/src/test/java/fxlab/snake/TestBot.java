package fxlab.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import fxlab.snake.model.BotSnake;
import fxlab.snake.model.Player;
import java.util.List;
import fxlab.snake.model.Food;
import fxlab.snake.model.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for model class - Bots.
 */
public class TestBot {

    private BotSnake botSnake;

    @BeforeEach
    public void setUp() {
        botSnake = new BotSnake(10, 10, 1, 5, 5);
    }

    @Test
    public void testInitialPosition() {
        assertEquals(5, botSnake.getSnakeHead().getX());
    }

    @Test
    public void testMoving() {
        Point target = new Point(8, 5);
        List<Player> enemies = new ArrayList<>();

        botSnake.move(target, enemies);
        botSnake.move(target, enemies);
        botSnake.move(target, enemies);
        assertEquals(8, botSnake.getSnakeHead().getX());
    }

    @Test
    public void testIsGameOver() {
        BotSnake mainSnake = new BotSnake(1, 1, 1, 0, 0);
        assertFalse(mainSnake.isGameOver());

        BotSnake enemySnake = new BotSnake(1, 1, 1, 0, 0);
        List<Player> enemyPlayers = new ArrayList<>();
        enemyPlayers.add(enemySnake);
        mainSnake.move(new Point(0, 0), enemyPlayers);
        assertTrue(mainSnake.isGameOver());
    }

    @Test
    public void testIsEatenFood() {
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food(10, 10));
        assertEquals(-1, botSnake.isEatenFood(foodList));

        foodList.get(0).getFood().setX(5);
        foodList.get(0).getFood().setY(5);
        assertEquals(0, botSnake.isEatenFood(foodList));
    }

    @Test
    public void testGetScore() {
        List<Food> foodList = new ArrayList<>();
        Food food = new Food(10, 10);
        foodList.add(food);
        food.getFood().setX(4);
        food.getFood().setY(5);
        botSnake.move(food.getFood(), new ArrayList<Player>());
        botSnake.isEatenFood(foodList);

        food.getFood().setX(3);
        food.getFood().setY(5);
        botSnake.move(food.getFood(), new ArrayList<Player>());
        botSnake.isEatenFood(foodList);
        assertEquals(10, botSnake.getScore());
    }
}
