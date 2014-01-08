package com.zeejfps.engine2d.util;

import com.zeejfps.engine2d.Renderable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Zeejfps on 1/8/14.
 */
public class Sprite {

    private static float[] vertices = {
            -0.5f,  0.5f,  0.0f, // 0
            -0.5f, -0.5f,  0.0f, // 1
            0.5f, -0.5f,  0.0f, // 2
            0.5f,  0.5f,  0.0f  // 3
    };

    private static int[] indices = {
            0, 1, 2,
            2, 3, 0
    };

    private int vertexBuffer;
    private int indexBuffer;
    private int textureBuffer;
    private int shaderProgram;

    private final int width, height;

    public Sprite(int[] pixels, int shaderProgram, int width, int height) {

        this.width = width;
        this.height = height;

        vertexBuffer = Renderable.makeBuffer(GL_ARRAY_BUFFER, vertices);
        indexBuffer = Renderable.makeBuffer(GL_ELEMENT_ARRAY_BUFFER, indices);
        textureBuffer = Renderable.makeTexture(pixels, width, height);

    }

    public void draw() {

        glUseProgram(shaderProgram);

        glBindTexture(GL_TEXTURE_2D, textureBuffer);

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);

    }

}
