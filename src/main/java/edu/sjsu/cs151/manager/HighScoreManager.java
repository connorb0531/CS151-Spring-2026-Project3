package edu.sjsu.cs151.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class HighScoreManager {

    private final List<HighScoreEntry> blackjackScores = new ArrayList<>();
    private final List<HighScoreEntry> snakeScores = new ArrayList<>();
    private static final String FILE = "high_scores.txt";

    // Reads high_scores.txt and fills blackjackScores and snakeScores lists
    public void loadScores() {
        File f = new File(FILE);
        if (!f.exists()) return;

        try (Scanner scanner = new Scanner(f)) {
            List<HighScoreEntry> current = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equals("BLACKJACK")) {
                    current = blackjackScores;
                } else if (line.equals("SNAKE")) {
                    current = snakeScores;
                } else if (!line.isEmpty() && current != null) {
                    String[] parts = line.split(",");
                    current.add(new HighScoreEntry(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No high scores file found.");
        }
    }

    // Writes both score lists to high_scores.txt
    public void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE))) {
            writer.println("BLACKJACK");
            for (HighScoreEntry e : blackjackScores) {
                writer.println(e.getUsername() + "," + e.getScore());
            }
            writer.println("SNAKE");
            for (HighScoreEntry e : snakeScores) {
                writer.println(e.getUsername() + "," + e.getScore());
            }
        } catch (IOException e) {
            System.out.println("Could not save high scores.");
        }
    }

    // Updates the user's blackjack score and saves it
    public void updateBlackjackScore(String username, int score) {
        updateScore(blackjackScores, username, score);
        saveScores();
    }

    // Updates the user's snake score and saves it
    public void updateSnakeScore(String username, int score) {
        updateScore(snakeScores, username, score);
        saveScores();
    }

    // Returns top  blackjack scores
    public List<HighScoreEntry> getTopBlackjack(int n) {
        return getTop(blackjackScores, n);
    }

    // Returns top snake scores
    public List<HighScoreEntry> getTopSnake(int n) {
        return getTop(snakeScores, n);
    }

    // Adds a default score of 1000 for a new user in both games
    public void initialDefaultScore(String username) {
        blackjackScores.add(new HighScoreEntry(username, 1000));
        snakeScores.add(new HighScoreEntry(username, 0));
        saveScores();
    }

    // Replaces user's old score and sorts highest to lowest while keeping only top 5
    private void updateScore(List<HighScoreEntry> list, String username, int score) {
        int existing = list.stream()
            .filter(e -> e.getUsername().equals(username))
            .mapToInt(HighScoreEntry::getScore)
            .findFirst()
            .orElse(-1);
        if (score <= existing) return;
        list.removeIf(e -> e.getUsername().equals(username));
        list.add(new HighScoreEntry(username, score));
        list.sort(Comparator.comparingInt(HighScoreEntry::getScore).reversed());
        if (list.size() > 5) list.subList(5, list.size()).clear();

    }

    // Returns a sublist of the top n entries
    private List<HighScoreEntry> getTop(List<HighScoreEntry> list, int n) {
        return list.subList(0, Math.min(n, list.size()));
    }
}
