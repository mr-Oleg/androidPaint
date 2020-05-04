package com.example.paint;

public class ToolkitValue {

    private static int currentColor;

    private static int currentBorderColor;

    private static double borderWeight;

    private static double startX;

    private static double startY;

    private static double endX;

    private static double endY;

    private static double radius;

    public static double getRadius() {
        return radius;
    }

    public static void setRadius(double radius) {
        ToolkitValue.radius = radius;
    }

    public static double getEndX() {
        return endX;
    }

    public static void setEndX(double endX) {
        ToolkitValue.endX = endX;
    }

    public static double getEndY() {
        return endY;
    }

    public static void setEndY(double endXY) {
        ToolkitValue.endY = endXY;
    }

    public static double getStartX() {
        return startX;
    }

    public static void setStartX(double startX) {
        ToolkitValue.startX = startX;
    }

    public static double getStartY() {
        return startY;
    }

    public static void setStartY(double startY) {
        ToolkitValue.startY = startY;
    }

    private static final ToolkitValue ourInstance = new ToolkitValue();

    public static ToolkitValue getInstance() {
        return ourInstance;
    }

    public static int getCurrentColor() {
        return currentColor;
    }

    public static void setCurrentColor(int currentColor) {
        ToolkitValue.currentColor = currentColor;
    }

    public static int getCurrentBorderColor() {
        return currentBorderColor;
    }

    public static void setCurrentBorderColor(int currentBorderColor) {
        ToolkitValue.currentBorderColor = currentBorderColor;
    }

    public static double getBorderWeight() {
        return borderWeight;
    }

    public static void setBorderWeight(double borderWeight) {
        ToolkitValue.borderWeight = borderWeight;
    }
}
