package edu.sjsu.cs151.snake.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SnakeMenuController {

    public static Parent getGameView() throws IOException { //@chelsea requested getGameView() static method
    FXMLLoader loader = new FXMLLoader(
        SnakeMenuController.class.getResource("/edu/sjsu/cs151/snake/view/fxml/snake-game.fxml")
    );

    return loader.load();
}
    
}
