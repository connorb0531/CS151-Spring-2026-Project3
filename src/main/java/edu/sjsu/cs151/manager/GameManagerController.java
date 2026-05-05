package edu.sjsu.cs151.manager;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameManagerController {

    private UserAccountManager accountManager;
    private HighScoreManager scoreManager;
    private UserAccount currentUser;
    private Stage stage;
    private BorderPane rootLayout;

    public GameManagerController(Stage stage) {
        this.stage = stage;
        accountManager = new UserAccountManager();
        scoreManager = new HighScoreManager();
    }

    public void initialize() {
        accountManager.loadAccounts();
        scoreManager.loadScores();
        showLoginScreen();
    }

    public boolean login(String username, String password) {
        UserAccount account = accountManager.login(username, password);
        if (account != null) {
            currentUser = account;
            showMainMenu();
            return true;
        }
        return false;
    }

    public boolean createAccount(String username, String password) {
        boolean created = accountManager.createAccount(username, password);
        if (created) {
            scoreManager.initialDefaultScore(username);
            currentUser = accountManager.getAccount(username);
            showMainMenu();
            return true;
        }
        return false;
    }

    private void showLoginScreen() {
        VBox loginScreen = buildLoginScreen();
        stage.setScene(new Scene(loginScreen, 950, 650));
        stage.setTitle("Game Manager - Login");
        stage.show();
    }

    private VBox buildLoginScreen() {
        Label title = new Label("Game Manager");
        title.setStyle("-fx-font-size: 26;");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Log In");
        Button createButton = new Button("Create Account");

        loginButton.setOnAction(e -> {
            boolean success = login(usernameField.getText(), passwordField.getText());
            if (!success) {
                errorLabel.setText("Invalid username or password.");
            }
        });

        createButton.setOnAction(e -> {
            boolean success = createAccount(usernameField.getText(), passwordField.getText());
            if (!success) {
                errorLabel.setText("Username already taken.");
            }
        });

        VBox loginScreen = new VBox(10, title, usernameLabel, usernameField, passwordLabel, passwordField, errorLabel, loginButton, createButton);
        loginScreen.setStyle("-fx-padding: 40; -fx-background-color: #f2f2f2;");
        return loginScreen;
    }

    public void showMainMenu() {
        rootLayout = new BorderPane();
        rootLayout.setCenter(buildMainMenu());

        stage.setScene(new Scene(rootLayout, 950, 650));
        stage.setTitle("Game Manager");
        stage.show();
    }

    private HBox buildMainMenu() {
        Label scoresTitle = new Label("High Scores");
        VBox scoresPanel = new VBox(10, scoresTitle);

        Label gamesTitle = new Label("Choose a game");
        Button blackjackButton = new Button("Play Blackjack");
        Button snakeButton = new Button("Play Snake");

        blackjackButton.setOnAction(e -> {
            // launch blackjack
        });

        snakeButton.setOnAction(e -> {
            // launch snake
        });

        VBox gamesPanel = new VBox(10, gamesTitle, blackjackButton, snakeButton);
        return new HBox(40, scoresPanel, gamesPanel);
    }

}