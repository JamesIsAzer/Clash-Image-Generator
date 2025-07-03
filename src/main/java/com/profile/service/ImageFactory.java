package com.profile.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.profile.service.imageGenerator.ImageGenerator;
import com.profile.utils.Cache;

public class ImageFactory {

    private static final long CACHE_TTL_MS = 5 * 60 * 1000; // 5 minutes
    private static final Map<String, Cache<byte[]>> renderCache = new ConcurrentHashMap<>();

    public static byte[] getCachedRender(
        String renderClass,
        String tag, 
        ImageGenerator<String> renderFunction
    ) throws Exception {
        String key = renderClass + "-" + tag;

        Cache<byte[]> cached = renderCache.get(key);
        long now = System.currentTimeMillis();

        if (cached != null && cached.expiresAt() > now) {
            return cached.value();
        }

        try {
            byte[] result = renderFunction.generateImage(tag);
            renderCache.put(key, new Cache<>(result, now + CACHE_TTL_MS));
            return result;
        } catch (Exception e) {
            System.err.println("Render function failed for key " + key + ": " + e.getMessage());
            throw e;
        }
    }
}