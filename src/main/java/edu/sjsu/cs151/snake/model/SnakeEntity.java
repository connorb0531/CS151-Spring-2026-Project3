package edu.sjsu.cs151.snake.model;

public abstract class SnakeEntity {
    
    protected Point position;

    public SnakeEntity(Point position) {
        this.position = position;
    }

    public Point getPosition() { 
        return position; 
    }
}