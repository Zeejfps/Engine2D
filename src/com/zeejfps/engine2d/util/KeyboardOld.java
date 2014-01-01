package com.zeejfps.engine2d.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Zeejfps on 12/28/13.
 */
public class KeyboardOld implements KeyListener {

    private static KeyboardOld instance = null;

    private static boolean[] keysDown = new boolean[256];
    private static boolean[] keysPressed = new boolean[256];
    private static boolean[] keysReleased = new boolean[256];

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

    public static boolean isKeyPressed(int key) {
        boolean b = keysPressed[key];
        keysPressed[key] = false;

        return b;
    }

    public static boolean isKeyReleased(int key) {
        boolean b = keysReleased[key];
        keysReleased[key] = false;

        return b;
    }

    public static boolean isKeyDown(int key) {
        return keysDown[key];
    }

    public static KeyboardOld getInstance() {
        if (instance == null)
            instance = new KeyboardOld();
        return instance;
    }

}
