package edu.sjsu.cs151.manager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HighScoreManagerTest {

    private HighScoreManager manager;

    @BeforeEach
    public void setUp() {
        manager = new HighScoreManager();
    }

    @Test
    public void testUpdateBlackjackScoreAddsEntry() {
        manager.updateBlackjackScore("alice", 1500);
        List<HighScoreEntry> top = manager.getTopBlackjack(5);
        assertEquals(1, top.size());
        assertEquals("alice", top.get(0).getUsername());
        assertEquals(1500, top.get(0).getScore());
    }

    @Test
    public void testUpdateSnakeScoreAddsEntry() {
        manager.updateSnakeScore("alice", 200);
        List<HighScoreEntry> top = manager.getTopSnake(5);
        assertEquals(1, top.size());
        assertEquals(200, top.get(0).getScore());
    }

    @Test
    public void testUpdateBlackjackScoreReplacesOldScore() {
        manager.updateBlackjackScore("alice", 1000);
        manager.updateBlackjackScore("alice", 2000);
        List<HighScoreEntry> top = manager.getTopBlackjack(5);
        assertEquals(1, top.size());
        assertEquals(2000, top.get(0).getScore());
    }

    @Test
    public void testScoresSortedHighestFirst() {
        manager.updateBlackjackScore("alice", 500);
        manager.updateBlackjackScore("bob", 1500);
        manager.updateBlackjackScore("charlie", 1000);
        List<HighScoreEntry> top = manager.getTopBlackjack(3);
        assertEquals("bob", top.get(0).getUsername());
        assertEquals("charlie", top.get(1).getUsername());
        assertEquals("alice", top.get(2).getUsername());
    }

    @Test
    public void testTopNKeepsOnlyTopFive() {
        for (int i = 1; i <= 6; i++) {
            manager.updateBlackjackScore("user" + i, i * 100);
        }
        List<HighScoreEntry> top = manager.getTopBlackjack(5);
        assertEquals(5, top.size());
        assertEquals(600, top.get(0).getScore());
    }

    @Test
    public void testGetTopNReturnsCorrectCount() {
        manager.updateBlackjackScore("alice", 1000);
        manager.updateBlackjackScore("bob", 2000);
        List<HighScoreEntry> top = manager.getTopBlackjack(1);
        assertEquals(1, top.size());
    }

    @Test
    public void testInitialDefaultScoreAddsToBlackjack() {
        manager.initialDefaultScore("alice");
        List<HighScoreEntry> top = manager.getTopBlackjack(5);
        assertEquals(1, top.size());
        assertEquals(1000, top.get(0).getScore());
    }

    @Test
    public void testInitialDefaultScoreAddsToSnake() {
        manager.initialDefaultScore("alice");
        List<HighScoreEntry> top = manager.getTopSnake(5);
        assertEquals(1, top.size());
        assertEquals(1000, top.get(0).getScore());
    }
}
