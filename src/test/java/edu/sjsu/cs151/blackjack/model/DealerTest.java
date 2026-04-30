package edu.sjsu.cs151.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DealerTest {
    private Dealer dealer;

    @BeforeEach
    public void setup() {
        dealer = new Dealer();
    }

    @Test
    public void shouldHit16() {
        dealer.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
        dealer.addCard(new Card(Suit.CLUBS, Rank.SIX));

        assertTrue(dealer.shouldHit());
    }

    @Test
    public void shouldHit17() {
        dealer.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
        dealer.addCard(new Card(Suit.CLUBS, Rank.SEVEN));

        assertFalse(dealer.shouldHit());
    }

}
