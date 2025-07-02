package com.profile;

import com.profile.http.Interface;
import com.profile.utils.ImageManager;

public class App {
    public static void main(String[] args) {
        Interface.start(8080);
        ImageManager.preloadAllImages();
    }
}