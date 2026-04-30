package edu.sjsu.cs151.blackjack.model;

public class Player extends Participant {
    private String name;
    private int bankRoll;
    private int currentBet;
    private final int INITIAL_BANKROLL = 1000;

    public Player(String name) {
        super();
        this.name = name;
        this.bankRoll = INITIAL_BANKROLL;
    }

    public Player() {
        super();
    }

    public void placeBet(int amount) {
        if (amount <= 0) {
        throw new IllegalArgumentException("Bet must be greater than 0");
        }
        if (amount > bankRoll) {
            throw new IllegalArgumentException("Bet cannot exceed bankroll aka you're too poor");
        }
        
        currentBet = amount;
        bankRoll -= currentBet;
    }

    public void winBet() {
        bankRoll += 2 * currentBet;
        currentBet = 0;
    }

    public void pushBet() {
        bankRoll += currentBet;
        currentBet = 0;
    }

    public void resetBankRoll() {
        bankRoll = INITIAL_BANKROLL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBankRoll() {
        return bankRoll;
    }

    public void setBankRoll(int bankRoll) {
        this.bankRoll = bankRoll;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }
}
