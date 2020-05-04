package com.example.paint;

import android.graphics.Color;

import java.util.Objects;

public class Line implements Shape {

    private Point start;
    private Point end;
    private int color;
    private double border;
    private int borderColor;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.color = Color.BLACK;
        this.border = 1;
        this.borderColor = Color.BLACK;
    }

    public Line(Point start, Point end, int color, double border, int borderColor) {
        this(start, end);
        this.color = color;
        this.border = border;
        this.borderColor = borderColor;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return getStart().equals(line.getStart()) &&
                getEnd().equals(line.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public Shapes whoAmI() {
        return Shapes.LINE;
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
        return Math.round((point.getX() - start.getX()) / (end.getX() - start.getX())) == Math.round((point.getY() - start.getY()) / (end.getY() - start.getY()));
    }
}
