package edu.sjsu.cs151.snake.model;

import java.util.LinkedList;

//this snake is a list of Points
public class Snake implements Collidable {

    private LinkedList<Point> body;
    private Direction currentDirection;

    public Snake(int startX, int startY) {
        body = new LinkedList<>(); 

        // head
        body.add(new Point(startX, startY)); 
        
        // middle

        body.add(new Point(startX - 1, startY));

        // tail
        body.add(new Point(startX - 2, startY));
        currentDirection = Direction.RIGHT;
    }


    public void move(boolean ateFood) {
        Point newHead = getHead().translate(currentDirection);
        body.addFirst(newHead);

        if (!ateFood) {
            body.removeLast();
        }
    }

    public void setDirection(Direction newDir) {
        if (!newDir.isOpposite(currentDirection)) {
            currentDirection = newDir;
        }
    }

    public Direction getDirection() {
        return currentDirection;
    }


    public Point getHead() {
        return body.getFirst();
    }



    public boolean bodyContains(Point p) {
        return body.stream().skip(1).anyMatch(seg -> seg.equals(p));
    }

    public LinkedList<Point> getBody() {
        return body;
    }


    @Override
    public boolean collidsWith(Point p) {
        throw new UnsupportedOperationException("Unimplemented method 'collidsWith'");
    }

}