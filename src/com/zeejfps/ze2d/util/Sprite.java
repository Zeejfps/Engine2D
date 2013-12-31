package com.zeejfps.ze2d.util;

/**
 * Created by Zeejfps on 12/28/13.
 */
public class Sprite {

    private final SpriteSheet spriteSheet;
    public final int[] pixels;
    public final int width, height;

    public Sprite(SpriteSheet spriteSheet, int posX, int posY, int width, int height) {

        this.spriteSheet = spriteSheet;
        this.pixels = new int[width * spriteSheet.tileSize * height * spriteSheet.tileSize];
        this.width = spriteSheet.tileSize * width;
        this.height = spriteSheet.tileSize * height;

        final int startX = posX*spriteSheet.tileSize;
        final int startY = posY*spriteSheet.tileSize;

        for (int i = 0; i < height * spriteSheet.tileSize; i ++) {

            for (int j = 0; j < width * spriteSheet.tileSize; j++) {

                pixels[i* width *spriteSheet.tileSize + j] = spriteSheet.pixels[(startY + i) *spriteSheet.width + startX + j];

            }

        }

    }

}
