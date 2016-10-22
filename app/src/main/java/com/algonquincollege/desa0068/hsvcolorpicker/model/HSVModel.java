package com.algonquincollege.desa0068.hsvcolorpicker.model;

import android.graphics.Color;

import java.util.Observable;

/**
 * Created by vaibhavidesai on 2016-10-18.
 */

public class HSVModel extends Observable {

    public static float MAX_HUE = 359.0f;
    public static float MAX_SATURATION = 100.0f;
    public static float MAX_VALUE = 100.0f;
    public static float MIN_VALUE = 0.0f;
    private float hue, saturation, value;


    public HSVModel() {
        this(MAX_HUE, MAX_HUE, MAX_VALUE);
    }

    public HSVModel(float hue, float saturation, float value) {
        super();
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
        this.updateObservers();
    }

    public float getValue() {
        return value * 100;
    }

    public void setValue(float value) {
        this.value = value / 100;
        this.updateObservers();
    }

    public float getSaturation() {
        return saturation * 100;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation / 100;
        this.updateObservers();
    }

    // broadcast the update method to all registered observers
    private void updateObservers() {
        this.setChanged();
        this.notifyObservers();
    }


    public int getHSVColor() {
        return Color.HSVToColor(new float[]{hue, saturation, value});
    }

    public float[] getColorToHSV(int color) {
        float hsv[] = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] * 100;
        hsv[2] = hsv[2] * 100;
        return hsv;
    }
}
