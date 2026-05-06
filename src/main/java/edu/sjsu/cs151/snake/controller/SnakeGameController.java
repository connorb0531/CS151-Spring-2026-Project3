package edu.sjsu.cs151.snake.controller;

import edu.sjsu.cs151.snake.model.SnakeGameModel;
import edu.sjsu.cs151.snake.model.SnakeGameState;
import edu.sjsu.cs151.snake.model.Direction;
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

        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus(); 
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

            case R -> {
                model = new SnakeGameModel();
                lastUpdate = 0;
                gameLoop.stop();
                startGameLoop();
            }

            default -> {}            

        }
    }

  
    private void render() {
        gameCanvas.requestFocus();

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        double w = gameCanvas.getWidth(), h = gameCanvas.getHeight();

        for (int x = 0; x * TILE < w; x++) {
            for (int y = 0; y * TILE < h; y++) {
                gc.setFill((x + y) % 2 == 0 ? Color.web("#1a3a1a") : Color.web("#1f451f"));
                gc.fillRect(x * TILE, y * TILE, TILE, TILE);
            }
        }

        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font("Verdana", 14));
        gc.fillText("Score: " + model.getScore(), 10, 20);


        if (model.getState() == SnakeGameState.GAME_OVER) {
    
            gc.setFill(Color.color(0, 0, 0, 0.6));
            gc.fillRect(0, 0, w, h);

            gc.setFill(Color.PINK);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 36));
            String gameover = "GAME OVER";
            gc.fillText(gameover, w / 2 - 100, h / 2 - 20); 

            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 11));
            String score = "Score: " + model.getScore() + " |  Press R to restart";
            gc.fillText(score, w / 2 - 115, h / 2 + 15);
            gameLoop.stop();
            return;
        }

        if (model.getState() == SnakeGameState.PAUSED) {
            gc.setFill(Color.color(0, 0, 0, 0.55));

            gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 36));
            gc.fillText("PAUSED", w / 2 - 70, h / 2 - 10);

            gc.setFill(Color.LIGHTGRAY);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 14));

            gc.fillText("Press ESCAPE to resume", w / 2 - 105, h / 2 + 20);
            return;
        }


        gc.setFill(Color.PINK);

        gc.fillOval(
            model.getFood().getPosition().x * TILE,
            model.getFood().getPosition().y * TILE,
            TILE, TILE
        );

        var body = model.getSnake().getBody();
        for (int i = 0; i < body.size(); i++) {
            var seg = body.get(i);
            gc.setFill(i % 2 == 0 ? Color.FORESTGREEN : Color.web("#4a8a3a"));
            gc.fillRect(seg.x * TILE, seg.y * TILE, TILE -1, TILE -1);
        }

    }

}
