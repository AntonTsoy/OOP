package fxlab.snake;

import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import fxlab.snake.model.Snake;
import fxlab.snake.model.Food;


/**
 * The main class that controls the Snake game.
 */
public class Main extends Application {

    private static final int WIDTH = 800; // The width of the game window
    private static final int HEIGHT = WIDTH; // The height of the game window
    private static final int ROWS = 20; // The number of rows in the game grid
    private static final int COLUMNS = ROWS; // The number of columns in the game grid
    private static final int SQUARE_SIZE = WIDTH / ROWS; // The size of each square in the grid
    private static final int FOOD_CNT = 4; // The number of different food images
    private static String[] FOOD_IMAGES = new String[FOOD_CNT]; // Array to store paths to food images

    private GraphicsContext graphContext; // Graphics context for rendering
    private Image foodImage; // The image of the food
    private Snake snake; // The snake object
    private Food food; // The food object

    /**
     * Starts the game and initializes the game window and objects.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws Exception If an error occurs during initialization.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialization of food images array
        for (int i = 0; i < FOOD_CNT; i++) {
            FOOD_IMAGES[i] = Main.class.getResource("img/" + i + ".png").toExternalForm();
        }

        // Initialization of the game canvas and scene
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.show();

        // Initialization of graphics context and game objects
        graphContext = canvas.getGraphicsContext2D();
        snake = new Snake(COLUMNS, ROWS);
        food = new Food(COLUMNS, ROWS);
        food.generateFood(snake);
        foodImage = new Image(FOOD_IMAGES[(int)(Math.random() * FOOD_CNT)]);

        // Event handler for keyboard input
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    snake.changeDirection(Direction.RIGHT);
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    snake.changeDirection(Direction.LEFT);
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    snake.changeDirection(Direction.UP);
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    snake.changeDirection(Direction.DOWN);
                }
            }
        });

        // Animation timeline for the game loop
        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(270), e -> run(graphContext)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * The main game loop that updates the game state and renders it.
     *
     * @param graphContext The graphics context for rendering.
     */
    private void run(GraphicsContext graphContext) {
        // Game over condition
        if (snake.isGameOver()) {
            graphContext.setFill(Color.RED);
            graphContext.setFont(new Font("Digital-7", 70));
            graphContext.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            return;
        }

        // Render game elements
        drawBackground(graphContext);
        drawFood(graphContext);
        drawSnake(graphContext);
        drawScore();

        // Move snake and check for food
        snake.move();
        if (snake.isEatenFood(food)) {
            food.generateFood(snake);
            foodImage = new Image(FOOD_IMAGES[(int)(Math.random() * FOOD_CNT)]);
        }
    }

    /**
     * Draws the background of the game grid.
     *
     * @param graphContext The graphics context for rendering.
     */
    private void drawBackground(GraphicsContext graphContext) {
        // Drawing background grid
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    graphContext.setFill(Color.web("AAD751"));
                } else {
                    graphContext.setFill(Color.web("A2D149"));
                }
                graphContext.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    /**
     * Draws the food on the game grid.
     *
     * @param graphContext The graphics context for rendering.
     */
    private void drawFood(GraphicsContext graphContext) {
        Point foodCoords = food.getFood();
        graphContext.drawImage(
            this.foodImage, foodCoords.getX() * SQUARE_SIZE, 
            foodCoords.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Draws the snake on the game grid.
     *
     * @param graphContext The graphics context for rendering.
     */
    private void drawSnake(GraphicsContext graphContext) {
        Point snakeHead = snake.getSnakeHead();
        graphContext.setFill(Color.web("4674E9"));
        graphContext.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE,
            SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

        List<Point> snakeBody = snake.getSnakeBody();
        for (int i = 1; i < snakeBody.size(); i++) {
            graphContext.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE,
                snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                SQUARE_SIZE - 1, 20, 20);
        }
    }

    /**
     * Draws the score on the game screen.
     */
    private void drawScore() {
        graphContext.setFill(Color.WHITE);
        graphContext.setFont(new Font("Digital-7", 35));
        graphContext.fillText("Score: " + snake.getScore(), 10, 35);
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
