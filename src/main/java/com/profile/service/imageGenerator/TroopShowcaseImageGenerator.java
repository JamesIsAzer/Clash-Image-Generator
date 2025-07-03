package com.profile.service.imageGenerator;

import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.profile.client.ClashApiClient;
import com.profile.data.Profile;
import com.profile.render.TroopShowcaseImageRenderer;

public class TroopShowcaseImageGenerator implements ImageGenerator<String>{

    @Override
    public byte[] generateImage(String tag) throws Exception {
        Profile profile = ClashApiClient.fetchProfile(tag);
        var image = TroopShowcaseImageRenderer.render(profile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
