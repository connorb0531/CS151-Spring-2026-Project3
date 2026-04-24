package edu.sjsu.cs151.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setup() {
        player = new Player("Bob");
    }

    @Test
    public void oneAceValue() {
        player.addCard(new Card(Suit.CLUBS, Rank.ACE));

        assertEquals(11, player.getHandValue());
    }

    @Test
    public void twoAceValue() {
        player.addCard(new Card(Suit.CLUBS, Rank.ACE));
        player.addCard(new Card(Suit.DIAMONDS, Rank.ACE));

        assertEquals(12, player.getHandValue());
    }

    @Test
    public void threeAceValue() {
        player.addCard(new Card(Suit.CLUBS, Rank.ACE));
        player.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        player.addCard(new Card(Suit.SPADES, Rank.ACE));

        assertEquals(13, player.getHandValue());
    }

    @Test
    public void fourAceValue() {
        player.addCard(new Card(Suit.CLUBS, Rank.ACE));
        player.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        player.addCard(new Card(Suit.SPADES, Rank.ACE));
        player.addCard(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(14, player.getHandValue());
    }

    @Test
    public void adjustAceBustValue() {
        player.addCard(new Card(Suit.CLUBS, Rank.ACE));
        player.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));

        assertEquals(16, player.getHandValue());

        player.addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        assertEquals(12, player.getHandValue());
    }
}
