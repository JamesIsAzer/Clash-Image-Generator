package com.profile.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class RenderingUtility {
    public static void addRenderingHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    } 

    public static void drawRoundedRect(Graphics2D g, int x, int y, int width, int height, int radius) {
        g.fill(new RoundRectangle2D.Float(x, y, width, height, radius, radius));
    }

    public static void drawRoundedRectOutline(Graphics2D g, int x, int y, int width, int height, int radius) {
        g.draw(new RoundRectangle2D.Float(x, y, width, height, radius, radius));
    }
}
