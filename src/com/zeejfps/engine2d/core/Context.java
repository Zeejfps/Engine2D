package com.zeejfps.engine2d.core;

import com.zeejfps.engine2d.core.util.Clock;
import com.zeejfps.engine2d.core.util.Keyboard;
import com.zeejfps.engine2d.core.util.Mouse;

/**
 * User: Zeejfps
 * Date: 1/3/14
 * Time: 4:44 PM
 */
public class Context implements Runnable {

    private final Game game;
    protected Screen screen;
    public Keyboard keyboard;
    protected Mouse mouse;

    public Context(Game game) {
        this.game = game;
    }

    @Override
    public void run() {

        screen = new Screen(game.getWidth(), game.getHeight(), game.getTitle());
        keyboard = new Keyboard();
        mouse = new Mouse();

        new Loop(game.getTPS(), game.getFPS()).run();

    }

    private class Loop {

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

        public void run() {

            int skippedFrames;
            long runTime = 0;

            ticksClock.start();
            framesClock.start();

            game.onStart();
            while (game.isRunning() && !screen.isCloseRequested()) {

                ticksClock.tick();
                runTime += ticksClock.getTimePerTick();

                skippedFrames = 0;
                while (runTime >= nsPerTick && skippedFrames <= maxSkippedFrames) {

                    keyboard.poll();
                    game.tick(Context.this);
                    runTime -= nsPerTick;
                    skippedFrames ++;

                    ticks++;
                }

                framesClock.tick();
                if (framesClock.getRunTimeNs() >= nsPerFrame) {

                    game.render(screen);

                    framesClock.reset();
                    frames++;
                }

                if (true) {

                    if (ticksClock.getRunTimeMs() >= 1000) {

                        System.out.println("Ticks: " + ticks + " Frames: " + frames);

                        ticks = 0;
                        frames = 0;

                        ticksClock.reset();

                    }

                }

            }
            game.onStop();
            screen.destroy();

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
