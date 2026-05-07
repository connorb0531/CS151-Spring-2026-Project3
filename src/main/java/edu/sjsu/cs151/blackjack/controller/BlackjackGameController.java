package edu.sjsu.cs151.blackjack.controller;

import edu.sjsu.cs151.blackjack.model.BlackjackGame;
import edu.sjsu.cs151.blackjack.model.Card;
import edu.sjsu.cs151.blackjack.model.Participant;
import edu.sjsu.cs151.blackjack.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BlackjackGameController {

    @FXML private Label turnLabel;
    @FXML private Label dealerMoneyLabel;
    @FXML private Label playerOneMoneyLabel;
    @FXML private Label playerTwoMoneyLabel;
    @FXML private Label humanMoneyLabel;
    @FXML private Label statusLabel;

    @FXML private HBox dealerCardsBox;
    @FXML private HBox playerOneCardsBox;
    @FXML private HBox playerTwoCardsBox;
    @FXML private HBox humanCardsBox;

    @FXML private VBox dealerBox;
    @FXML private VBox playerOneBox;
    @FXML private VBox playerTwoBox;
    @FXML private VBox humanBox;

    @FXML private TextField betField;
    @FXML private Button betButton;
    @FXML private Button hitButton;
    @FXML private Button standButton;

    private BlackjackGame game;
    private MediaPlayer musicPlayer;

    private void startMusic() {
        try {
            java.net.URL resource = getClass().getResource("/edu/sjsu/cs151/blackjack/music/Strategy.mp3");
            Media media = new Media(resource.toString());
            musicPlayer = new MediaPlayer(media);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.setVolume(0.5);
            musicPlayer.play();
        } catch (Exception e) {
            System.out.println("Could not load blackjack music: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        Player human = new Player("You");
        game = new BlackjackGame(human);

        updateMoney();
        disableGameButtons();

        betButton.setOnAction(event -> placeBet());
        hitButton.setOnAction(event -> hit());
        standButton.setOnAction(event -> stand());

        startMusic();
    }

    private void placeBet() {
        try {
            int amount = Integer.parseInt(betField.getText());

            game.playerBet(amount);
            game.startRound();

            statusLabel.setText("Bet placed: $" + amount + ". Your turn.");
            enableGameButtons();
            updateUI();

        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid number for your bet.");
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    private void hit() {
        game.playerHit();
        statusLabel.setText("You hit.");
        updateUI();
    }

    private void stand() {
        game.playerStand();
        statusLabel.setText("You stand. CPU players and dealer are playing.");
        updateUI();
    }

    private void updateUI() {
        updateTurn();
        updateMoney();
        updateCards();
        updateHighlight();

        if (!game.isRoundActive()) {
            statusLabel.setText("Round result: " + game.getHumanRoundResult());
            disableGameButtons();
        }
    }

    private void updateTurn() {
        turnLabel.setText("Current Turn: " + game.getRoundPhase());
    }

    private void updateMoney() {
        humanMoneyLabel.setText("Money: $" + game.getHumanPlayer().getBankRoll());
        playerOneMoneyLabel.setText("Money: $" + game.getCpuOne().getBankRoll());
        playerTwoMoneyLabel.setText("Money: $" + game.getCpuTwo().getBankRoll());
        dealerMoneyLabel.setText("Dealer");
    }

    private void updateCards() {
        dealerCardsBox.getChildren().clear();
        playerOneCardsBox.getChildren().clear();
        playerTwoCardsBox.getChildren().clear();
        humanCardsBox.getChildren().clear();

        showCards(humanCardsBox, game.getHumanPlayer(), false);
        showCards(playerOneCardsBox, game.getCpuOne(), false);
        showCards(playerTwoCardsBox, game.getCpuTwo(), false);
        showCards(dealerCardsBox, game.getDealer(), game.isDealerSecondCardHidden());
    }

    private void showCards(HBox box, Participant participant, boolean hideSecondCard) {
        for (int i = 0; i < participant.getHand().size(); i++) {
            if (hideSecondCard && i == 1) {
                box.getChildren().add(new Label("[?]"));
            } else {
                Card card = participant.getHand().get(i);
                box.getChildren().add(new Label("[" + card + "]"));
            }
        }
    }

    private void updateHighlight() {
        clearHighlights();

        String phase = String.valueOf(game.getRoundPhase());

        if (phase.contains("PLAYER")) {
            highlightBox(humanBox);
        } else if (phase.contains("DEALER")) {
            highlightBox(dealerBox);
        }
    }

    private void clearHighlights() {
        dealerBox.setStyle("-fx-padding: 10;");
        playerOneBox.setStyle("-fx-padding: 10;");
        playerTwoBox.setStyle("-fx-padding: 10;");
        humanBox.setStyle("-fx-padding: 10;");
    }

    private void highlightBox(VBox box) {
        box.setStyle("-fx-border-color: green; -fx-border-width: 3; -fx-padding: 10;");
    }

    private void disableGameButtons() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }

    private void enableGameButtons() {
        hitButton.setDisable(false);
        standButton.setDisable(false);
    }
}
