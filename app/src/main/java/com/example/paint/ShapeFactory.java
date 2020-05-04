package com.example.paint;

public interface ShapeFactory {
    Shape create(Shapes shapesENUM) throws NotFoundFigureException;
}
