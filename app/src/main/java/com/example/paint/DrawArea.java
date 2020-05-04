package com.example.paint;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.paint.Shapes.*;

public class DrawArea extends View {

    private List<Shape> shapes = new ArrayList<>();
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private ShapeFactory defaults;
    private boolean touchMode = false;
    private Shape caughtShape = null;
    private Point startTouch;
    private Point endTouch;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public DrawArea(Context context) {
        super(context);
        this.setDrawingCacheEnabled(true);
    }

    public DrawArea(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setDrawingCacheEnabled(true);
    }

    public DrawArea(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setDrawingCacheEnabled(true);
    }

    public DrawArea(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        defaults = new ShapeFactoryImpl();
        paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawColor(Color.WHITE);
        for (Shape shape : shapes) {
            if (shape.whoAmI().equals(CIRCLE)) {
                paint.setStyle(Paint.Style.FILL);
                Circle circle = (Circle) shape;
                paint.setColor(circle.getColor());
                canvas.drawCircle((float) circle.getCenter().getX(), (float) circle.getCenter().getY(), (float) circle.getRadius(), paint);
                paint.setColor(circle.getBorderColor());
                paint.setStrokeWidth((float) circle.getWeightBorder() * 5);
                System.out.println(circle.getBorderColor());
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle((float) circle.getCenter().getX(), (float) circle.getCenter().getY(), (float) circle.getRadius(), paint);
            } else if (shape.whoAmI().equals(RECTANGLE)) {
                paint.setStyle(Paint.Style.FILL);
                Rectangle rectangle = (Rectangle) shape;
                paint.setColor(rectangle.getColor());
                canvas.drawRect((float) rectangle.getLeftTop().getX(), (float) rectangle.getLeftTop().getY(), (float) rectangle.getRightDown().getX(), (float) rectangle.getRightDown().getY(), paint);
                paint.setColor(rectangle.getBorderColor());
                paint.setStrokeWidth((float) rectangle.getWeightBorder() * 5);
                System.out.println(rectangle.getBorderColor());
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect((float) rectangle.getLeftTop().getX(), (float) rectangle.getLeftTop().getY(), (float) rectangle.getRightDown().getX(), (float) rectangle.getRightDown().getY(), paint);
            } else {
                paint.setStyle(Paint.Style.FILL);
                Line line = (Line) shape;
                paint.setColor(line.getColor());
                canvas.drawLine((float) line.getStart().getX(), (float) line.getStart().getY(), (float) line.getEnd().getX(), (float) line.getEnd().getY(), paint);
                paint.setColor(line.getBorderColor());
                paint.setStrokeWidth((float) line.getWeightBorder() * 5);
                System.out.println(line.getBorderColor());
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawLine((float) line.getStart().getX(), (float) line.getStart().getY(), (float) line.getEnd().getX(), (float) line.getEnd().getY(), paint);
            }
        }
        bitmap = Bitmap.createBitmap(this.getDrawingCache());
    }

    public void drawCircle() {
        try {
            Circle circle = (Circle) defaults.create(CIRCLE);
            shapes.add(circle);
            invalidate();
        } catch (NotFoundFigureException e) {
            e.printStackTrace();
        }
    }

    public void drawRectangle() {
        try {
            Rectangle rectangle = (Rectangle) defaults.create(RECTANGLE);
            shapes.add(rectangle);
            invalidate();
        } catch (NotFoundFigureException e) {
            e.printStackTrace();
        }
    }

    public void drawLine() {
        try {
            Line line = (Line) defaults.create(LINE);
            shapes.add(line);
            invalidate();
        } catch (NotFoundFigureException e) {
            e.printStackTrace();
        }
    }

    public String saveToGallery(ContentResolver contentResolver) {
        Bitmap bm = this.getDrawingCache();
        return MediaStore.Images.Media.insertImage(contentResolver, bm, "image", "");
    }

    public void clear() {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        shapes = new ArrayList<>();
        startTouch = null;
        endTouch = null;
        touchMode = false;
        caughtShape = null;
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!touchMode) {
                    startTouch = new Point(event.getX(), event.getY());
                    caughtShape = checkArea(startTouch);
                    if (caughtShape != null) {
                        touchMode = true;
                        EditText weight = MainActivity.getBorderEditText();
                        weight.setText(String.valueOf(caughtShape.getWeightBorder()));
                        EditText coord = MainActivity.getCoordEditText();
                        EditText size = MainActivity.getSizeEditText();
                        MainActivity.getColorButton().setBackgroundTintList(ColorStateList.valueOf(caughtShape.getColor()));
                        MainActivity.getColorBorderButton().setBackgroundTintList(ColorStateList.valueOf(caughtShape.getBorderColor()));
                        switch (caughtShape.whoAmI()) {
                            case CIRCLE:
                                Circle circle = (Circle) caughtShape;
                                coord.setText(String.valueOf(circle.getCenter().getX()) + ";" + String.valueOf(circle.getCenter().getY()));
                                size.setText(String.valueOf(circle.getRadius()));
                                ToolkitValue.setRadius(circle.getRadius());
                                ToolkitValue.setStartX(circle.getCenter().getX());
                                ToolkitValue.setStartY(circle.getCenter().getY());
                                break;
                            case RECTANGLE:
                                System.out.println("in");
                                Rectangle rectangle = (Rectangle) caughtShape;
                                coord.setText(String.valueOf(rectangle.getLeftTop().getX()) + ";" + String.valueOf(rectangle.getLeftTop().getY()));
                                size.setText(String.valueOf(rectangle.getRightDown().getX()) + ";" + String.valueOf(rectangle.getRightDown().getY()));
                                ToolkitValue.setStartX(rectangle.getLeftTop().getX());
                                ToolkitValue.setStartY(rectangle.getLeftTop().getY());
                                ToolkitValue.setEndX(rectangle.getRightDown().getX());
                                ToolkitValue.setEndY(rectangle.getRightDown().getY());
                                break;
                            case LINE:
                                Line line = (Line) caughtShape;
                                coord.setText(String.valueOf(line.getStart().getX()) + ";" + String.valueOf(line.getStart().getY()));
                                size.setText(String.valueOf(line.getEnd().getX()) + ";" + String.valueOf(line.getEnd().getY()));
                                ToolkitValue.setStartX(line.getStart().getX());
                                ToolkitValue.setStartY(line.getStart().getY());
                                ToolkitValue.setEndX(line.getEnd().getX());
                                ToolkitValue.setEndY(line.getEnd().getY());
                                break;
                        }
                        ToolkitValue.setCurrentColor(caughtShape.getColor());
                        ToolkitValue.setCurrentBorderColor(caughtShape.getBorderColor());
                        return false;
                    } else {
                        startTouch = null;
                        touchMode = false;
                        return false;
                    }
                } else {
                    endTouch = new Point(event.getX(), event.getY());
                    EditText coord = MainActivity.getCoordEditText();
                    coord.setText(String.valueOf(Math.round(endTouch.getX())) + ";" + String.valueOf(Math.round(endTouch.getY())));
                    switch (caughtShape.whoAmI()) {
                        case CIRCLE:
                            Circle circle = (Circle) caughtShape;
                            try {
                                ToolkitValue.setStartX(endTouch.getX());
                                ToolkitValue.setStartY(endTouch.getY());
                                Circle newCircle = (Circle) defaults.create(CIRCLE);
                                circle.setColor(newCircle.getColor());
                                circle.setBorderColor(newCircle.getBorderColor());
                                circle.setRadius(newCircle.getRadius());
                                circle.setCenter(newCircle.getCenter());
                                circle.setWeightBorder(newCircle.getWeightBorder());
                            } catch (NotFoundFigureException e) {
                                e.printStackTrace();
                            }
                        case LINE:
                            Line line = (Line) caughtShape;
                            try {
                                double dX = line.getStart().getX() - line.getEnd().getX();
                                double dY = line.getStart().getY() - line.getEnd().getY();
                                ToolkitValue.setStartX(endTouch.getX());
                                ToolkitValue.setStartY(endTouch.getY());
                                ToolkitValue.setEndX(endTouch.getX() + dX);
                                ToolkitValue.setEndY(endTouch.getY() + dY);
                                Line newLine = (Line) defaults.create(LINE);
                                line.setColor(newLine.getColor());
                                line.setBorderColor(newLine.getBorderColor());
                                line.setStart(newLine.getStart());
                                line.setEnd(newLine.getEnd());
                                line.setWeightBorder(newLine.getWeightBorder());
                            } catch (NotFoundFigureException e) {
                                e.printStackTrace();
                            }
                            break;
                        case RECTANGLE:
                            Rectangle rectangle = (Rectangle) caughtShape;
                            try {
                                double dX = rectangle.getRightDown().getX() - rectangle.getLeftTop().getX();
                                double dY = rectangle.getRightDown().getY() - rectangle.getLeftTop().getY();
                                ToolkitValue.setStartX(endTouch.getX());
                                ToolkitValue.setStartY(endTouch.getY());
                                ToolkitValue.setEndX(endTouch.getX() + dX);
                                ToolkitValue.setEndY(endTouch.getY() + dY);
                                Rectangle newRectangle = (Rectangle) defaults.create(RECTANGLE);
                                rectangle.setBorderColor(newRectangle.getBorderColor());
                                rectangle.setColor(newRectangle.getColor());
                                rectangle.setLeftTop(newRectangle.getLeftTop());
                                rectangle.setRightDown(newRectangle.getRightDown());
                                rectangle.setWeightBorder(newRectangle.getWeightBorder());
                            } catch (NotFoundFigureException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    touchMode = false;
                    startTouch = null;
                    endTouch = null;
                    caughtShape = null;
                    invalidate();
                }
        }
        return false;
    }

    private Shape checkArea(Point point) {
        Shape returnObj = null;
        for (Shape shape : shapes) {
            if (shape.isInside(point)) {
                returnObj = shape;
            }
        }
        System.out.println(returnObj);
        return returnObj;
    }
}

