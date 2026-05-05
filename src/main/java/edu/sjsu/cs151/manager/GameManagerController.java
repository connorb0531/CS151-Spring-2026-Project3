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

        Label subtitle = new Label("Sign in to continue");
        subtitle.setStyle("-fx-font-size: 14; -fx-text-fill: #888888;");

        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #555555;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #dddddd;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 13;"
        );

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #555555;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #dddddd;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 13;"
        );

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13;");

        Button loginButton = new Button("Log In");
        loginButton.setStyle(
            "-fx-background-color: #185FA5;" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: #E6F1FB;" +
            "-fx-font-size: 14;" +
            "-fx-padding: 10 0 10 0;" +
            "-fx-pref-width: 300;"
        );

        Button createButton = new Button("Create Account");
        createButton.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #dddddd;" +
            "-fx-border-radius: 8;" +
            "-fx-font-size: 13;" +
            "-fx-padding: 9 0 9 0;" +
            "-fx-pref-width: 300;"
        );

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter a username and password.");
                return;
            }
            boolean success = login(username, password);
            if (!success) {
                errorLabel.setText("Invalid username or password.");
            }
        });

        createButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter a username and password.");
                return;
            }
            boolean success = createAccount(username, password);
            if (!success) {
                errorLabel.setText("Username already taken.");
            }
        });

        VBox card = new VBox(14,
            title, subtitle,
            usernameLabel, usernameField,
            passwordLabel, passwordField,
            errorLabel,
            loginButton, createButton
        );
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 40;" +
            "-fx-pref-width: 380;"
        );

        VBox wrapper = new VBox(card);
        wrapper.setStyle(
            "-fx-background-color: #f2f2f2;" +
            "-fx-alignment: center;" +
            "-fx-padding: 80;"
        );

        return wrapper;
    }

    public void showMainMenu() {
        rootLayout = new BorderPane();

        Label userLabel = new Label(currentUser.getUsername());
        userLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #888888;");

        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setStyle(
            "-fx-background-color: #2a2a2a;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #444444;" +
            "-fx-border-radius: 6;" +
            "-fx-text-fill: #4a90d9;" +
            "-fx-font-size: 12;" +
            "-fx-padding: 5 14 5 14;"
        );

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(
            "-fx-background-color: #2a2a2a;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #444444;" +
            "-fx-border-radius: 6;" +
            "-fx-text-fill: #999999;" +
            "-fx-font-size: 12;" +
            "-fx-padding: 5 14 5 14;"
        );

        mainMenuBtn.setOnAction(e -> showMainMenu());
        logoutButton.setOnAction(e -> {
            currentUser = null;
            showLoginScreen();
        });

        BorderPane toolbar = new BorderPane();
        toolbar.setLeft(userLabel);
        toolbar.setRight(new HBox(8, mainMenuBtn, logoutButton));
        toolbar.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 12 20 12 20;");

        BorderPane centerWrapper = new BorderPane();
        centerWrapper.setCenter(buildMainMenu());

        rootLayout.setTop(toolbar);
        rootLayout.setCenter(centerWrapper);

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