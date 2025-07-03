package com.profile.http;

import static spark.Spark.*;

import com.profile.service.ImageFactory;
import com.profile.service.imageGenerator.ProfileImageGenerator;
import com.profile.service.imageGenerator.TroopShowcaseImageGenerator;
import com.profile.service.imageGenerator.XpImageGenerator;

public class ProfileEndpoint {
    private static final ProfileImageGenerator profileImageGenerator = new ProfileImageGenerator();
    private static final TroopShowcaseImageGenerator troopShowcaseImageGenerator = new TroopShowcaseImageGenerator();
    private static final XpImageGenerator xpImageGenerator = new XpImageGenerator();

    public static void register() {
        path("/profile", () -> {
            get("/:tag/stats", (req, res) -> {
                String tag = req.params(":tag");
                byte[] imageResult = ImageFactory.getCachedRender("stats", tag, profileImageGenerator::generateImage);
                res.type("image/png");
                return imageResult;
            });

            get("/:tag/troops", (req, res) -> {
                String tag = req.params(":tag");
                byte[] imageResult = ImageFactory.getCachedRender("troops", tag, troopShowcaseImageGenerator::generateImage);
                res.type("image/png");
                return imageResult;
            });

            get("/:tag/xp", (req, res) -> {
                String tag = req.params(":tag");
                byte[] imageResult = ImageFactory.getCachedRender("xp", tag, xpImageGenerator::generateImage);
                res.type("image/png");
                return imageResult;
            });
        });
    }
}