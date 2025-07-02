package com.profile.utils;

import java.util.*;
import java.util.concurrent.*;
import java.awt.*;

public class GradientManager {
    private static final int GRADIENT_CACHE_LIMIT = 50;
 
    private static final Map<String, Paint> gradientCache = new ConcurrentHashMap<>();
    private static final Map<String, Long> gradientAccessOrder = new LinkedHashMap<>();

    public static Paint createOptimizedGradient(String key, int x, int y, int width, int height,
                                                Color[] colors, float[] offsets, boolean horizontal) {
        String cacheKey = key + "_" + x + "_" + y + "_" + width + "_" + height + "_" + (horizontal ? "horizontal" : "vertical") + "_" + generateColorKey(colors, offsets);
        long now = System.currentTimeMillis();

        // Cache hit
        if (gradientCache.containsKey(cacheKey)) {
            gradientAccessOrder.put(cacheKey, now);
            return gradientCache.get(cacheKey);
        }

        // Create gradient
        LinearGradientPaint gradient;
        if (horizontal) {
            gradient = new LinearGradientPaint(x, y, x + width, y, offsets, colors);
        } else {
            gradient = new LinearGradientPaint(x, y, x, y + height, offsets, colors);
        }

        // LRU eviction
        if (gradientCache.size() >= GRADIENT_CACHE_LIMIT) {
            String oldestKey = gradientAccessOrder.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (oldestKey != null) {
                gradientCache.remove(oldestKey);
                gradientAccessOrder.remove(oldestKey);
            }
        }

        // Store new gradient
        gradientCache.put(cacheKey, gradient);
        gradientAccessOrder.put(cacheKey, now);

        return gradient;
    }

    private static String generateColorKey(Color[] colors, float[] offsets) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < colors.length; i++) {
            Color c = colors[i];
            sb.append(offsets[i]).append("_").append(c.getRed()).append("_").append(c.getGreen())
              .append("_").append(c.getBlue()).append("_").append(c.getAlpha()).append("|");
        }
        return sb.toString();
    }
}
