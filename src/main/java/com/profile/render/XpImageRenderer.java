package com.profile.render;

import com.profile.data.Profile;
import com.profile.utils.FontUtils;
import com.profile.utils.ImageManager;
import com.profile.utils.RenderingUtility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XpImageRenderer {
    
    static {
        FontUtils.loadCustomFonts();
    }

    private static final Logger logger = LoggerFactory.getLogger(TroopShowcaseImageRenderer.class);

    public static BufferedImage render(Profile profile) throws IOException {
        logger.info(String.format("Generating xp image for %s", profile.tag));

        int width = 214;
        int height = 214;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        RenderingUtility.addRenderingHints(g);

        BufferedImage xpImage = ImageManager.getCachedImage("xp_thumbnail");
        g.drawImage(xpImage, 0, 0, width, height, null);

        FontUtils.drawClashFont(g, String.valueOf(profile.expLevel), 107, 107, 70, true, Color.WHITE, 6);

        g.dispose();
        return image;        
    }
}
