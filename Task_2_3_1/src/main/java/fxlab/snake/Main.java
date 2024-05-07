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


public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static final int FOOD_CNT = 4;
    private static String[] FOOD_IMAGES = new String[FOOD_CNT];

    private GraphicsContext graphContext;
    private Image foodImage;
    private Snake snake;
    private Food food;

    @Override
    public void start(Stage primaryStage) throws Exception {
        for (int i = 0; i < FOOD_CNT; i++) {
            FOOD_IMAGES[i] = Main.class.getResource("img/" + i + ".png").toExternalForm();
        }
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.show();
        graphContext = canvas.getGraphicsContext2D();
        snake = new Snake(COLUMNS, ROWS);
        food = new Food(COLUMNS, ROWS);
        food.generateFood(snake);
        foodImage = new Image(FOOD_IMAGES[(int)(Math.random() * FOOD_CNT)]);

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

        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(270), e -> run(graphContext)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext graphContext) {
        if (snake.isGameOver()) {
            graphContext.setFill(Color.RED);
            graphContext.setFont(new Font("Digital-7", 70));
            graphContext.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            return;
        }
        drawBackground(graphContext);
        drawFood(graphContext);
        drawSnake(graphContext);
        drawScore();

        snake.move();
        if (snake.isEatenFood(food)) {
            food.generateFood(snake);
            foodImage = new Image(FOOD_IMAGES[(int)(Math.random() * FOOD_CNT)]);
        }
    }

    private void drawBackground(GraphicsContext graphContext) {
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

    private void drawFood(GraphicsContext graphContext) {
        Point foodCoords = food.getFood();
        graphContext.drawImage(
            this.foodImage, foodCoords.getX() * SQUARE_SIZE, 
            foodCoords.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

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

    private void drawScore() {
        graphContext.setFill(Color.WHITE);
        graphContext.setFont(new Font("Digital-7", 35));
        graphContext.fillText("Score: " + snake.getScore(), 10, 35);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
