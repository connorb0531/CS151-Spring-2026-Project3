package edu.sjsu.cs151.manager;
public class HighScoreEntry {

    private String username;
    private int score;

    public HighScoreEntry(String username, int score){
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore(){
        return score;
    }


}