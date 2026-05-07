package edu.sjsu.cs151.snake.model;

import java.util.LinkedList;
import java.util.List;

public class Snake implements Collidable {

    private LinkedList<Point> body;
    private Direction currentDirection;

    public Snake(int startX, int startY) {
        body = new LinkedList<>(); 

        // generate random starting position!
        java.util.Random rand = new java.util.Random();

        int[][] starts = {{startX, startY}, {startX+3, startY}, {startX-3, startY}, {startX, startY+3}};
        int[] start = starts[rand.nextInt(starts.length)];
        int x = start[0], y = start[1];

        List<Direction> dirs = List.of(Direction.values());
        currentDirection = dirs.get(rand.nextInt(dirs.size()));

        int dx = 0, dy = 0;

        if (currentDirection == Direction.RIGHT) dx = -1;
        if (currentDirection == Direction.LEFT)  dx =  1;
        if (currentDirection == Direction.DOWN)  dy = -1;
        if (currentDirection == Direction.UP)    dy =  1;

        body.add(new Point(x, y)); 

        body.add(new Point(x + dx, y + dy));

        body.add(new Point(x + dx*2, y + dy*2));
        
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
    public boolean collidesWith(Point p) {
        return getHead().equals(p);
    }

}