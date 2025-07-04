package com.profile.service.imageGenerator;

import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.profile.data.Profile;
import com.profile.render.TroopShowcaseImageRenderer;

public class TroopShowcaseImageGenerator implements ImageGenerator<Profile>{

    @Override
    public byte[] generateImage(Profile profile) throws Exception {
        var image = TroopShowcaseImageRenderer.render(profile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
