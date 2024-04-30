package fxlab.snake.hello;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static javafx.scene.input.KeyCode.RIGHT;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Rectangle[][] field = new Rectangle[24][16];

        SnakeElement snake  = new SnakeElement(12, 8);
        
        Group root = new Group();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 16; j++) {
                Rectangle rectangle = new Rectangle();

                field[i][j] = rectangle;

                rectangle.setX(50 * i);
                rectangle.setY(50 * j);
                rectangle.setHeight(50);
                rectangle.setWidth(50);
                rectangle.setFill(Color.BLACK);
                rectangle.setStroke(Color.AZURE);
                if (i == snake.x() && j == snake.y()) {
                    rectangle.setFill(Color.BLACK);
                }

                root.getChildren().add(rectangle);
            }
        }


        AtomicReference<KeyCode> lastPressedKey
                = new AtomicReference<>(RIGHT);
        
        Thread move = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(140);
                    move(field, snake, lastPressedKey);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        move.start();


        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();

        root.requestFocus();
        root.setOnKeyPressed(event -> {
            lastPressedKey.set(event.getCode());
        });
    }

    private void move(Rectangle[][] field, SnakeElement snake, AtomicReference<KeyCode> lastPressedKey) {
        field[snake.x()][snake.y()].setFill(Color.LIGHTGREEN);

        switch (lastPressedKey.get()) {
            case RIGHT:
                snake.setX((snake.x() + 1) % 24);
                break;
            case LEFT:
                snake.setX((snake.x() - 1 + 24) % 24);
                break;
            case UP:
                snake.setY((snake.y() - 1 + 16) % 16);
                break;
            case DOWN:
                snake.setY((snake.y() + 1) % 16);
                break;
            default:
                break;
        }
        field[snake.x()][snake.y()].setFill(Color.RED);
    }

    public static void main(String[] args) {
        launch();
    }
}
