package com.zeejfps.ze2d;

import com.zeejfps.ze2d.util.Clock;

/**
 * Created by Zeejfps on 12/29/13.
 */
public class GameView {

    private final int width, height, minX, minY, maxX, maxY, posX, posY;
    public final int[] pixels;

    public GameView(int posX, int posY, int width, int height) {

        this.posX = posX;       this.posY = posY;
        this.width = width;     this.height = height;

        pixels = new int[width * height];

        minX = posX - width/2;
        minY = posY - height/2;

        maxX = posX + width/2;
        maxY = posY + height/2;

    }

    public void update() {

    }
    /*
    public void render(Renderable obj) {

        final int localX = obj.getPosX() - obj.getWidth()/2 - minX;
        final int localY = obj.getPosY() - obj.getHeight()/2 - minY;

        for (int i = 0; i < obj.getHeight(); i++) {

            for (int j = 0; j < obj.getWidth(); j++) {

                int pixelPosX = localX + j;
                if (pixelPosX < minX || pixelPosX >= maxX)
                    continue;

                int pixelPosY = localY + i;
                if (pixelPosY < minY || pixelPosY >= maxY) {
                    continue;
                }

                pixels[pixelPosY*width + pixelPosX] = obj.getPixels()[i*obj.getWidth() + j];

            }

        }

    }
*/

    public void clear() {

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0x000000;
        }

    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
