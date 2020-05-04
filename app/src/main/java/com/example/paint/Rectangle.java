package com.example.paint;

import android.graphics.Color;

import java.util.Objects;

public class Rectangle implements Shape {

    private Point leftTop;
    private Point rightDown;
    private int color;
    private double border;
    private int borderColor;

    public Rectangle(Point leftTop, Point rightDown) {
        this.leftTop = leftTop;
        this.rightDown = rightDown;
        color = Color.BLACK;
        border = 1;
        borderColor = Color.BLACK;
    }

    public Rectangle(Point leftTop, Point rightDown, int color, double border, int borderColor) {
        this(leftTop, rightDown);
        this.color = color;
        this.border = border;
        this.borderColor = borderColor;
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Point leftTop) {
        this.leftTop = leftTop;
    }

    public Point getRightDown() {
        return rightDown;
    }

    public void setRightDown(Point rightDown) {
        this.rightDown = rightDown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle)) return false;
        Rectangle rectangle = (Rectangle) o;
        return getLeftTop().equals(rectangle.getLeftTop()) &&
                getRightDown().equals(rectangle.getRightDown());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeftTop(), getRightDown());
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "leftTop=" + leftTop +
                ", rightDown=" + rightDown +
                '}';
    }

    @Override
    public Shapes whoAmI() {
        return Shapes.RECTANGLE;
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
        return (point.getX() >= leftTop.getX() && point.getX() <= rightDown.getX() && point.getY() >= leftTop.getY() && point.getY() <= rightDown.getY());
    }
}
