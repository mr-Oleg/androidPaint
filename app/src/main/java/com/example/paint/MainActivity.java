package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private static ToolkitValue toolkitValue;
    private static EditText border;
    private static EditText coord;
    private static EditText size;
    private static Button color;
    private static Button borderColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolkitValue = ToolkitValue.getInstance();
        ToolkitValue.setCurrentColor(Color.BLACK);
        ToolkitValue.setCurrentBorderColor(Color.BLACK);
        ToolkitValue.setBorderWeight(1);
        ToolkitValue.setStartX(0);
        ToolkitValue.setStartY(0);
        ToolkitValue.setEndX(100);
        ToolkitValue.setEndY(100);
        ToolkitValue.setRadius(100);
        border = findViewById(R.id.editText);
        coord = findViewById(R.id.editText2);
        size = findViewById(R.id.editText3);
        color = findViewById(R.id.button5);
        borderColor = findViewById(R.id.button3);
    }

    public static ToolkitValue getCurrentTools() {
        return toolkitValue;
    }

    public void saveToGallery(View v) {
        DrawArea drawArea = findViewById(R.id.drawArea);
        String response = drawArea.saveToGallery(getContentResolver());
        if (response == null) {
            Toast.makeText(getApplicationContext(),
                    "Изображение не сохранено!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Изображение сохранено!", Toast.LENGTH_SHORT).show();
        }
    }

    public void createCircle(View v) {
        DrawArea drawArea = findViewById(R.id.drawArea);
        EditText weight = findViewById(R.id.editText);
        parseCoord();
        switchFunc();
        try {
            double value = Double.parseDouble(weight.getText().toString());
            ToolkitValue.setBorderWeight(value);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    "Некорректная толщина !", Toast.LENGTH_SHORT).show();
            return;
        }
        drawArea.drawCircle();
    }

    public void createSquare(View v) {
        DrawArea drawArea = findViewById(R.id.drawArea);
        EditText weight = findViewById(R.id.editText);
        parseCoord();
        switchFunc();
        try {
            double value = Double.parseDouble(weight.getText().toString());
            ToolkitValue.setBorderWeight(value);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    "Некорректная толщина !", Toast.LENGTH_SHORT).show();
            return;
        }
        drawArea.drawRectangle();
    }

    public void createLine(View v) {
        DrawArea drawArea = findViewById(R.id.drawArea);
        EditText weight = findViewById(R.id.editText);
        parseCoord();
        switchFunc();
        try {
            double value = Double.parseDouble(weight.getText().toString());
            ToolkitValue.setBorderWeight(value);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    "Некорректная толщина !", Toast.LENGTH_SHORT).show();
            return;
        }
        drawArea.drawLine();
    }

    public void clear(View v) {
        DrawArea drawArea = findViewById(R.id.drawArea);
        drawArea.clear();
        drawArea.setBackgroundColor(Color.WHITE);
    }

    /**
     * Callback that is invoked when a color is selected from the color picker dialog.
     *
     * @param dialogId The dialog id used to create the dialog instance.
     * @param color    The selected color
     */
    @Override
    public void onColorSelected(int dialogId, int color) {
        Button b1 = findViewById(R.id.button3);
        Button b2 = findViewById(R.id.button5);
        switch (dialogId) {
            case 10:
                ToolkitValue.setCurrentBorderColor(color);
                b1.setBackgroundTintList(ColorStateList.valueOf(color));
                break;
            case 20:
                ToolkitValue.setCurrentColor(color);
                b2.setBackgroundTintList(ColorStateList.valueOf(color));
                break;
            default:
                return;
        }
    }

    /**
     * Callback that is invoked when the color picker dialog was dismissed.
     *
     * @param dialogId The dialog id used to create the dialog instance.
     */
    @Override
    public void onDialogDismissed(int dialogId) {
        Toast.makeText(getApplicationContext(),
                "Цвет выбран", Toast.LENGTH_SHORT).show();
    }

    private void createColorPickerDialog(int id) {
        ColorPickerDialog.newBuilder()
                .setColor(Color.RED)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.CIRCLE)
                .setDialogId(id)
                .show(this);
    }

    public void colorPickerBorder(View v) {
        createColorPickerDialog(10);
    }

    public void colorPickerShape(View v) {
        createColorPickerDialog(20);
    }

    public void parseCoord() {
        EditText coordField = findViewById(R.id.editText2);
        String value = coordField.getText().toString();
        String[] values = value.split(";");
        double xVal, yVal;
        try {
            xVal = Double.parseDouble(values[0]);
            yVal = Double.parseDouble(values[1]);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    "Некорректные координаты !", Toast.LENGTH_SHORT).show();
            coordField.setText("0;0");
            ToolkitValue.setStartX(0);
            ToolkitValue.setStartY(0);
            return;
        }
        ToolkitValue.setStartX(xVal);
        ToolkitValue.setStartY(yVal);
    }

    public void switchFunc() {
        EditText coordField = findViewById(R.id.editText3);
        String[] values = coordField.getText().toString().split(";");
        if (values.length == 1) {
            parseCircleSize();
        } else {
            parseNormalSize();
        }
    }

    public void parseNormalSize() {
        EditText coordField = findViewById(R.id.editText3);
        String value = coordField.getText().toString();
        String[] values = value.split(";");
        double xVal, yVal;
        try {
            xVal = Double.parseDouble(values[0]);
            yVal = Double.parseDouble(values[1]);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    "Некорректные координаты !", Toast.LENGTH_SHORT).show();
            coordField.setText("100;100");
            ToolkitValue.setEndX(100);
            ToolkitValue.setEndY(100);
            return;
        }
        ToolkitValue.setEndX(ToolkitValue.getStartX() + xVal);
        ToolkitValue.setEndY(ToolkitValue.getStartY() + yVal);
    }

    public void parseCircleSize() {
        EditText coordField = findViewById(R.id.editText3);
        String value = coordField.getText().toString();
        double radius;
        try {
            radius = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    "Некорректные координаты !", Toast.LENGTH_SHORT).show();
            coordField.setText("100");
            ToolkitValue.setRadius(100);
            return;
        }
        ToolkitValue.setRadius(radius);
    }

    public static EditText getBorderEditText() {
        return border;
    }

    public static EditText getCoordEditText() {
        return coord;
    }

    public static EditText getSizeEditText() {
        return size;
    }

    public static Button getColorButton() {
        return color;
    }

    public static Button getColorBorderButton() {
        return borderColor;
    }
}
