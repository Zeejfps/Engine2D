package com.zeejfps.engine2d.core.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Zeejfps on 1/1/14.
 */
public class ResourceLoader {

    public static SpriteSheet loadSpriteSheet(URL url, int tileWidth, int tileHeight) {

        try {

            BufferedImage image = ImageIO.read(url);
            return new SpriteSheet(image, tileWidth, tileHeight);

        } catch (IOException e) {
            System.err.println("Could not load spriteSheet!");
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

}
