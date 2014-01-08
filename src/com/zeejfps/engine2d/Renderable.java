package com.zeejfps.engine2d;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * User: Zeejfps
 * Date: 1/7/14
 * Time: 10:01 AM
 */
public class Renderable {

    private int vbo;

    public Renderable() {

        vbo = glGenBuffers();

        makeBuffer(2, new float[] {2,3});

    }

    public static int makeBuffer(int type, float[] data) {

        final FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
        fb.put(data);
        fb.flip();

        int buffer = glGenBuffers();
        glBindBuffer(type, buffer);
        glBufferData(type, fb, GL_STATIC_DRAW);
        glBindBuffer(type, 0);

        return buffer;
    }

    public static int makeBuffer(int type, int[] data) {

        final IntBuffer ib = BufferUtils.createIntBuffer(data.length);
        ib.put(data);
        ib.flip();

        int buffer = glGenBuffers();
        glBindBuffer(type, buffer);
        glBufferData(type, ib, GL_STATIC_DRAW);
        glBindBuffer(type, 0);

        return buffer;
    }

    public static int makeTexture(String dir) {

        BufferedImage image = null;
        try {

            image = ImageIO.read(new URL(dir));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            final IntBuffer ib = BufferUtils.createIntBuffer(pixels.length);
            ib.put(pixels);
            ib.flip();

            int texture = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texture);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.getWidth(), image.getHeight(), 0, GL_RGB, GL_INT, ib);
            glBindTexture(GL_TEXTURE_2D, 0);


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }

    public static int makeTexture(int[] pixels, int width, int height) {

        final IntBuffer ib = BufferUtils.createIntBuffer(pixels.length);
        ib.put(pixels);
        ib.flip();

        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_INT, ib);
        glBindTexture(GL_TEXTURE_2D, 0);

        return 0;
    }

}
