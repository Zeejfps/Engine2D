package com.zeejfps.engine2d.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Zeejfps on 12/29/13.
 */
public class Screen {

    public Screen(int width, int height, String title) {

        try {

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            Display.create();

        } catch (LWJGLException e) {
            System.err.println("Could not create the display!");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void clear(int mask) {
        glClear(mask);
    }

    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    public void update() {
        Display.update();
    }

    public int getWidth() {
        return Display.getWidth();
    }

    public int getHeight() {
        return Display.getHeight();
    }

    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public void destroy() {
        Display.destroy();
    }

}
