package edu.sjsu.cs151.snake.controller;

import edu.sjsu.cs151.snake.model.Direction;
import edu.sjsu.cs151.snake.model.SnakeGameModel;
import edu.sjsu.cs151.snake.model.SnakeGameState;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;


public class SnakeGameController {
    
    @FXML private Canvas gameCanvas; 

    private SnakeGameModel model;
    private AnimationTimer gameLoop;

    private static final int TILE = 20;

    private static final long TICK_NS = 150_000_000L;

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

            default -> {}            

        }
    }

  
    private void render() {

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(),gameCanvas.getHeight());

        if (model.getState() == SnakeGameState.GAME_OVER) {
            gc.setFill(Color.PINK);
            gc.fillText("GAME OVER  Score: " + model.getScore(), 60, 200);
            gameLoop.stop();
            return;
        }

        if (model.getState() == SnakeGameState.PAUSED) {
            gc.setFill(Color.color(0, 0, 0, 0.55));

            gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 36));
            gc.fillText("PAUSED", 130, 185);

            gc.setFill(Color.LIGHTGRAY);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 14));

            return;
        }

        //make the food
        gc.setFill(Color.PINK);

        gc.fillOval(
            model.getFood().getPosition().x * TILE,
            model.getFood().getPosition().y * TILE,
            TILE, TILE
        );

        gc.setFill(Color.FORESTGREEN);
        for (var seg : model.getSnake().getBody()) {
            gc.fillRect(seg.x * TILE, seg.y * TILE, TILE -1, TILE -1);
        }

    }



}
