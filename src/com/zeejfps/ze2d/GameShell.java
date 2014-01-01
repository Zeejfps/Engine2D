package com.zeejfps.ze2d;

import com.zeejfps.ze2d.util.Clock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zeejfps on 12/28/13.
 */
public abstract class GameShell {

    private final JFrame gameWindow;
    private final Screen gameScreen;
    private final Context gameContext;
    private final Engine gameEngine;

    protected boolean displayStats = true;
    private volatile boolean running = false;

    public GameShell(final int width, final int height, final int scale, final String title, int tps, int fps) {

        gameEngine = new Engine(Clock.NS_IN_SEC / tps, Clock.NS_IN_SEC / fps);

        gameWindow = new JFrame(title);
        gameScreen = new Screen(0, 0, width, height, scale);
        gameContext = new Context();

        initDisplay(title);
    }

    public final synchronized void start() {

        if (!running) {
            running = true;
            new Thread(gameEngine).start();
        }
    }

    public final synchronized void stop() {

        if (running)
            running = false;
    }

    private void initDisplay(String title) {

        gameScreen.addKeyListener(gameContext.keyboard);
        gameScreen.addMouseListener(gameContext.mouse);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.add(gameScreen, BorderLayout.CENTER);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

    }

    public void setFps(int fps) {
        if (fps < 1)
            gameEngine.nsPerFrame = (float)Clock.NS_IN_SEC / 9999f;
        else
            gameEngine.nsPerFrame = (float)Clock.NS_IN_MS / (float)fps;
    }

    public Screen getScreen() {
        return gameScreen;
    }

    public Context getContext() {
        return gameContext;
    }

    public abstract void onStart();

    public abstract void tick(Context context);

    public abstract void render(Screen screen);

    public abstract void onStop();

    private class Engine implements Runnable {

        private final int maxSkippedFrames = 9;
        private final Clock ticksClock = new Clock();
        private final Clock framesClock = new Clock();

        private float nsPerTick;
        private float nsPerFrame;

        private int ticks = 0;
        private int frames = 0;

        public Engine(float nsPerTick, float nsPerFrame) {
            this.nsPerTick = nsPerTick;
            this.nsPerFrame = nsPerFrame;
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

                    tick(gameContext);
                    runTime -= nsPerTick;
                    skippedFrames ++;

                    ticks++;
                }

                framesClock.tick();
                if (framesClock.getRunTimeNs() >= nsPerFrame) {

                    render(gameScreen);

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

    }

}
