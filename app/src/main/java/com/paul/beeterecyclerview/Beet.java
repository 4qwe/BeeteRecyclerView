package com.paul.beeterecyclerview;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import androidx.annotation.ColorInt;

import java.util.Random;


public class Beet {

    public String bezeichnung;
    private int wasserstand;
    public GradientDrawable shap;


    public Beet(String name) {
        this.bezeichnung = name;
        this.wasserstand = (int) Math.random() * 100;
        this.shap = this.makeShape();
    }

    public GradientDrawable makeShape() {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
        String colorCode = String.format("#%06x", rand_num);
        gd.setColor(Color.parseColor(colorCode));
        return gd;
    }

}
