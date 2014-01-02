package com.zeejfps.engine2d.core;

import com.zeejfps.engine2d.core.util.Clock;
import com.zeejfps.engine2d.core.util.Keyboard;
import com.zeejfps.engine2d.core.util.Mouse;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zeejfps on 12/28/13.
 */
public abstract class Game {

    private final JFrame gameWindow;
    private final Loop gameLoop;
    public final Screen screen;
    public final Keyboard keyboard;
    public final Mouse mouse;

    protected boolean displayStats = true;
    private volatile boolean running = false;

    public Game(final int width, final int height, final int scale, final String title, int tps, int fps) {

        gameLoop = new Loop(tps, fps);
        gameWindow = new JFrame(title);

        screen = new Screen(width, height, scale);
        keyboard = new Keyboard();
        mouse = new Mouse();

        initDisplay(title);
    }

    public final synchronized void start() {

        if (!running) {
            running = true;
            new Thread(gameLoop).start();
        }
    }

    public final synchronized void stop() {

        if (running)
            running = false;
    }

    private void initDisplay(String title) {

        screen.addKeyListener(keyboard);
        screen.addMouseListener(mouse);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.add(screen, BorderLayout.CENTER);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        screen.requestFocusInWindow();
    }

    public abstract void onStart();

    public abstract void tick();

    public abstract void render(Screen screen);

    public abstract void onStop();

    private class Loop implements Runnable {

        private final int maxSkippedFrames = 9;
        private final Clock ticksClock = new Clock();
        private final Clock framesClock = new Clock();

        private float nsPerTick;
        private float nsPerFrame;

        private int ticks = 0;
        private int frames = 0;

        public Loop(int tps, int fps) {

            setTps(tps);
            setFps(fps);
        }

        @Override
        public void run() {

            int skippedFrames;
            long runTime = 0;

            ticksClock.start();
            framesClock.start();

            onStart();
            while (running) {

                ticksClock.tick();
                runTime += ticksClock.getTimePerTick();

                skippedFrames = 0;
                while (runTime >= nsPerTick && skippedFrames <= maxSkippedFrames) {

                    tick();
                    runTime -= nsPerTick;
                    skippedFrames ++;

                    ticks++;
                }

                framesClock.tick();
                if (framesClock.getRunTimeNs() >= nsPerFrame) {

                    render(screen);

                    framesClock.reset();
                    frames++;
                }

                if (displayStats) {

                    if (ticksClock.getRunTimeMs() >= 1000) {

                        System.out.println("Ticks: " + ticks + " Frames: " + frames);

                        ticks = 0;
                        frames = 0;

                        ticksClock.reset();

                    }

                }

            }
            onStop();

            ticksClock.stop();
            framesClock.stop();

        }

        public void setFps(int fps) {
            if (fps < 1)
                nsPerFrame = (float)Clock.NS_IN_SEC / 9999999f;
            else
                nsPerFrame = (float)Clock.NS_IN_SEC / (float)fps;
        }

        public void setTps(int tps) {
            if (tps < 1)
                nsPerTick = (float)Clock.NS_IN_SEC / 9999f;
            else
                nsPerTick = (float)Clock.NS_IN_SEC / (float)tps;
        }

    }

}
