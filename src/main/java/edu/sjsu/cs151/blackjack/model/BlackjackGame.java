package edu.sjsu.cs151.blackjack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BlackjackGame {
    public enum RoundPhase {
        BETTING,
        PLAYER_TURN,
        DEALER_TURN,
        ROUND_OVER
    }

    public enum RoundResult {
        NONE,
        IN_PROGRESS,
        WIN,
        LOSE,
        PUSH,
        BUST
    }

    private Dealer dealer;
    private Player humanPlayer;
    private Player cpuOne;
    private Player cpuTwo;
    private Deck deck;
    private boolean roundActive;
    private RoundPhase roundPhase;
    private RoundResult humanRoundResult;

    public BlackjackGame(Player player) {
        this.humanPlayer = player;
        this.dealer = new Dealer();
        this.cpuOne = new Player("CPU 1");
        this.cpuTwo = new Player("CPU 2");
        this.deck = new Deck();
        this.roundActive = false;
        this.roundPhase = RoundPhase.BETTING;
        this.humanRoundResult = RoundResult.NONE;
    }

    public BlackjackGame() {}

    public void startRound() {
        if (roundActive) {
            throw new IllegalStateException("Round is already in progress.");
        }
        if (humanPlayer.getCurrentBet() <= 0) {
            throw new IllegalStateException("Player must place a bet before starting the round.");
        }

        resetRound();
        cpuBet(cpuOne);
        cpuBet(cpuTwo);
        dealInitialCards();
        roundActive = true;
        roundPhase = RoundPhase.PLAYER_TURN;
        humanRoundResult = RoundResult.IN_PROGRESS;

        if (dealer.hasBlackjack()) {
            resolveInitialDealerBlackjack();
            endRound();
            return;
        }
    }

    private void dealInitialCards() {
        humanPlayer.addCard(deck.dealCard());
        cpuOne.addCard(deck.dealCard());
        cpuTwo.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        humanPlayer.addCard(deck.dealCard());
        cpuOne.addCard(deck.dealCard());
        cpuTwo.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
    }

    private void resolveInitialDealerBlackjack() {
        humanRoundResult = settleAgainstDealer(humanPlayer);
        settleAgainstDealer(cpuOne);
        settleAgainstDealer(cpuTwo);
    }

    private void dealerTurn() {
        roundPhase = RoundPhase.DEALER_TURN;

        while (dealer.shouldHit()) {
            dealer.addCard(deck.dealCard());
        }
    }

    public void playerBet(int amount) {
        if (roundActive) {
            throw new IllegalStateException("Cannot change bet during an active round");
        }

        humanPlayer.placeBet(amount);
    }

    public void playerHit() {
        ensureRoundActive();

        humanPlayer.addCard(deck.dealCard());
        if (humanPlayer.isBusted() || humanPlayer.hasBlackjack()) {
            finishRound();
            return;
        }
    }

    public void playerStand() {
        ensureRoundActive();
        finishRound();
    }

    public String saveStateString() {
        
        return null;
    }

    private void resetRound() {
        humanPlayer.clearHand();
        cpuOne.clearHand();
        cpuTwo.clearHand();
        dealer.clearHand();

        cpuOne.setCurrentBet(0);
        cpuTwo.setCurrentBet(0);

        deck = new Deck();
        deck.shuffle();
        roundActive = false;
        roundPhase = RoundPhase.BETTING;
        humanRoundResult = RoundResult.NONE;
    }

    private void playCpuTurn(Player cpuPlayer) {
        while (!cpuPlayer.isBusted() && cpuPlayer.getHandValue() < 17) {
            cpuPlayer.addCard(deck.dealCard());
        }
    }

    private void cpuBet(Player cpuPlayer) {
        if (cpuPlayer.getBankRoll() <= 0) {  
            cpuPlayer.resetBankRoll();
        }

        int betAmount = Math.max(1, cpuPlayer.getBankRoll() / 10);
        cpuPlayer.placeBet(betAmount);
    }

    private void finishRound() {
        playCpuTurn(cpuOne);
        playCpuTurn(cpuTwo);

        if (shouldDealerPlay()) {
            dealerTurn();
        }

        resolveRound();
        endRound();
    }

    private boolean shouldDealerPlay() {
        return !humanPlayer.isBusted() || !cpuOne.isBusted() || !cpuTwo.isBusted();
    }

    private void resolveRound() {
        humanRoundResult = settleAgainstDealer(humanPlayer);
        settleAgainstDealer(cpuOne);
        settleAgainstDealer(cpuTwo);
    }

    private RoundResult settleAgainstDealer(Player player) {
        if (player.getCurrentBet() <= 0) {
            return RoundResult.NONE;
        }

        if (player.isBusted()) {
            player.setCurrentBet(0);
            return RoundResult.BUST;
        }

        if (dealer.isBusted() || player.getHandValue() > dealer.getHandValue()) {
            player.winBet();
            return RoundResult.WIN;
        }

        if (player.getHandValue() == dealer.getHandValue()) {
            player.pushBet();
            return RoundResult.PUSH;
        }

        player.setCurrentBet(0);
        return RoundResult.LOSE;
    }

    private void endRound() {
        roundActive = false;
        roundPhase = RoundPhase.ROUND_OVER;
    }

    private void ensureRoundActive() {
        if (!roundActive) {
            throw new IllegalStateException("No active round.");
        }
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Player getCpuOne() {
        return cpuOne;
    }

    public Player getCpuTwo() {
        return cpuTwo;
    }

    public Deck getDeck() {
        return deck;
    }

    public boolean isRoundActive() {
        return roundActive;
    }

    public RoundPhase getRoundPhase() {
        return roundPhase;
    }

    public RoundResult getHumanRoundResult() {
        return humanRoundResult;
    }

    @JsonIgnore
    public boolean isDealerSecondCardHidden() {
        return roundActive && roundPhase == RoundPhase.PLAYER_TURN;
    }
}
