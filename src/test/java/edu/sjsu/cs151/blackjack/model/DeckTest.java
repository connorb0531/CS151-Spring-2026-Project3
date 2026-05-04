package edu.sjsu.cs151.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckTest {
    private Deck deck;

    @BeforeEach
    public void setup() {
        deck = new Deck();
    }

    @Test
    public void isEmptyDeck() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }

        assertEquals(0, deck.size());
        assertTrue(deck.isEmpty());
    }

}
