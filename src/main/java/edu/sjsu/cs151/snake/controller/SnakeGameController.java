package edu.sjsu.cs151.snake.controller;

import edu.sjsu.cs151.snake.model.SnakeGameModel;
import edu.sjsu.cs151.snake.model.SnakeGameState;

import java.io.IOException;

import edu.sjsu.cs151.snake.model.Direction;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class SnakeGameController {
    
    @FXML private Canvas gameCanvas;


    private SnakeGameModel model;
    private AnimationTimer gameLoop;
    private Runnable onGameOver;
    private Image currentFoodImage;
    private MediaPlayer musicPlayer;

    private static final String[] FOOD_IMAGES = {
        "/edu/sjsu/cs151/snake/view/images/atretochoana.png",
        "/edu/sjsu/cs151/snake/view/images/centipede.png",
        "/edu/sjsu/cs151/snake/view/images/pipidae.png",
        "/edu/sjsu/cs151/snake/view/images/pyxicephalidae.png",
        "/edu/sjsu/cs151/snake/view/images/blue-jay.png",
        "/edu/sjsu/cs151/snake/view/images/mouse.png",
        "/edu/sjsu/cs151/snake/view/images/eggs.png",
        "/edu/sjsu/cs151/snake/view/images/rabbit.png"
    };

    private static final int TILE = SnakeGameModel.TILE;

    private static final long TICK_NS = 150_000_000L;

    private long lastUpdate = 0;

    public void setOnGameOver(Runnable callback) {
        this.onGameOver = callback;
    }

    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }

    private void startMusic() {
        try {
            java.net.URI uri = new java.io.File("src/main/resources/music/magnetic.mp3").toURI();
            javafx.scene.media.Media media = new javafx.scene.media.Media(uri.toString());

            musicPlayer = new MediaPlayer(media);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.setVolume(0.5);
            musicPlayer.play();
        } catch (Exception e) {
            System.out.println("Could not load music: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        model = new SnakeGameModel();
        pickNewFoodImage();
        startGameLoop();
        startMusic();

        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus(); 
    }

    private void pickNewFoodImage() {
        String path = FOOD_IMAGES[new java.util.Random().nextInt(FOOD_IMAGES.length)];
        currentFoodImage = new Image(getClass().getResourceAsStream(path));
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= TICK_NS) {
                    int scoreBefore = model.getScore();
                    model.update();

                    if (model.getScore() != scoreBefore) {
                        pickNewFoodImage();
                    }

                    render();
                    lastUpdate = now;
                }
            }
        };

        gameLoop.start();
    }


    public static javafx.util.Pair<Parent, SnakeGameController> getGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            SnakeGameController.class.getResource("/edu/sjsu/cs151/snake/view/fxml/snake-game.fxml")
        );

        Parent view = loader.load();
        SnakeGameController controller = loader.getController();
        return new javafx.util.Pair<>(view, controller);
    }

    public SnakeGameModel getModel() {
    return model;
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
                pickNewFoodImage();
                gameLoop.stop();
                if (musicPlayer != null) musicPlayer.stop();
                startGameLoop();
                
                startMusic();
            }

            default -> {}            

        }
    }

  
    public int getScore() { return model.getScore(); }

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

            javafx.scene.text.Font bigFont = javafx.scene.text.Font.font("Verdana", 40);
            javafx.scene.text.Font smallFont = javafx.scene.text.Font.font("Verdana", 20);

            gc.setFont(bigFont);
            javafx.scene.text.Text bigText = new javafx.scene.text.Text("GAME OVER");
            bigText.setFont(bigFont);
            double bigW = bigText.getLayoutBounds().getWidth();

            gc.setFill(Color.PINK);
            gc.fillText("GAME OVER", w / 2 - bigW / 2, h / 2 - 10); 

            String scoreStr = "Score: " + model.getScore() + "  |  Press R to restart";
            javafx.scene.text.Text smallText = new javafx.scene.text.Text(scoreStr);
            smallText.setFont(smallFont);
            double smallW = smallText.getLayoutBounds().getWidth();

            gc.setFill(Color.WHITE);
            gc.setFont(smallFont);
            gc.fillText(scoreStr, w / 2 - smallW / 2, h / 2 + 25);

            
            gameLoop.stop();
            if (onGameOver != null) {
                onGameOver.run();
                onGameOver = null;
            }

            return;
        }

        if (model.getState() == SnakeGameState.PAUSED) {
            gc.setFill(Color.color(0, 0, 0, 0.6));

            gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 36));
            gc.fillText("PAUSED", w / 2 - 70, h / 2 - 10);

            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font("Verdana", 14));

            gc.fillText("Press ESCAPE to resume", w / 2 - 105, h / 2 + 20);
            return;
        }


        if (currentFoodImage != null) {
            gc.drawImage(
                currentFoodImage,
                model.getFood().getPosition().x * TILE,
                model.getFood().getPosition().y * TILE,
                TILE, TILE
            );
        }

        var body = model.getSnake().getBody();
        for (int i = 0; i < body.size(); i++) {
            var seg = body.get(i);
            gc.setFill(i % 2 == 0 ? Color.FORESTGREEN : Color.web("#4a8a3a"));
            gc.fillRect(seg.x * TILE, seg.y * TILE, TILE -1, TILE -1);
        }

    }

}
