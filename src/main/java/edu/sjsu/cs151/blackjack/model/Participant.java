package edu.sjsu.cs151.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant {
    private List<Card> hand;
    private int aceCount;

    public Participant() {
        this.hand = new ArrayList<>();
        this.aceCount = 0;
    }

    public void addCard(Card card) {
        if (card.getRank().isAce()) {
            aceCount++;
        }

        hand.add(card);
    }

    public void clearHand() {
        aceCount = 0;
        hand.clear();
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public boolean hasBlackjack() {
        return getHandValue() == 21;
    }

    public int getHandValue() {
        int value = 0;
        
        for (Card card : hand) {
            
            value += card.getRank().getValue();
        }

        // ace(s) default value is 11 unless it causes bust
        if (aceCount > 0 && value + 10 <= 21) {
            value += 10;
        }

        return value;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getAceCount() {
        return aceCount;
    }    
}
