package edu.sjsu.cs151.blackjack.controller;

import edu.sjsu.cs151.blackjack.model.BlackjackGame;
import edu.sjsu.cs151.blackjack.model.BlackjackGameSave;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class BlackjackMenuController {

    @FXML
    private Button startButton;

    @FXML
    private Button loadButton;

    @FXML
    private TextArea saveStateInput;

    @FXML
    private Label loadStatusLabel;

    private edu.sjsu.cs151.manager.GameManagerController gameManagerController;

    public void setGameManagerController(edu.sjsu.cs151.manager.GameManagerController gmc) {
        this.gameManagerController = gmc;
    }

    public static Parent getGameView() {
        try {
            return FXMLLoader.load(
                    BlackjackMenuController.class.getResource(
                            "/edu/sjsu/cs151/blackjack/view/fxml/blackjack-menu.fxml"
                    )
            );
        } catch (IOException e) {
            return null;
        }
    }

    @FXML
    public void initialize() {
        startButton.setOnAction(event -> openNewGameScreen());
        loadButton.setOnAction(event -> loadGame());
    }

    private void openNewGameScreen() {
        if (gameManagerController != null) {
            gameManagerController.launchBlackjack("/edu/sjsu/cs151/blackjack/view/fxml/blackjack-game.fxml");
            return;
        }

        try {
            Parent gameScreen = FXMLLoader.load(
                    getClass().getResource("/edu/sjsu/cs151/blackjack/view/fxml/blackjack-game.fxml")
            );
            startButton.getScene().setRoot(gameScreen);
        } catch (IOException e) {
            // no-op fallback when running outside the GameManagerController flow
        }
    }

    private void loadGame() {
        String saveStateString = saveStateInput.getText();
        if (saveStateString == null || saveStateString.trim().isEmpty()) {
            setLoadStatus("Please enter a save state string.");
            return;
        }

        try {
            BlackjackGameSave persistence = new BlackjackGameSave();
            BlackjackGame loadedGame = persistence.load(saveStateString.trim());
            if (loadedGame == null) {
                setLoadStatus("No save matched the given state string.");
                return;
            }
            if (gameManagerController != null) {
                gameManagerController.launchBlackjackWithGame(loadedGame);
            }
        } catch (Exception e) {
            setLoadStatus("Could not load game: " + e.getMessage());
        }
    }

    private void setLoadStatus(String message) {
        if (loadStatusLabel != null) {
            loadStatusLabel.setText(message);
        }
    }
}
