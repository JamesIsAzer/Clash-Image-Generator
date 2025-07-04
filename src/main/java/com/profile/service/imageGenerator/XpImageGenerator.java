package com.profile.service.imageGenerator;

import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.profile.data.Profile;
import com.profile.render.XpImageRenderer;

public class XpImageGenerator implements ImageGenerator<Profile>{

    @Override
    public byte[] generateImage(Profile profile) throws Exception {
        var image = XpImageRenderer.render(profile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
