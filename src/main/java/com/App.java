package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.profile.http.Interface;
import com.profile.utils.ImageManager;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting app...");
        Interface.start(34827);
        ImageManager.preloadAllImages();
        logger.info("App started successfully!");
    }
}