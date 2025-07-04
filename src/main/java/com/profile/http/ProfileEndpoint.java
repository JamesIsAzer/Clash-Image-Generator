package com.profile.http;

import static spark.Spark.*;

import com.profile.service.ClashImageService;

public class ProfileEndpoint {
    private static final ClashImageService imageService = new ClashImageService();

    public static void register() {
        path("/profile", () -> {
            get("/:tag/stats", (req, res) -> {
                String tag = req.params(":tag");
                byte[] imageResult = imageService.getProfile(tag);
                res.type("image/png");
                return imageResult;
            });

            get("/:tag/troops", (req, res) -> {
                String tag = req.params(":tag");
                byte[] imageResult = imageService.getTroops(tag);
                res.type("image/png");
                return imageResult;
            });

            get("/:tag/xp", (req, res) -> {
                String tag = req.params(":tag");
                byte[] imageResult = imageService.getXP(tag);
                res.type("image/png");
                return imageResult;
            });
        });
    }
}