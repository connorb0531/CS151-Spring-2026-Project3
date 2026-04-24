package edu.sjsu.cs151.model;

public class Dealer extends Participant {
    public Dealer() {
        super();
    }

    public boolean shouldHit() {
        return (super.getHandValue() < 17);
    }

    public Card showFirstCard() {
        return super.getHand().get(0);
    }
}
