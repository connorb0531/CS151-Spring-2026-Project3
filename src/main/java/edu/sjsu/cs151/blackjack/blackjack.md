# Blackjack

## How the model is intended to be used

The frontend should treat `BlackjackGame` as the main access point to the blackjack backend.

Typical round flow:

1. Create a `Player`.
2. Create a `BlackjackGame` with that player.
3. Call `playerBet(amount)`.
4. Call `startRound()`.
5. Read hands, bankrolls,round phase, and round results for each player. 
6. While `getRoundPhase()` is `PLAYER_TURN`, let the user press Hit or Stand.
7. Call `playerHit()` or `playerStand()` based on the button pressed.
8. After the round ends, read `getHumanRoundResult()`.
9. When the user wants to play again, place a new bet and start the next round.

## Frontend methods for BlackjackGame

Frontend code should mainly use these methods:

Used for controlling flow of game:
- `playerBet(int amount)`
- `startRound()`
- `playerHit()`
- `playerStand()`

Used for data retrieval:
- `isRoundActive()`
- `getRoundPhase()`
- `getHumanRoundResult()`
- `isDealerSecondCardHidden()`
- `getHumanPlayer()`
- `getCpuOne()`
- `getCpuTwo()`
- `getDealer()`

## Game serialization

`BlackjackGameSave.java` provides functionality for saving game progess via serializing `BlackjackGame` into JSON file
- `save()` accepts `BlackjackGame` and returns hashed string JSON file name
- `load()` accepts said hashed string and returns corresponding `BlackjackGame`
