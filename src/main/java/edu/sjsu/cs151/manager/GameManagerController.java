package edu.sjsu.cs151.manager;

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

    }

    private VBox buildLoginScreen() {
        return new VBox();
    }

    public void showMainMenu() {

    }

    private HBox buildMainMenu() {
        return new HBox();
    }

}