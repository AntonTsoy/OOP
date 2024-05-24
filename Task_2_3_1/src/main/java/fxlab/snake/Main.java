package fxlab.snake;

import fxlab.snake.model.BotSnake;
import fxlab.snake.model.Food;
import fxlab.snake.model.Player;
import fxlab.snake.model.Point;
import fxlab.snake.model.Snake;

import java.util.ArrayList;
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


/**
 * The main class that controls the Snake game.
 */
public class Main extends Application {

    private static final int width = 800; // The width of the game window
    private static final int height = width; // The height of the game window
    private static final int rows = 20; // The number of rows in the game grid
    private static final int columns = rows; // The number of columns in the game grid
    private static final int squareSize = width / rows; // The size of each square in the grid
    private static final int foodCnt = 4; // The number of different food images
    private static final int gameFoodCnt = 3;
    private static String[] sourceFoodImages = new String[foodCnt];
    
    private GraphicsContext graphContext; // Graphics context for rendering
    private List<Image> gameFoodImages; // The image of the food
    private Snake snake; // The snake object
    private List<Food> gameFood; // The food object
    private List<Player> enemies;
    private BotSnake red;
    private List<Player> redEnemies;
    private BotSnake yellow;
    private List<Player> yellowEnemies;


    /**
     * Starts the game and initializes the game window and objects.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws Exception If an error occurs during initialization.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialization of food images array
        for (int i = 0; i < foodCnt; i++) {
            sourceFoodImages[i] = Main.class.getResource("img/" + i + ".png").toExternalForm();
        }

        // Initialization of the game canvas and scene
        Canvas canvas = new Canvas(width, height);
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.show();

        // Enemies
        this.enemies = new ArrayList<Player>();
        this.redEnemies = new ArrayList<Player>();
        this.yellowEnemies = new ArrayList<Player>();
        this.red = new BotSnake(columns, rows, 5, 0, 0);
        this.yellow = new BotSnake(columns, rows, 1, columns-1, rows-1);
        this.enemies.add(red);
        this.enemies.add(yellow);

        // Initialization of graphics context and game objects
        graphContext = canvas.getGraphicsContext2D();
        snake = new Snake(columns, rows);
        this.redEnemies.add(snake);
        this.redEnemies.add(yellow);
        this.yellowEnemies.add(snake);
        this.yellowEnemies.add(red);
        gameFoodImages = new ArrayList<Image>(gameFoodCnt);
        gameFood = new ArrayList<Food>(gameFoodCnt);
        for (int foodId = 0; foodId < gameFoodCnt; foodId++) {
            gameFoodImages.add(new Image(sourceFoodImages[(int) (Math.random() * foodCnt)]));
            Food pieceFood = new Food(columns, rows);
            pieceFood.generateFood(snake, gameFood, enemies);
            gameFood.add(pieceFood);
        }

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
            graphContext.fillText("Game Over", width / 3.5, height / 2);
            return;
        }

        // Render game elements
        drawBackground(graphContext);
        drawFood(graphContext);
        drawSnake(graphContext);
        drawRedEnemy(graphContext);
        drawYellowEnemy(graphContext);
        drawScore();
        drawYellowScore();

        Point redTarget = movePoint(snake.getSnakeHead(), snake.getDirection(), 1);
        int enemyId = 0;
        while (enemyId < redEnemies.size()) {
            if (redEnemies.get(enemyId).isGameOver()) {
                redEnemies.remove(enemyId);
            } else {
                enemyId++;
            }
        }
        this.red.move(redTarget, redEnemies);
        enemyId = 0;
        while (enemyId < yellowEnemies.size()) {
            if (yellowEnemies.get(enemyId).isGameOver()) {
                yellowEnemies.remove(enemyId);
            } else {
                enemyId++;
            }
        }
        if (snake.getDirection() == null) {
            this.yellow.move(null, yellowEnemies);
        } else {
            this.yellow.move(this.gameFood.get(0).getFood(), yellowEnemies);
        }

        // Move snake and check for food
        enemyId = 0;
        while (enemyId < enemies.size()) {
            if (enemies.get(enemyId).isGameOver()) {
                enemies.remove(enemyId);
            } else {
                enemyId++;
            }
        }
        snake.move(enemies);
        int enemyFoodId = this.yellow.isEatenFood(gameFood);
        int eatenFoodId = snake.isEatenFood(gameFood);
        if (enemyFoodId >= 0 && eatenFoodId >= 0) {
            if (enemyFoodId > eatenFoodId) {
                checkDelFood(enemyFoodId);
                checkDelFood(eatenFoodId);
            } else if (eatenFoodId > enemyFoodId) {
                checkDelFood(eatenFoodId);
                checkDelFood(enemyFoodId);
            }
        } else if (enemyFoodId >= 0) {
            checkDelFood(enemyFoodId);
        } else if (eatenFoodId >= 0) {
            checkDelFood(eatenFoodId);
        }
    }

    private void checkDelFood(int foodId) {
        gameFood.remove(foodId);
        gameFoodImages.remove(foodId);
        Food pieceFood = new Food(columns, rows);
        pieceFood.generateFood(snake, gameFood, enemies);
        gameFood.add(pieceFood);
        gameFoodImages.add(new Image(sourceFoodImages[(int) (Math.random() * foodCnt)]));
    }

    /**
     * Draws the background of the game grid.
     *
     * @param graphContext The graphics context for rendering.
     */
    private void drawBackground(GraphicsContext graphContext) {
        // Drawing background grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0) {
                    graphContext.setFill(Color.web("AAD751"));
                } else {
                    graphContext.setFill(Color.web("A2D149"));
                }
                graphContext.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }

    /**
     * Draws the food on the game grid.
     *
     * @param graphContext The graphics context for rendering.
     */
    private void drawFood(GraphicsContext graphContext) {
        Point foodCoords;
        for (int foodId = 0; foodId < gameFoodCnt; foodId++) {
            foodCoords = gameFood.get(foodId).getFood();
            graphContext.drawImage(
                this.gameFoodImages.get(foodId), foodCoords.getX() * squareSize, 
                foodCoords.getY() * squareSize, squareSize, squareSize);
        }
    }

    /**
     * Draws the snake on the game grid.
     *
     * @param graphContext The graphics context for rendering.
     */
    private void drawSnake(GraphicsContext graphContext) {
        Point snakeHead = snake.getSnakeHead();
        graphContext.setFill(Color.web("4674E9"));
        graphContext.fillRoundRect(snakeHead.getX() * squareSize, snakeHead.getY() * squareSize,
            squareSize - 1, squareSize - 1, 38, 38);

        List<Point> snakeBody = snake.getSnakeBody();
        for (int i = 1; i < snakeBody.size(); i++) {
            graphContext.fillRoundRect(snakeBody.get(i).getX() * squareSize,
                snakeBody.get(i).getY() * squareSize, squareSize - 1,
                squareSize - 1, 25, 25);
        }
    }

    private void drawRedEnemy(GraphicsContext graphicsContext) {
        if (red.isGameOver()) {
            return;
        }
        Point redHead = this.red.getSnakeHead();
        graphContext.setFill(Color.web("800000"));
        graphContext.fillRoundRect(redHead.getX() * squareSize, redHead.getY() * squareSize,
            squareSize - 1, squareSize - 1, 10, 10);

        List<Point> redBody = red.getSnakeBody();
        for (int i = 1; i < redBody.size(); i++) {
            graphContext.fillRoundRect(redBody.get(i).getX() * squareSize,
                redBody.get(i).getY() * squareSize, squareSize - 1,
                squareSize - 1, 42, 42);
        }
    }

    private void drawYellowEnemy(GraphicsContext graphicsContext) {
        if (yellow.isGameOver()) {
            return;
        }
        Point yellowHead = this.yellow.getSnakeHead();
        graphContext.setFill(Color.web("FFFF00"));
        graphContext.fillRoundRect(yellowHead.getX() * squareSize, yellowHead.getY() * squareSize,
            squareSize - 1, squareSize - 1, 50, 50);

        List<Point> yellowBody = yellow.getSnakeBody();
        for (int i = 1; i < yellowBody.size(); i++) {
            graphContext.fillRoundRect(yellowBody.get(i).getX() * squareSize,
                yellowBody.get(i).getY() * squareSize, squareSize - 1,
                squareSize - 1, 50, 50);
        }
    }

    /**
     * Draws the score on the game screen.
     */
    private void drawScore() {
        graphContext.setFill(Color.BLUE);
        graphContext.setFont(new Font("Digital-7", 35));
        graphContext.fillText("Score: " + snake.getScore(), 10, 35);
    }
    
    private void drawYellowScore() {
        if (yellow.isGameOver()) {
            return;
        }
        graphContext.setFill(Color.YELLOW);
        graphContext.setFont(new Font("Digital-7", 35));
        graphContext.fillText("Score: " + yellow.getScore(), 10 + 15 * squareSize, 35);
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Point movePoint(Point point, Direction dir, int times) {
        Point resPoint = new Point(point.getX(), point.getY());
        if (dir == null) {
            return null;
        } else if (dir == Direction.RIGHT) {
            resPoint.setX((point.getX() + times) % columns);
        } else if (dir == Direction.LEFT) {
            resPoint.setX((point.getX() - times + columns) % columns);
        } else if (dir == Direction.UP) {
            resPoint.setY((point.getY() - times + rows) % rows);
        } else if (dir == Direction.DOWN) {
            resPoint.setY((point.getY() + times) % rows);
        }
        return resPoint;
    }
}
