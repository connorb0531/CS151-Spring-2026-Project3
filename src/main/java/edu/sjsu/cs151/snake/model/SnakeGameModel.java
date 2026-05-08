package edu.sjsu.cs151.snake.model;

public class SnakeGameModel {

    public static final int TILE = 20;

    public static final int BOARD_WIDTH = 940 / TILE;
    public static final int BOARD_HEIGHT = 640 / TILE;

    private Snake snake;
    private Food food;
    private int score;
    private SnakeGameState state;

    public SnakeGameModel() { 
        snake = new Snake(BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
        food = new Food(BOARD_WIDTH, BOARD_HEIGHT);
        score = 1000;
        state = SnakeGameState.RUNNING;
    }

    //game tick concept
    public void update() {
        if (state != SnakeGameState.RUNNING) {
            return;
        }


        boolean ateFood = snake.collidesWith(food.getPosition());
        snake.move(ateFood);


        if (ateFood) {
            score = score + 100;
            food.respawn();
        }

        if (isOutOfBounds(snake.getHead()) || snake.bodyContains(snake.getHead())) {
            state = SnakeGameState.GAME_OVER;
        }
    }


    private boolean isOutOfBounds(Point p) {
        return p.x < 0 || p.x >= BOARD_WIDTH || p.y < 0 || p.y >= BOARD_HEIGHT;

    }

    public void changeDirection(Direction d) {
        snake.setDirection(d);
    }

    public void togglePause() {
        if (state == SnakeGameState.RUNNING) state = SnakeGameState.PAUSED;
        else if (state == SnakeGameState.PAUSED) state = SnakeGameState.RUNNING;
    }

    public Snake getSnake() { 
        return snake; 
    }

    public Food getFood() { 
        return food;
    }

    public int getScore() {
        return score;
    }

    public SnakeGameState getState() {
        return state;
    }

}
