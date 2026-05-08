# CS151 Project 3

## Overview
This project is multi-game hub built with JavaFX 21 and Maven. It includes a game manager for account login and high score tracking, along with two games: Blackjack and Snake.

The project is organized into a few main packages:
- `edu.sjsu.cs151.manager`
  - Handles user accounts, login flow, and high score management.
- `edu.sjsu.cs151.blackjack`
  - Contains the Blackjack game model, controllers, save/load support, FXML views.
- `edu.sjsu.cs151.snake`
  - Contains the Snake game model, controllers, and FXML views.

See Contributions section to view each team member’s role and specific project work.

## Design 
The project was created through object-oriented programming principines, the application itself consists of components such as the Game Manager, Blackjack Game and Snake Game. Each game is implemented by serperate models. Furthermore, the frontened GUI was built using JavaFX and FXML, while the backend game logic was constructed into model classes in order to maintain organization. Essentially, the goal of the design is to keep the game logic independent from one another. 

Blackjack:
- The game was implemented using Java and JavaFX. The game will support a user-human player along with two automated CPU players, and a dealer. The player is able to place hit, bets, stand and to play multiple rounds while their bankroll is updated throughout rounds. The backend game logic is seperated into model classes responsible for managing the overall game state.

Snake:
- The Snake game is a keyboard-controlled arcade game implemented using Java and JavaFX. The player controls the snake using arrow keys while collecting food to increase both the snake’s length and the player’s score. The GUI additionally showcases updates in real-time which displays the food, score, and game board.

## Installation
Requirements:
- Java 21
- Maven 3.9 or newer

Clone the repository and move into the project folder (ssh):

```bash
git clone git@github.com:connorb0531/CS151-Spring-2026-Project3.git
cd project3
```

Compile the project:

```bash
mvn compile
```

Run the JavaFX application:

```bash
mvn javafx:run
```

Run the tests:

```bash
mvn test
```

## Instructions
1. Launch the app with `mvn javafx:run`
2. On the login screen, enter a username and password to log in, or click **Create Account** to register a new account
3. From the main menu, click **Play now** under Blackjack or Snake to launch a game
4. Use the toolbar at the top to navigate: **Go Back** returns to the game menu, **Main Menu** returns to the main hub, **Logout** signs you out
5. In Blackjack: enter a bet amount and click **Place Bet** to start a round, then use **Hit** or **Stand** to play
6. In Snake: use the arrow keys to move the snake, **Escape** to pause, and **R** to restart after game over
7. High scores are saved automatically when you exit a game and checks the main menu leaderboard to see the top 5 scores for each game

## Usage

## Blackjack Functionality

#### Placing a Bet
- Enter a whole dollar amount into the **Bet** text field and click **Bet** to begin a round.
- The bet must be a valid integer and cannot exceed your current bankroll; an error message will display otherwise.
- Once a bet is placed, the round starts immediately and cards are dealt to all players and the dealer.
- The bet field and button are disabled during an active round and re-enabled after the round ends.

#### Playing Your Hand
- **Hit:** Draws one additional card from the deck and adds it to your hand. Can be used repeatedly until you bust or choose to stand.
- **Stand** Ends your turn without drawing. The two CPU players then take their turns, followed by the dealer, automatically.
- Both buttons are only active during your turn and are disabled before betting and after the round concludes.

#### Dealer and CPU Behavior
- CPU players and the dealer act automatically after you stand; no input is required.
- The dealer's second card is hidden (face-down) until it is the dealer's turn, at which point it is revealed.
- The active player or dealer is highlighted with a green border to indicate whose turn it is.

#### Round Results
- After all participants have played, the round result is displayed in the status bar (e.g. win, loss, or bust).
- All players' bankrolls update automatically to reflect the outcome.

#### Saving and Restoring
- **Save:** Serializes the full current game state (hands, bankrolls, round phase) into an encoded string displayed in the save field.
- **Copy:** Copies the save string to your clipboard so it can be stored externally and loaded in a future session.
- A saved game can be reloaded via the load screen; if the round was active when saved, hit and stand will be immediately available upon loading.

  
## Snake Game Functionality
 
#### Movement
- The player controls the snake using the arrow keys (up, down, left, right).
- The snake continuously moves in the last input direction until redirected or the game ends.
- The snake cannot reverse direction (e.g. cannot go left while moving right).
- At the start of each game, the snake spawns near the center of the map with a randomly chosen direction and starting position.
- The current score is displayed in the top-left corner of the screen and updates in real time as food is collected.
#### Food and Growth
- Food spawns at a random position on the grid at the start of each game.
- When the snake's head collides with the food, the snake grows by one segment and the score increases.
- After being collected, food immediately respawns at a new random position, including along the edges of the board.
- Food will never respawn in the same position as the previous food item.
#### Collision and Game Over
- The game ends if the snake's head hits a wall or collides with any part of its own body.
- When the game ends, a centered overlay displays the final score and a prompt to press `R` to restart.
- The high score is persistently recorded at game over.
#### Pause
- The player can press `Escape` at any time to pause or resume the game.
- While paused, all movement stops and a "PAUSED" overlay is displayed on screen.
- Pressing `Escape` again resumes the game from where it left off.
- The player can press `R` at any time (including while the game is running) to restart.


## Video Demo LINK
@julie


## Members
Connor Buckley, Shannon Lo, Chelsea Pham, and Julie Nguyen.

## Contributions

| Team Member      | Responsibility | Description |
| :---        | :----   | :--- |
| Connor Buckley | Blackjack | Core game logic in `edu.sjsu.cs151.blackjack.model` (e.g. deck, cards, player, dealer, game rules and save state). |
| Shannon Lo   | Snake Game Implementation | For the Snake game, I implemented a fully functional JavaFX game controlled entirely by keyboard input. The snake spawns near the center of the board with a random starting direction and moves continuously until redirected by the player. I built out all core game mechanics including food collection, snake growth, score tracking, wall and self-collision detection, and a pause system triggered by the Escape key. The score updates in real time on screen, and the game over screen displays the final score with a restart option as a centered overlay. On the visual side, I designed a checkered dark green game board that snaps to a fixed tile size to prevent partial tiles from appearing at the edges. Instead of using a plain colored circle for food, I created a set of custom PNG images representing different animals, and the game randomly selects a new image each time food is collected. The snake segments alternate between two shades of green to give it a connected, scaled appearance. I also added locally stored background music that plays on loop when the game starts, stops on game over, and restarts when the player presses R to play again. |
| Chelsea Pham | Game Manager Implementation | Built the Game Manager hub including the login screen, account creation, and input validation. Implemented `UserAccountManager` and `HighScoreManager` to persist user accounts and top 5 scores per game to local text files. Built `GameManagerController` to handle all scene transitions and a persistent toolbar with Main Menu, Logout, and Go Back buttons. Added `CipherUtil` using AES encryption to encrypt passwords before storing them. |
| Julie Nguyen | Blackjack | I implemented the Blackjack frontend using JavaFX, FXML, and controller classes. I created the Blackjack start menu with buttons to start a new game and load a game using a saveStateString input, and added a getGameView() method for Game Manager integration. I built the main game UI with sections for the human player, CPU players, and dealer, including card displays, Hit/Stand buttons, bankroll labels, betting input, turn display and highlighting, and status messages. The UI is connected to the BlackjackGame backend to handle gameplay, including placing bets, starting rounds, updating bankrolls, displaying cards (with hidden dealer card), and calling playerHit() and playerStand(). |
