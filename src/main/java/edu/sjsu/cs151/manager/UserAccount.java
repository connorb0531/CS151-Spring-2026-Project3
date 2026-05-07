package edu.sjsu.cs151.manager;

public class UserAccount {

    private String username;
    private String passwordHash;
    private int blackjackScore;
    private int snakeScore;

    public UserAccount(String username, String password) {
        this.username = username;
        this.passwordHash = password;
        this.blackjackScore = 1000;
        this.snakeScore = 0;
    }

    public boolean checkPassword(String password) {
        return this.passwordHash.equals(password);
    }

    public String getUsername() { 
        return username; 
    }
    public String getPasswordHash() { 
        return passwordHash; 
    }
    public int getBlackjackScore() { 
        return blackjackScore; 
    }
    public int getSnakeScore() { 
        return snakeScore; 
    }
    public void setBlackjackScore(int score) {
        this.blackjackScore = score;
    }
    public void setSnakeScore(int score) { 
        this.snakeScore = score; 
    }
}
