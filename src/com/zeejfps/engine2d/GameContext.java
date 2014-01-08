package com.zeejfps.engine2d;

import com.zeejfps.engine2d.util.Clock;

/**
 * User: Zeejfps
 * Date: 1/3/14
 * Time: 4:44 PM
 */
public class GameContext implements Runnable {

    private static GameContext instance = null;

    private final Game game;
    public GameScreen screen;
    public GameInput input;

    private GameContext(Game game) {
        this.game = game;
    }

    @Override
    public void run() {

        if (!game.isRunning()) {

            game.setRunning(true);
            screen = GameScreen.getInstance(game.getWidth(), game.getHeight(), game.getTitle());
            input = GameInput.getInstance();

            new GameLoop(game.getTPS(), game.getFPS()).run();

        }

    }

    public static GameContext getInstance(Game game) {

        if (instance == null)
            instance = new GameContext(game);

        return instance;
    }

    private class GameLoop {

        private final int maxSkippedFrames = 9;
        private final Clock ticksClock = new Clock();
        private final Clock framesClock = new Clock();

        private float nsPerTick;
        private float nsPerFrame;

        private int ticks = 0;
        private int frames = 0;

        public GameLoop(int tps, int fps) {

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

                    input.poll();
                    game.tick(game.getContext());
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
            input.destroy();

            ticksClock.stop();
            framesClock.stop();

        }

        public void setFps(int fps) {
            if (fps < 1)
                nsPerFrame = (float)Clock.NS_PER_SEC / 9999999f;
            else
                nsPerFrame = (float)Clock.NS_PER_SEC / (float)fps;
        }

        public void setTps(int tps) {
            if (tps < 1)
                nsPerTick = (float)Clock.NS_PER_SEC / 9999f;
            else
                nsPerTick = (float)Clock.NS_PER_SEC / (float)tps;
        }

    }

}
