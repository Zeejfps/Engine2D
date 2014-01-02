package com.zeejfps.engine2d.core.util;

/**
 * Created by Zeejfps on 1/2/14.
 */
public class Sprite {

    public final int pxWidth, pxHeight;
    public final int[] pixels;

    public Sprite(SpriteSheet spriteSheet, int locX, int locY, int pxWidth, int pxHeight) {

        this.pxWidth = pxWidth;
        this.pxHeight = pxHeight;
        pixels = loadPixels(spriteSheet, locX, locY, pxWidth, pxHeight);

    }

    private int[] loadPixels(SpriteSheet spriteSheet, int locX, int locY, int pxWidth, int pxHeight) {

        final int startX = locX * spriteSheet.tileWidth;
        final int startY = locY * spriteSheet.tileHeight;

        return spriteSheet.image.getRGB(startX, startY, pxWidth, pxHeight, null, 0, pxWidth);

    }

}
