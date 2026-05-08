package edu.sjsu.cs151.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void initialPositionIsWithinBounds() {
        Food food = new Food(47, 32);
        Point pos = food.getPosition();
        assertTrue(pos.x >= 0 && pos.x < 47);
        assertTrue(pos.y >= 0 && pos.y < 32);
    }

    @Test
    void respawnChangesPosition() {
        Food food = new Food(47, 32);
        Point before = food.getPosition();
        boolean changed = false;
        for (int i = 0; i < 20; i++) {
            food.respawn();
            if (!food.getPosition().equals(before)) {
                changed = true;
                break;
            }
        }
        assertTrue(changed);
    }
}
