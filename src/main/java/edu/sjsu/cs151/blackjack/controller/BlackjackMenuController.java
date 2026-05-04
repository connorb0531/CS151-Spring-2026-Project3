package edu.sjsu.cs151.blackjack.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class BlackjackMenuController {

    @FXML
    private Button startButton;

    @FXML
    private Button loadButton;

    @FXML
    private TextArea saveStateInput;

    public static Parent getGameView() {
        try {
            return FXMLLoader.load(
                    BlackjackMenuController.class.getResource(
                            "/edu/sjsu/cs151/blackjack/view/fxml/blackjack-menu.fxml"
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void initialize() {
        startButton.setOnAction(event -> openGameScreen());
        loadButton.setOnAction(event -> loadGame());
    }

    private void openGameScreen() {
        try {
            Parent gameScreen = FXMLLoader.load(
                    getClass().getResource("/edu/sjsu/cs151/blackjack/view/fxml/blackjack-game.fxml")
            );

            startButton.getScene().setRoot(gameScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() {
        String saveStateString = saveStateInput.getText();

        System.out.println("Loading game with saveStateString:");
        System.out.println(saveStateString);

        openGameScreen();
    }
}