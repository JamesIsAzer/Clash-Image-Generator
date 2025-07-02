package com.profile.utils;

public class GradientStop {
    private final float offset;
    private final String color;

    public GradientStop(float offset, String color) {
        this.offset = offset;
        this.color = color;
    }

    public float getOffset() {
        return offset;
    }

    public String getColor() {
        return color;
    }
}