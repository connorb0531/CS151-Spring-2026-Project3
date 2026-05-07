package edu.sjsu.cs151.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sjsu.cs151.blackjack.model.BlackjackGame.RoundPhase;
import edu.sjsu.cs151.blackjack.model.BlackjackGame.RoundResult;


public class BlackjackGameSerializationTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void roundTrip_preservesBettingState() throws Exception {
        Player human = new Player("Yuno Miles");
        BlackjackGame original = new BlackjackGame(human);
        original.playerBet(25);

        String json = mapper.writeValueAsString(original);
        assertTrue(json.contains("Yuno Miles"));
        assertTrue(json.contains("BETTING"));

        BlackjackGame restored = mapper.readValue(json, BlackjackGame.class);

        assertNotNull(restored.getHumanPlayer());
        assertEquals("Yuno Miles", restored.getHumanPlayer().getName());
        assertEquals(975, restored.getHumanPlayer().getBankRoll());
        assertEquals(25, restored.getHumanPlayer().getCurrentBet());
        assertEquals(RoundPhase.BETTING, restored.getRoundPhase());
        assertEquals(RoundResult.NONE, restored.getHumanRoundResult());
        assertEquals(false, restored.isRoundActive());
    }

    @Test
    public void roundTrip_preservesCpuPlayersAndDeckSize() throws Exception {
        BlackjackGame original = new BlackjackGame(new Player("Pat"));
        original.playerBet(10);
        original.startRound();

        int deckSizeBefore = original.getDeck().size();
        int cpuOneBet = original.getCpuOne().getCurrentBet();
        int cpuTwoBet = original.getCpuTwo().getCurrentBet();

        String json = mapper.writeValueAsString(original);
        BlackjackGame restored = mapper.readValue(json, BlackjackGame.class);

        assertEquals(deckSizeBefore, restored.getDeck().size());
        assertEquals(cpuOneBet, restored.getCpuOne().getCurrentBet());
        assertEquals(cpuTwoBet, restored.getCpuTwo().getCurrentBet());
        assertEquals(original.getHumanPlayer().getHand().size(), restored.getHumanPlayer().getHand().size());
        assertEquals(original.getDealer().getHand().size(), restored.getDealer().getHand().size());
        assertEquals(original.getRoundPhase(), restored.getRoundPhase());
        assertEquals(original.isRoundActive(), restored.isRoundActive());
    }
}
