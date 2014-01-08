package com.zeejfps.engine2d;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

/**
 * User: Zeejfps
 * Date: 1/7/14
 * Time: 10:45 PM
 */
public class Quad extends Renderable {

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

    private Quad() {

        vertexBuffer = glGenBuffers();
        indexBuffer = glGenBuffers();

        final FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.length);
        vertexData.put(vertices);
        vertexData.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        final IntBuffer indexData = BufferUtils.createIntBuffer(indices.length);
        indexData.put(indices);
        indexData.flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void draw() {

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);

    }

    public static Quad createQuad() {
        return new Quad();
    }

}
