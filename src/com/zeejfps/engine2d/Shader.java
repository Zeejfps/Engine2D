package com.zeejfps.engine2d;

import com.zeejfps.engine2d.util.math.Vector2f;
import com.zeejfps.engine2d.util.math.Vector3f;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

/**
 * User: Zeejfps
 * Date: 1/6/14
 * Time: 10:36 PM
 */
public abstract class Shader {

    public static final String DEFAULT_DIR = "res/shaders/";

    public static int createProgram(String vertexShader, String fragmentShader) {

        int program = glCreateProgram();

        int vert = glCreateShader(GL_VERTEX_SHADER);
        int frag = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vert, Shader.loadSource(vertexShader));
        glCompileShader(vert);
        if (glGetShaderi(vert, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Failed to compile Vertex Shader!");
            System.exit(1);
        }

        glShaderSource(frag, Shader.loadSource(fragmentShader));
        glCompileShader(frag);
        if (glGetShaderi(frag, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Failed to compile Fragment Shader!");
            System.exit(1);
        }

        glAttachShader(program, vert);
        glAttachShader(program, frag);
        glLinkProgram(program);
        glValidateProgram(program);

        return program;
    }

    private static String loadSource(String fileName) {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(DEFAULT_DIR + fileName));
            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line);

            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            System.err.println("Could not find file: " + fileName);
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        return null;
    }

    public static int getUniformLocation(int program, String uniform) {

        int location = glGetUniformLocation(program, uniform);
        if (location == -1) {
            System.err.println("Could not find uniform: " + uniform);
            System.exit(1);
        }

        return location;
    }

    public static int getAttribLocation(int program, String attribute) {

        int location = glGetAttribLocation(program, attribute);
        if (location == -1) {
            System.err.println("Could not find attribute: " + attribute);
            System.exit(1);
        }

        return location;
    }

}
