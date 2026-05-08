package edu.sjsu.cs151.snake.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DirectionTest {
    @Test
    void oppositeDirectionsDetected() {
        assertTrue(Direction.UP.isOpposite(Direction.DOWN));
        assertTrue(Direction.LEFT.isOpposite(Direction.RIGHT));
    }

    @Test
    void nonOppositeDirectionsReturnFalse() {
        assertFalse(Direction.UP.isOpposite(Direction.LEFT));
        assertFalse(Direction.DOWN.isOpposite(Direction.RIGHT));
    }

}
