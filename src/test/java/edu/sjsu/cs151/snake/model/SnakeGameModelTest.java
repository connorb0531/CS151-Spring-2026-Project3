package edu.sjsu.cs151.snake.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class SnakeGameModelTest {

    private SnakeGameModel model;

    @BeforeEach
    void setUp() {
        model = new SnakeGameModel();
    }

    @Test
    void initialScoreIsZero() {
        assertEquals(1000, model.getScore());
    }

    @Test
    void initialStateIsRunning() {
        assertEquals(SnakeGameState.RUNNING, model.getState());
    }

    @Test
    void togglePausePausesGame() {
        model.togglePause();
        assertEquals(SnakeGameState.PAUSED, model.getState());
    }

    @Test
    void togglePauseResumesGame() {
        model.togglePause();
        model.togglePause();
        assertEquals(SnakeGameState.RUNNING, model.getState());
    }

    @Test
    void updateDoesNotRunWhenPaused() {
        model.togglePause();
        Point headBefore = model.getSnake().getHead();
        model.update();
        assertEquals(headBefore, model.getSnake().getHead());
    }
}
