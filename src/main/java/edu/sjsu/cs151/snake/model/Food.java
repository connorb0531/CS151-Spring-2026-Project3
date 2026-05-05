package edu.sjsu.cs151.snake.model;

import java.util.Random;

public class Food implements Collidable{
    
    private Point position;
    private final int boardWidth;
    private final int boardHeight;

    public Food(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.position = randomPosition();
    }

    public void respawn() {
        position = randomPosition();
    }

    private Point randomPosition() {
        Random rand = new Random();
        int x = rand.nextInt(boardWidth);
        int y = rand.nextInt(boardHeight);
        return new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public boolean collidesWith(Point p) {
        return position.equals(p);
    }

}
