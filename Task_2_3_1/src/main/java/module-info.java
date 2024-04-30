module fxlab.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens fxlab.snake to javafx.fxml;
    exports fxlab.snake;
}
