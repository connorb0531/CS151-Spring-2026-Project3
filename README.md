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

## Instructions -- Chelsea
## Usage -- Shannon

## Video Demo LINK
@julie


## Members
Connor Buckley, Shannon Lo, Chelsea Pham, and Julie Nguyen.

## Contributions

| Team Member      | Responsibility | Description     |
| :---        |    :----   |          :--- |
| Connor Buckley | Blackjack | Core game logic in `edu.sjsu.cs151.blackjack.model` (e.g. deck, cards, player, dealer, game rules and save state). |
| Shannon Lo   | Snake Game Implementation        | - what i did|
| Chelsea Pham | Game Manager Implementation | (Bulletpoints of what was done) |
| Julie Nguyen | Blackjack |   |
