package edu.sjsu.cs151.snake.model;

import java.util.Objects;

public final class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point translate(Direction d) {
        return new Point(x + d.dx, y + d.dy);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point p)) return false;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
