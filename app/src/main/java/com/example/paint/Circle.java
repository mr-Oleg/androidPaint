package com.example.paint;

import android.graphics.Color;

import java.util.Objects;

public class Circle implements Shape {

    private Point center;
    private double radius;
    private int color;
    private double border;
    private int borderColor;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
        this.border = 1;
        this.color = Color.BLACK;
        this.borderColor = Color.BLACK;
    }

    public Circle(Point center, double radius, int color, double border, int borderColor) {
        this(center, radius);
        this.border = border;
        this.color = color;
        this.borderColor = borderColor;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.getRadius(), getRadius()) == 0 &&
                getCenter().equals(circle.getCenter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCenter(), getRadius());
    }

    @Override
    public String toString() {
        return "Circle{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Shapes whoAmI() {
        return Shapes.CIRCLE;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public void setWeightBorder(double border) {
        this.border = border;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public double getWeightBorder() {
        return border;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public boolean isInside(Point point) {
        return Math.sqrt((point.getX() - center.getX()) * (point.getX() - center.getX()) + (point.getY() - center.getY()) * (point.getY() - center.getY())) <= radius;
    }

}
