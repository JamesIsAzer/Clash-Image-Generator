package com.profile.http;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.data.Profile;
import com.profile.render.ProfileImageRenderer;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

public class Interface {

    public static void start(int port) {
        port(port);

        post("/render", (req, res) -> {
            ObjectMapper mapper = new ObjectMapper();
            Profile profile = mapper.readValue(req.body(), Profile.class);
            
            var image = ProfileImageRenderer.render(profile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            res.type("image/png");
            return baos.toByteArray();
        });
    }
}