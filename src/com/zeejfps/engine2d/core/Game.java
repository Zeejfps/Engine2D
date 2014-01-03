package com.zeejfps.engine2d.core;

import com.zeejfps.engine2d.core.util.Clock;
import com.zeejfps.engine2d.core.util.Keyboard;
import com.zeejfps.engine2d.core.util.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zeejfps on 12/28/13.
 */
public abstract class Game {

    private final Context context;
    private final int width, height;
    private String title;
    private int tps, fps;

    private volatile boolean running = false;

    public Game(final int width, final int height, final String title, int tps, int fps) {

        this.width = width; this.height = height;
        this.title = title;
        this.tps = tps; this.fps = fps;

        context = new Context(this);
    }

    public final void start() {

        if (!running) {
            running = true;

            new Thread(context).start();
        }
    }

    public final void stop() {

        if (running)
            running = false;
    }

    public abstract void onStart();

    public abstract void tick(Context context);

    public abstract void render(Screen screen);

    public abstract void onStop();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public int getTPS() {
        return tps;
    }

    public int getFPS() {
        return fps;
    }

    public boolean isRunning() {
        return running;
    }

}
