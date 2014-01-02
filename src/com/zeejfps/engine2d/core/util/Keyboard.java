package com.zeejfps.engine2d.core.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Zeejfps on 1/1/14.
 */
public class Keyboard implements KeyListener {

    private boolean[] keysDown = new boolean[256];
    private boolean[] keysPressed = new boolean[256];
    private boolean[] keysReleased = new boolean[256];

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (keysDown[e.getKeyCode()]) {
            keysPressed[e.getKeyCode()] = false;
        } else {
            keysDown[e.getKeyCode()] = true;
            keysPressed[e.getKeyCode()] = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysReleased[e.getKeyCode()] = true;
        keysDown[e.getKeyCode()] = false;
    }

    public boolean isKeyPressed(int key) {
        boolean b = keysPressed[key];
        keysPressed[key] = false;

        return b;
    }

    public boolean isKeyReleased(int key) {
        boolean b = keysReleased[key];
        keysReleased[key] = false;

        return b;
    }

    public boolean isKeyDown(int key) {
        return keysDown[key];
    }

}
