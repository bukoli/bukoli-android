package com.mobillium.bukoliandroidsdk.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.mobillium.bukoliandroidsdk.Bukoli;

/**
 * Created by oguzhandongul on 14/10/2016.
 */

public class ShapeCreator {
    static ShapeCreator instance = null;

    public ShapeCreator() {
    }

    public static ShapeCreator getInstance() {
        if (instance == null) {
            instance = new ShapeCreator();
        }
        return instance;
    }

    public static Drawable createStrokeButton() {

        GradientDrawable shape1 = new GradientDrawable();
        shape1.setColor(Bukoli.getInstance().getButtonBackgroundColor());
        shape1.setCornerRadius(Bukoli.getInstance().convertDpiToPixel(5));
        shape1.setStroke(Bukoli.getInstance().convertDpiToPixel(1), getDarker(Bukoli.getInstance().getButtonBackgroundColor(), 0.85f));

        return shape1;
    }

    public static Drawable createStrokeButtonPressed() {

        GradientDrawable shape1 = new GradientDrawable();
        shape1.setColor(getDarker(Bukoli.getInstance().getButtonBackgroundColor(), 0.85f));
        shape1.setCornerRadius(Bukoli.getInstance().convertDpiToPixel(5));

        return shape1;
    }


    private static int getDarker(int color, float factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }


}
