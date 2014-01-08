package com.zeejfps.engine2d;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;

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

    }

    public static int createVertexBuffer(float[] vertices) {

        final FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
        fb.put(vertices);
        fb.flip();

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    public static int createIndexBuffer(int[] indices) {

        final IntBuffer fb = BufferUtils.createIntBuffer(indices.length);
        fb.put(indices);
        fb.flip();

        int ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        return ibo;
    }

}
