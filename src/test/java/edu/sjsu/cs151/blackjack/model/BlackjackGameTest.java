package edu.sjsu.cs151.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.sjsu.cs151.blackjack.model.BlackjackGame.RoundPhase;
import edu.sjsu.cs151.blackjack.model.BlackjackGame.RoundResult;

public class BlackjackGameTest {
    private BlackjackGame game;
    
    @BeforeEach
    public void setup() {
        Player player = new Player("Bob");
        game = new BlackjackGame(player);
    }

    @Test
    public void IlegallPlayerBet() {
        assertThrows(IllegalArgumentException.class, () -> {
            game.playerBet(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.playerBet(1001);
        });
    }

    @Test
    public void dealerBetterHandResult() {
        game.playerBet(10);

        game.startRound();

        Dealer dealer = game.getDealer();
        Player player = game.getHumanPlayer();
        dealer.clearHand();
        dealer.addCard(new Card(Suit.CLUBS, Rank.ACE));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));

        while (game.getRoundPhase() == RoundPhase.PLAYER_TURN) {
            player.clearHand();
            player.addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));
            player.addCard(new Card(Suit.DIAMONDS, Rank.TWO));
            game.playerStand();
        }
        
        assertEquals(990, player.getBankRoll());
        assertEquals(RoundResult.LOSE, game.getHumanRoundResult());
    }

    @Test
    public void dealerPlayerPushResult() {
        game.playerBet(10);

        game.startRound();

        Dealer dealer = game.getDealer();
        Player player = game.getHumanPlayer();
        dealer.clearHand();
        dealer.addCard(new Card(Suit.CLUBS, Rank.ACE));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        while (game.getRoundPhase() == RoundPhase.PLAYER_TURN) {
            player.clearHand();
            player.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
            player.addCard(new Card(Suit.DIAMONDS, Rank.NINE));
            game.playerStand();
        }
        
        assertEquals(1000, player.getBankRoll());
        assertEquals(RoundResult.PUSH, game.getHumanRoundResult());
    }

    @Test
    public void playerBetterHandResult() {
        game.playerBet(10);

        game.startRound();

        Dealer dealer = game.getDealer();
        Player player = game.getHumanPlayer();
        dealer.clearHand();
        dealer.addCard(new Card(Suit.CLUBS, Rank.ACE));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        while (game.getRoundPhase() == RoundPhase.PLAYER_TURN) {
            player.clearHand();
            player.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
            player.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
            game.playerStand();
        }
        
        assertEquals(1010, player.getBankRoll());
        assertEquals(RoundResult.WIN, game.getHumanRoundResult());
    }

    @Test
    public void playerBustResult() {
        game.playerBet(10);

        game.startRound();

        Dealer dealer = game.getDealer();
        Player player = game.getHumanPlayer();
        dealer.clearHand();
        dealer.addCard(new Card(Suit.CLUBS, Rank.ACE));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        while (game.getRoundPhase() == RoundPhase.PLAYER_TURN) {
            player.clearHand();
            player.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
            player.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
            player.addCard(new Card(Suit.DIAMONDS, Rank.FOUR));
            game.playerHit();
        }
        
        assertEquals(RoundResult.BUST, game.getHumanRoundResult());
    }

}
