package edu.sjsu.cs151.snake.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(10, 10);
    }

    @Test
    void initialBodyHasThreeSegments() {
        assertEquals(3, snake.getBody().size());
    }

    @Test
    void moveWithoutFoodKeepsBodySize() {
        int sizeBefore = snake.getBody().size();
        snake.move(false);
        assertEquals(sizeBefore, snake.getBody().size());
    }

    @Test
    void moveWithFoodGrowsBody() {
        int sizeBefore = snake.getBody().size();
        snake.move(true);
        assertEquals(sizeBefore + 1, snake.getBody().size());
    }

    @Test
    void cannotReverseDirection() {
        Direction original = snake.getDirection();
        Direction opposite = switch (original) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
        snake.setDirection(opposite);
        assertEquals(original, snake.getDirection());
    }

    @Test
    void bodyContainsReturnsFalseWhenNoSelfCollision() {
        assertFalse(snake.bodyContains(snake.getHead()));
    }
}
