package edu.sjsu.cs151.snake.controller;

import edu.sjsu.cs151.snake.model.Direction;
import edu.sjsu.cs151.snake.model.SnakeGameModel;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;


public class SnakeGameController {
    
    @FXML private Canvas gameCanvas; 

    private SnakeGameModel model;
    private AnimationTimer gameLoop;

    private static final long TICK_NS = 10_000_000L;

    private long lastUpdate = 0;

    @FXML
    public void initialize() {
        model = new SnakeGameModel();
        startGameLoop();
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= TICK_NS) {
                    model.update();
                    render();
                    lastUpdate = now;
                }
            }
        };

        gameLoop.start();
    }


    @FXML
    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case UP -> model.changeDirection(Direction.UP);
            case DOWN -> model.changeDirection(Direction.DOWN);
            case LEFT -> model.changeDirection(Direction.LEFT);
            case RIGHT -> model.changeDirection(Direction.RIGHT);

            case ESCAPE -> model.togglePause();

            default    -> {}            

        }
    }

  
    private void render() {

    }



}
