package com.example.paint;

public interface Shape {

    Shapes whoAmI();

    void setColor(int color);

    void setBorderColor(int borderColor);

    void setWeightBorder(double border);

    int getColor();

    double getWeightBorder();

    int getBorderColor();

    boolean isInside(Point point);
}
