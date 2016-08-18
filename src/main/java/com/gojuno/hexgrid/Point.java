package com.gojuno.hexgrid;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("point{x: %f, y: %f}", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Point.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        Point other = (Point)obj;

        return other.x == x && other.y == y;
    }
}
