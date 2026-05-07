package edu.sjsu.cs151.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class BlackjackGameSaveTest {

    @Test
    public void saveThenLoad_roundTripsBettingState(@TempDir Path tempDir) throws Exception {
        BlackjackGameSave persistence = new BlackjackGameSave(tempDir.toAbsolutePath().toString());
        BlackjackGame game = new BlackjackGame(new Player("Sam"));
        game.playerBet(30);

        String token = persistence.save(game);
        assertNotEquals("error: saved failed", token);

        BlackjackGame loaded = persistence.load(token);
        assertNotNull(loaded);
        assertEquals("Sam", loaded.getHumanPlayer().getName());
        assertEquals(970, loaded.getHumanPlayer().getBankRoll());
        assertEquals(30, loaded.getHumanPlayer().getCurrentBet());
    }
}
