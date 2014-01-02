package com.zeejfps.engine2d.core.util;

/**
 * Created by Zeejfps on 12/18/13.
 */
public class Clock {

    public static final long NS_IN_MS = 1000000;
    public static final long NS_IN_SEC = 1000000000;

    private long currTime, lastTime, timePerTick;
    private long runTime = 0;
    private long lastRunTime = 0;

    private float deltaTime, timeScale;

    private boolean running = false;
    private boolean paused = false;

    public Clock() {
        this(1.0f);
    }

    public Clock(float timeScale) {
        this.timeScale = timeScale;
    }

    public void start() {
        if (running) return;
        running = true;
        lastTime = System.nanoTime();
    }

    public void stop() {
        if (!running) return;
        running = false;
    }

    public void tick() {

        if (!running) return;

        currTime = System.nanoTime();
        timePerTick = currTime - lastTime;

        if (!paused) {
            runTime += (long)(timePerTick * timeScale);
        }

        deltaTime = ((float)(runTime - lastRunTime) / (float)NS_IN_SEC) * Math.abs(timeScale);
        lastRunTime = runTime;
        lastTime = currTime;

    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setTimeScale(float timeScale) {
        this.timeScale = timeScale;
    }

    public float getTimeScale() {
        return timeScale;
    }

    public void stepForwards() {
        if (paused)
            runTime += timePerTick * timeScale;
    }

    public void stepBackwards() {
        if (paused)
            runTime -= timePerTick * timeScale;
    }

    public long getRunTimeMs() {
        return runTime / NS_IN_MS;
    }

    public long getRunTimeNs() {
        return runTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void reset() {
        runTime = 0;
    }

    public long getTimePerTick() {
        return timePerTick;
    }

}
