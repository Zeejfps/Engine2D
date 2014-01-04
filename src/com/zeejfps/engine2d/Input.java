package com.zeejfps.engine2d;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Created by Zeejfps on 1/1/14.
 */
public class Input {

    private static final int NUM_OF_KEYS = 256;
    private static Input instance = null;

    private boolean[] keysDown = new boolean[NUM_OF_KEYS];
    private boolean[] keysPressed = new boolean[NUM_OF_KEYS];
    private boolean[] keysReleased = new boolean[NUM_OF_KEYS];

    private int mouseX=0 , mouseY=0;
    private boolean mouseMoved = false;

    private Input() {

        try {

            Keyboard.create();
            Mouse.create();

        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void poll() {

        final int tempX = Mouse.getX();
        final int tempY = Mouse.getY();

        if (tempX != mouseX || tempY != mouseY) {
            mouseX = tempX;
            mouseY = tempY;
            mouseMoved = true;
        } else {
            mouseMoved = false;
        }

        for (int i = 0; i < NUM_OF_KEYS; i ++) {

            final boolean isDown = Keyboard.isKeyDown(i);

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

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean mouseMoved() {
        return mouseMoved;
    }

    public boolean isMouseButtonDown(int button) {

        return Mouse.isButtonDown(button);
    }

    public void destroy() {
        Keyboard.destroy();
        Mouse.destroy();
    }

    public static Input getInstance() {
        if (instance == null)
            instance = new Input();

        return instance;
    }

}
