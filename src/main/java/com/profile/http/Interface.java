package com.profile.http;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.client.ClashApiClient;
import com.profile.data.Profile;
import com.profile.render.ProfileImageRenderer;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

public class Interface {

    public static void start(int port) {
        port(port);

        post("/render/profile/:tag", (req, res) -> {
            String tag = req.params(":tag");
            Profile profile = ClashApiClient.fetchProfile(tag);
            
            var image = ProfileImageRenderer.render(profile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            res.type("image/png");
            return baos.toByteArray();
        });
    }
}