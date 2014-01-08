package com.zeejfps.engine2d.util;

import com.zeejfps.engine2d.Shader;

import java.awt.image.BufferedImage;

/**
 * Created by Zeejfps on 1/1/14.
 */
public class SpriteSheet {

    private final BufferedImage image;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    public Sprite getSprite(int xStart, int yStart, int width, int height) {

        final int[] pixels = image.getRGB(xStart, yStart, width, height, null, 0, width);

        return new Sprite(pixels, Shader.createProgram("basicVert.vert", "basicFrag.frag"), width, height);

    }

}
