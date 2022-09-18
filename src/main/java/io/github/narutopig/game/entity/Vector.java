package io.github.narutopig.game.entity;

import java.awt.*;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector sub(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector scale(double scale) {
        return new Vector(x * scale, y * scale);
    }

    public static double distance(Vector a, Vector b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public double distance(Vector other) {
        // interpret vector as point

        return new Point((int) x, (int) y).distance(other.x, other.y);
    }

    public String toString() {
        return "Vector(" + x + "," + y + ")";
    }
}