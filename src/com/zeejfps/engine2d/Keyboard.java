package com.zeejfps.engine2d;

import org.lwjgl.LWJGLException;

/**
 * Created by Zeejfps on 1/1/14.
 */
public class Keyboard {

    private static final int NUM_OF_KEYS = 256;
    private static Keyboard instance = null;

    private boolean[] keysDown = new boolean[NUM_OF_KEYS];
    private boolean[] keysPressed = new boolean[NUM_OF_KEYS];
    private boolean[] keysReleased = new boolean[NUM_OF_KEYS];

    private Keyboard() {

        try {

            org.lwjgl.input.Keyboard.create();

        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void poll() {

        for (int i = 0; i < NUM_OF_KEYS; i ++) {

            final boolean isDown = org.lwjgl.input.Keyboard.isKeyDown(i);

            if (isDown && keysDown[i]) {

                keysPressed[i] = false;

            } else if (isDown) {

                keysDown[i] = true;
                keysPressed[i] = true;
                keysReleased[i] = false;

            } else if (keysDown[i]){

                keysReleased[i] = true;
                keysDown[i] = false;

            } else {

                keysReleased[i] = false;
                keysPressed[i] = false;

            }

        }

    }

    public boolean isKeyPressed(int key) {
        return keysPressed[key];
    }

    public boolean isKeyReleased(int key) {
        return keysReleased[key];
    }

    public boolean isKeyDown(int key) {
        return keysDown[key];
    }

    public void destroy() {
        org.lwjgl.input.Keyboard.destroy();
    }

    public static Keyboard getInstance() {
        if (instance == null)
            instance = new Keyboard();

        return instance;
    }

}
