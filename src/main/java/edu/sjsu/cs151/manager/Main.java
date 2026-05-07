package edu.sjsu.cs151.manager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        GameManagerController controller = new GameManagerController(primaryStage);
        controller.initialize();
    }

    public static void main(String[] args){
        launch(args);
    }

}
