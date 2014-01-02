package com.zeejfps.engine2d.core;

import com.zeejfps.engine2d.core.util.Renderable;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Zeejfps on 12/29/13.
 */
public class Screen extends Canvas {

    private final int width, height, scale;
    private final BufferedImage drawImage;
    private int[] pixels;
    private int clearColor = 0x000000;

    private BufferStrategy bs = null;

    public Screen(int width, int height, int scale) {

        this.width = width; this.height = height;
        this.scale = scale;

        drawImage = new BufferedImage(width*scale, height*scale, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)drawImage.getRaster().getDataBuffer()).getData();
    }

    public void render(Renderable obj) {

        final int localX = obj.getOffsetX();
        final int localY = obj.getOffsetY();

        int pixelPosX;
        int pixelPosY;
        for (int i = 0; i < obj.getHeight(); i++) {

            for (int j = 0; j < obj.getWidth(); j++) {

                pixelPosX = localX + j;
                if (pixelPosX < 0 || pixelPosX >= getWidth())
                    continue;

                pixelPosY = localY + i;
                if (pixelPosY < 0 || pixelPosY >= getHeight()) {
                    continue;
                }

                for (int k = 0; k < scale; k++) {

                    for (int n = 0; n < scale; n++) {
                        pixels[(localY + i*scale + n)*width*scale + localX + j*scale + k] = obj.getPixels()[i* obj.getWidth() + j];
                    }

                }

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

        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(drawImage, 0, 0, width*scale, height*scale, null);
        g.dispose();

        bs.show();

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * scale, height * scale);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
