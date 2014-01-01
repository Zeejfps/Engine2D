package com.zeejfps.ze2d;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Zeejfps on 12/29/13.
 */
public class Screen extends Canvas implements Renderable {

    private final int x, y, width, height, scale, minX, minY, maxX, maxY;
    private BufferedImage drawImage;
    private int[] pixels;
    private int clearColor = 0x000000;

    public Screen(int x, int y, int width, int height, int scale) {

        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.scale = scale;

        drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)drawImage.getRaster().getDataBuffer()).getData();

        minX = x - width/2;
        minY = y - height/2;

        maxX = x + width/2;
        maxY = y + height/2;

    }

    public void render(Renderable renderable) {

        final int localX = renderable.getX() - renderable.getWidth()/2 - minX;
        final int localY = renderable.getY() - renderable.getHeight()/2 - minY;

        for (int i = 0; i < renderable.getHeight(); i++) {

            for (int j = 0; j < renderable.getWidth(); j++) {

                int pixelPosX = localX + j;
                if (pixelPosX < minX || pixelPosX >= maxX)
                    continue;

                int pixelPosY = localY + i;
                if (pixelPosY < minY || pixelPosY >= maxY) {
                    continue;
                }

                pixels[pixelPosY*getWidth() + pixelPosX] = renderable.getPixels()[i* renderable.getWidth() + j];

            }

        }

    }

    public void setClearColor(int color) {
        clearColor = color;
    }

    public int getClearColor() {
        return clearColor;
    }

    public void clear() {

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = clearColor;
        }

    }

    public void update() {

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(drawImage, 0, 0, getWidth(), getHeight(), null);
        g.dispose();

        bs.show();

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * scale, height * scale);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int[] getPixels() {
        return pixels;
    }
}
