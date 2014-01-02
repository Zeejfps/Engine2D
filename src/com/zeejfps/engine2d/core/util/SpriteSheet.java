package com.zeejfps.engine2d.core.util;

import java.awt.image.BufferedImage;

/**
 * Created by Zeejfps on 1/1/14.
 */
public class SpriteSheet {

    public final int width, height;
    public final int tileWidth, tileHeight;
    public final BufferedImage image;

    public SpriteSheet(BufferedImage image, int tileWidth, int tileHeight) {

        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

    }

}
