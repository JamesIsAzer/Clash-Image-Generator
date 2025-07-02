package com.profile.render;

import java.awt.*;
import java.io.InputStream;

public class FontLoader {
    public static void loadCustomFonts() {
        try {
            registerFont("/fonts/Clash_Regular.otf");
            registerFont("/fonts/Clash_Bold.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerFont(String path) throws Exception {
        try (InputStream is = FontLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Font not found at: " + path);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        }
    }
}