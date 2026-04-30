package edu.sjsu.cs151.blackjack.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BlackjackGameController {

    @FXML
    private Label turnLabel;

    @FXML
    private Label dealerMoneyLabel;

    @FXML
    private Label playerOneMoneyLabel;

    @FXML
    private Label playerTwoMoneyLabel;

    @FXML
    private Label humanMoneyLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private HBox dealerCardsBox;

    @FXML
    private HBox playerOneCardsBox;

    @FXML
    private HBox playerTwoCardsBox;

    @FXML
    private HBox humanCardsBox;

    @FXML
    private Button hitButton;

    @FXML
    private Button standButton;

    @FXML
    public void initialize() {
        showStartingGameDisplay();

        hitButton.setOnAction(event -> hit());
        standButton.setOnAction(event -> stand());
    }

    private void showStartingGameDisplay() {
        turnLabel.setText("Current Turn: You");

        dealerMoneyLabel.setText("Money: $1000");
        playerOneMoneyLabel.setText("Money: $1000");
        playerTwoMoneyLabel.setText("Money: $1000");
        humanMoneyLabel.setText("Money: $1000");

        statusLabel.setText("Game started. Your turn.");

        dealerCardsBox.getChildren().clear();
        playerOneCardsBox.getChildren().clear();
        playerTwoCardsBox.getChildren().clear();
        humanCardsBox.getChildren().clear();

        dealerCardsBox.getChildren().add(new Label("[10]"));
        dealerCardsBox.getChildren().add(new Label("[?]"));

        playerOneCardsBox.getChildren().add(new Label("[5]"));
        playerOneCardsBox.getChildren().add(new Label("[9]"));

        playerTwoCardsBox.getChildren().add(new Label("[7]"));
        playerTwoCardsBox.getChildren().add(new Label("[8]"));

        humanCardsBox.getChildren().add(new Label("[8]"));
        humanCardsBox.getChildren().add(new Label("[K]"));
    }

    private void hit() {
        statusLabel.setText("You clicked Hit.");
        humanCardsBox.getChildren().add(new Label("[3]"));
    }

    private void stand() {
        statusLabel.setText("You stand. Player 1's turn.");
        turnLabel.setText("Current Turn: Player 1");
    }
}
