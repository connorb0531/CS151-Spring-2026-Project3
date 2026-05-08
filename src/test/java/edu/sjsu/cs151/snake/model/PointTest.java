package edu.sjsu.cs151.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void translateMovesPointCorrectly() {
        Point p = new Point(5, 5);
        Point moved = p.translate(Direction.RIGHT);
        assertEquals(new Point(6, 5), moved);
    }

    @Test
    void equalPointsAreEqual() {
        Point a = new Point(3, 4);
        Point b = new Point(3, 4);
        assertEquals(a, b);
    }
}
