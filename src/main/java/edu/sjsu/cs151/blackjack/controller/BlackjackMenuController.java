package edu.sjsu.cs151.blackjack.controller;

import edu.sjsu.cs151.blackjack.model.BlackjackGame;
import edu.sjsu.cs151.blackjack.model.BlackjackGameSave;
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
        startButton.setOnAction(event -> openNewGameScreen());
        loadButton.setOnAction(event -> loadGame());
    }

    private void openNewGameScreen() {
        try {
            Parent gameScreen = FXMLLoader.load(
                    getClass().getResource("/edu/sjsu/cs151/blackjack/view/fxml/blackjack-game.fxml")
            );

            startButton.getScene().setRoot(gameScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openLoadedGameScreen(BlackjackGame loadedGame) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/edu/sjsu/cs151/blackjack/view/fxml/blackjack-game.fxml")
            );

            Parent gameScreen = loader.load();

            BlackjackGameController controller = loader.getController();
            controller.setGame(loadedGame);

            loadButton.getScene().setRoot(gameScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() {
        String saveStateString = saveStateInput.getText();

        if (saveStateString == null || saveStateString.trim().isEmpty()) {
            System.out.println("Please enter a saveStateString.");
            return;
        }

        try {
            BlackjackGame loadedGame = BlackjackGameSave.load(saveStateString.trim());
            openLoadedGameScreen(loadedGame);

        } catch (Exception e) {
            System.out.println("Could not load game.");
            e.printStackTrace();
        }
    }
}
