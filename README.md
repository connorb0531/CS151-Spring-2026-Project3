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

## Design -- Julie

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
7. High scores are saved automatically when you exit a game — check the main menu leaderboard to see the top 5 scores for each game

## Usage -- Shannon

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
