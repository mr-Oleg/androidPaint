package com.example.paint;

public class ShapeFactoryImpl implements ShapeFactory {
    @Override
    public Shape create(Shapes shapesENUM) throws NotFoundFigureException {
        switch (shapesENUM) {
            case CIRCLE:
                return new Circle(new Point(ToolkitValue.getStartX(), ToolkitValue.getStartY()), ToolkitValue.getRadius(), ToolkitValue.getCurrentColor(), ToolkitValue.getBorderWeight(), ToolkitValue.getCurrentBorderColor());
            case LINE:
                return new Line(new Point(ToolkitValue.getStartX(), ToolkitValue.getStartY()), new Point(ToolkitValue.getEndX(), ToolkitValue.getEndY()), ToolkitValue.getCurrentColor(), ToolkitValue.getBorderWeight(), ToolkitValue.getCurrentBorderColor());
            case RECTANGLE:
                return new Rectangle(new Point(ToolkitValue.getStartX(), ToolkitValue.getStartY()), new Point(ToolkitValue.getEndX(), ToolkitValue.getEndY()), ToolkitValue.getCurrentColor(), ToolkitValue.getBorderWeight(), ToolkitValue.getCurrentBorderColor());
            default:
                throw new NotFoundFigureException();
        }
    }
}
