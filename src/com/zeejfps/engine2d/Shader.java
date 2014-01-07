package com.zeejfps.engine2d;

import com.zeejfps.engine2d.util.math.Vector2f;
import com.zeejfps.engine2d.util.math.Vector3f;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

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

    public static String loadSource(String fileName) {

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
            System.err.println("Incorrect file name!");
            System.err.println("Usage: basicVert.ver");
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

    public static void setUniformInt(int program, String uniform, int value) {
        glUniform1i(Shader.getUniform(program, uniform), value);
    }

    public static void setUniformFlt(int program, String uniform, float value) {
        glUniform1f(Shader.getUniform(program, uniform), value);
    }

    public static void setUniformVec2(int program, String uniform, Vector2f value) {
        glUniform2f(Shader.getUniform(program, uniform), value.x, value.y);
    }

    public static void setUniformVec3(int program, String uniform, Vector3f value) {
        glUniform3f(Shader.getUniform(program, uniform), value.x, value.y, value.z);
    }

    public static int getUniform(int program, String uniform) {

        int location = glGetUniformLocation(program, uniform);
        if (location == -1) {
            System.err.println("Could not find uniform: " + uniform);
            System.exit(1);
        }

        return location;
    }

}
