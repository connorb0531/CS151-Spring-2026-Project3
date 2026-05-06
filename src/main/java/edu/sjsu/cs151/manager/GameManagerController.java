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

        return card;
    }

    public void showMainMenu() {
        rootLayout = new BorderPane();

        BorderPane centerWrapper = new BorderPane();
        centerWrapper.setCenter(buildMainMenu());

        rootLayout.setTop(buildToolbar());
        rootLayout.setCenter(centerWrapper);

        stage.setScene(new Scene(rootLayout, 950, 650));
        stage.setTitle("Game Manager");
        stage.show();
    }

    private BorderPane buildToolbar() {
        return buildToolbar(null);
    }

    private BorderPane buildToolbar(Runnable backAction) {
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
        logoutButton.setOnAction(e -> logout());

        HBox rightButtons = new HBox(8, mainMenuBtn, logoutButton);

        if (backAction != null) {
            Button goBackBtn = new Button("Go Back");
            goBackBtn.setStyle(
                "-fx-background-color: #2a2a2a;" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: #444444;" +
                "-fx-border-radius: 6;" +
                "-fx-text-fill: #cccccc;" +
                "-fx-font-size: 12;" +
                "-fx-padding: 5 14 5 14;"
            );
            goBackBtn.setOnAction(e -> backAction.run());
            rightButtons.getChildren().add(0, goBackBtn);
        }

        BorderPane toolbar = new BorderPane();
        toolbar.setLeft(userLabel);
        toolbar.setRight(rightButtons);
        toolbar.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 12 20 12 20;");
        return toolbar;
    }

    public void navigateToMainMenu() {
        showMainMenu();
    }

    public void logout() {
        currentUser = null;
        showLoginScreen();
    }

    public void launchBlackjack(String fxmlPath) {
        boolean isGameScreen = fxmlPath != null;
        String path = isGameScreen ? fxmlPath : "/edu/sjsu/cs151/blackjack/view/fxml/blackjack-menu.fxml";
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                edu.sjsu.cs151.blackjack.controller.BlackjackMenuController.class.getResource(path)
            );
            javafx.scene.Parent view = loader.load();
            Object controller = loader.getController();
            if (controller instanceof edu.sjsu.cs151.blackjack.controller.BlackjackMenuController) {
                ((edu.sjsu.cs151.blackjack.controller.BlackjackMenuController) controller).setGameManagerController(this);
            }
            BorderPane layout = new BorderPane();
            Runnable backAction = isGameScreen ? () -> launchBlackjack(null) : null;
            layout.setTop(buildToolbar(backAction));
            layout.setCenter(view);
            stage.setScene(new Scene(layout, 950, 650));
            stage.setTitle("Blackjack");
        } catch (Exception e) {
            System.out.println("Failed to load blackjack: " + e.getMessage());
        }
    }

    public void launchSnakeGame(String username) {
        try {
            javafx.scene.Parent view = edu.sjsu.cs151.snake.controller.SnakeMenuController.getGameView();
            BorderPane layout = new BorderPane();
            layout.setTop(buildToolbar(() -> launchSnakeGame(null)));
            layout.setCenter(view);
            stage.setScene(new Scene(layout, 950, 650));
            stage.setTitle("Snake");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox buildMainMenu() {
        Label blackjackHeading = new Label("BLACKJACK");
        blackjackHeading.setStyle("-fx-font-size: 11; -fx-text-fill: #111111;");

        VBox blackjackScores = new VBox(4, blackjackHeading);
        java.util.List<HighScoreEntry> topBlackjackScores = scoreManager.getTopBlackjack(5);
        for (int i = 0; i < topBlackjackScores.size(); i++) {
            HighScoreEntry entry = topBlackjackScores.get(i);

            Label nameLabel = new Label(entry.getUsername());
            nameLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #111111;");

            Label scoreLabel = new Label(String.valueOf(entry.getScore()));
            if (i == 0) {
                scoreLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #185FA5;");
            } else {
                scoreLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #666666;");
            }

            BorderPane row = new BorderPane();
            row.setLeft(nameLabel);
            row.setRight(scoreLabel);
            if (i == 0) {
                row.setStyle("-fx-background-color: #f0f5ff; -fx-background-radius: 6; -fx-padding: 6 10 6 10;");
            } else {
                row.setStyle("-fx-padding: 6 10 6 10;");
            }

            blackjackScores.getChildren().add(row);
        }

        Label snakeHeading = new Label("SNAKE");
        snakeHeading.setStyle("-fx-font-size: 11; -fx-text-fill: #111111;");

        VBox snakeScores = new VBox(4, snakeHeading);
        java.util.List<HighScoreEntry> topSnakeScores = scoreManager.getTopSnake(5);
        for (int i = 0; i < topSnakeScores.size(); i++) {
            HighScoreEntry entry = topSnakeScores.get(i);

            Label nameLabel = new Label(entry.getUsername());
            nameLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #111111;");

            Label scoreLabel = new Label(String.valueOf(entry.getScore()));
            if (i == 0) {
                scoreLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #185FA5;");
            } else {
                scoreLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #666666;");
            }

            BorderPane row = new BorderPane();
            row.setLeft(nameLabel);
            row.setRight(scoreLabel);
            if (i == 0) {
                row.setStyle("-fx-background-color: #f0f5ff; -fx-background-radius: 6; -fx-padding: 6 10 6 10;");
            } else {
                row.setStyle("-fx-padding: 6 10 6 10;");
            }

            snakeScores.getChildren().add(row);
        }

        VBox scoresPanel = new VBox(20, blackjackScores, snakeScores);
        scoresPanel.setStyle(
            "-fx-background-color: #f8f8f8;" +
            "-fx-border-color: #e8e8e8;" +
            "-fx-padding: 24 16 24 16;" +
            "-fx-pref-width: 240;" +
            "-fx-min-height: 600;"
        );

        Label blackjackTitle = new Label("Blackjack");
        blackjackTitle.setStyle("-fx-font-size: 15; -fx-text-fill: #111111;");

        Button blackjackButton = new Button("Play now");
        blackjackButton.setStyle(
            "-fx-background-color: #185FA5;" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: #E6F1FB;" +
            "-fx-font-size: 13;" +
            "-fx-padding: 9 0 9 0;" +
            "-fx-pref-width: 180;"
        );
        blackjackButton.setOnAction(e -> launchBlackjack(null));

        VBox blackjackCard = new VBox(12, blackjackTitle, blackjackButton);
        blackjackCard.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 10;" +
            "-fx-padding: 20;" +
            "-fx-pref-width: 200;"
        );

        Label snakeTitle = new Label("Snake");
        snakeTitle.setStyle("-fx-font-size: 15; -fx-text-fill: #111111;");

        Button snakeButton = new Button("Play now");
        snakeButton.setStyle(
            "-fx-background-color: #3B6D11;" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: #EAF3DE;" +
            "-fx-font-size: 13;" +
            "-fx-padding: 9 0 9 0;" +
            "-fx-pref-width: 180;"
        );
        snakeButton.setOnAction(e -> launchSnakeGame(currentUser.getUsername()));

        VBox snakeCard = new VBox(12, snakeTitle, snakeButton);
        snakeCard.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 10;" +
            "-fx-padding: 20;" +
            "-fx-pref-width: 200;"
        );

        Label futureLabelOne = new Label("Coming soon");
        futureLabelOne.setStyle("-fx-font-size: 15; -fx-text-fill: #aaaaaa;");

        Button futureButtonOne = new Button("Unavailable");
        futureButtonOne.setStyle(
            "-fx-background-color: #eeeeee;" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: #bbbbbb;" +
            "-fx-font-size: 13;" +
            "-fx-padding: 9 0 9 0;" +
            "-fx-pref-width: 180;"
        );
        futureButtonOne.setDisable(true);

        VBox futureCardOne = new VBox(12, futureLabelOne, futureButtonOne);
        futureCardOne.setStyle(
            "-fx-background-color: #fafafa;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #eeeeee;" +
            "-fx-border-radius: 10;" +
            "-fx-padding: 20;" +
            "-fx-pref-width: 200;"
        );

        Label futureLabelTwo = new Label("Coming soon");
        futureLabelTwo.setStyle("-fx-font-size: 15; -fx-text-fill: #aaaaaa;");

        Button futureButtonTwo = new Button("Unavailable");
        futureButtonTwo.setStyle(
            "-fx-background-color: #eeeeee;" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: #bbbbbb;" +
            "-fx-font-size: 13;" +
            "-fx-padding: 9 0 9 0;" +
            "-fx-pref-width: 180;"
        );
        futureButtonTwo.setDisable(true);

        VBox futureCardTwo = new VBox(12, futureLabelTwo, futureButtonTwo);
        futureCardTwo.setStyle(
            "-fx-background-color: #fafafa;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #eeeeee;" +
            "-fx-border-radius: 10;" +
            "-fx-padding: 20;" +
            "-fx-pref-width: 200;"
        );

        HBox cardRowOne = new HBox(16, blackjackCard, snakeCard);
        HBox cardRowTwo = new HBox(16, futureCardOne, futureCardTwo);

        VBox gamesPanel = new VBox(24, cardRowOne, cardRowTwo);
        gamesPanel.setStyle(
            "-fx-background-color: #f2f2f2;" +
            "-fx-padding: 36;"
        );

        return new HBox(scoresPanel, gamesPanel);
    }

}