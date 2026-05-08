package edu.sjsu.cs151.blackjack.controller;

import edu.sjsu.cs151.blackjack.model.BlackjackGame;
import edu.sjsu.cs151.blackjack.model.BlackjackGameSave;
import edu.sjsu.cs151.blackjack.model.Card;
import edu.sjsu.cs151.blackjack.model.Participant;
import edu.sjsu.cs151.blackjack.model.Player;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    @FXML private Button saveButton;
    @FXML private HBox saveStateRow;
    @FXML private TextField saveStateField;
    @FXML private Button copySaveStateButton;

    private BlackjackGame game;

    @FXML
    public void initialize() {
        Player human = new Player("You");
        game = new BlackjackGame(human);

        updateMoney();
        disableGameButtons();

        betButton.setOnAction(event -> placeBet());
        hitButton.setOnAction(event -> hit());
        standButton.setOnAction(event -> stand());
        saveButton.setOnAction(event -> saveGame());
        copySaveStateButton.setOnAction(event -> copySaveStateToClipboard());
    }

    public void setGame(BlackjackGame loadedGame) {
        this.game = loadedGame;
        saveStateField.clear();
        saveStateRow.setVisible(false);
        saveStateRow.setManaged(false);
        updateUI();
        if (game.isRoundActive()) {
            enableGameButtons();
            betButton.setDisable(true);
        } else {
            disableGameButtons();
            betButton.setDisable(false);
        }
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

    private void saveGame() {
        BlackjackGameSave persistence = new BlackjackGameSave();
        String saveStateString = persistence.save(game);

        if ("error: saved failed".equals(saveStateString)) {
            statusLabel.setText("Save failed.");
            return;
        }

        saveStateField.setText(saveStateString);
        saveStateRow.setVisible(true);
        saveStateRow.setManaged(true);
    }

    private void copySaveStateToClipboard() {
        String text = saveStateField.getText();
        if (text == null || text.isEmpty()) {
            return;
        }
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard.getSystemClipboard().setContent(content);
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
                box.getChildren().add(cardImageOrFallback("/PNG-cards/backside.png"));
            } else {
                Card card = participant.getHand().get(i);
                box.getChildren().add(cardImageOrFallback(getCardImagePath(card)));
            }
        }
    }

    private String getCardImagePath(Card card) {
        return "/PNG-cards/" + card.getRank().name() + "_" + card.getSuit().name() + ".png";
    }

    private Node cardImageOrFallback(String imagePath) {
        java.net.URL url = getClass().getResource(imagePath);
        if (url == null) {
            Label missing = new Label("?");
            missing.setMinSize(90, 130);
            missing.setAlignment(Pos.CENTER);
            return missing;
        }
        Image image = new Image(url.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90);
        imageView.setFitHeight(130);
        imageView.setPreserveRatio(false);
        return imageView;
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

    public int getBalance() { return game.getHumanPlayer().getBankRoll(); }

    private void disableGameButtons() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }

    private void enableGameButtons() {
        hitButton.setDisable(false);
        standButton.setDisable(false);
    }
}
