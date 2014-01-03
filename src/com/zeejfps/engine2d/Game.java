package com.zeejfps.engine2d;

/**
 * Created by Zeejfps on 12/28/13.
 */
public abstract class Game {

    private final GameContext context;
    private final int width, height;
    private final String title;
    private final Thread gameThread;
    private int tps, fps;

    private volatile boolean running = false;

    public Game(final int width, final int height, final String title, int tps, int fps) {

        this.width = width; this.height = height;
        this.title = title;
        this.tps = tps; this.fps = fps;

        context = GameContext.getInstance(this);
        gameThread = new Thread(context);
    }

    public final void start() {

        if (!running) {
            running = true;
            gameThread.start();
        }
    }

    public final void stop() {

        if (running)
            running = false;
    }

    public abstract void onStart();

    public abstract void tick(GameContext context);

    public abstract void render(GameScreen screen);

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

    public GameContext getContext() {
        return context;
    }

}
