package com.zeejfps.ze2d;

import com.zeejfps.ze2d.util.Clock;
import com.zeejfps.ze2d.util.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

/**
 * Created by Zeejfps on 12/28/13.
 */
public abstract class GameShell extends Canvas {

    private final int width, height, scale;
    private JFrame gameWindow;
    private Engine gameEngine;

    private volatile boolean running = false;

    private final ArrayList<GameView> views = new ArrayList<GameView>();
    private BufferedImage drawImage;
    private int[] pixels;

    private int ticks = 0;
    private int frames = 0;
    protected boolean displayStats = true;

    public GameShell(final int width, final int height, final int scale, final String title, int tps, int fps) {

        this.width = width;
        this.height = height;
        this.scale = scale;

        gameEngine = new Engine(Clock.NS_IN_SEC / tps, Clock.NS_IN_SEC / fps);
        drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)drawImage.getRaster().getDataBuffer()).getData();

        initDisplay(title);
    }

    public final synchronized void start() {

        if (running) return;

        running = true;
        new Thread(gameEngine).start();

    }

    public final synchronized void stop() {

        if (!running) return;

        running = false;

    }

    public void add(GameView view) {
        views.add(view);
    }

    private void initDisplay(String title) {

        this.addKeyListener(Keyboard.getInstance());

        gameWindow = new JFrame(title);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.add(this, BorderLayout.CENTER);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        this.requestFocusInWindow();

    }

    private void display() {

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        for (GameView view : views) {

            for (int i = 0; i < view.getHeight(); i++) {

                for (int j = 0; j < view.getWidth(); j++) {

                    pixels[(i + view.getPosY())*width + j] = view.pixels[i*view.getWidth() + j];

                }

            }

        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(drawImage, 0, 0, getWidth(), getHeight(), null);
        g.dispose();

        bs.show();

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * scale, height * scale);
    }

    public void setFps(int fps) {
        if (fps < 1)
            gameEngine.nsPerFrame = (float)Clock.NS_IN_SEC / 9999f;
        else
            gameEngine.nsPerFrame = (float)Clock.NS_IN_MS / (float)fps;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void onStart();

    public abstract void tick();

    public abstract void render();

    public abstract void onStop();

    private class Engine implements Runnable {

        private final int maxSkippedFrames = 9;
        private final Clock ticksClock = new Clock();
        private final Clock framesClock = new Clock();

        private float nsPerTick;
        private float nsPerFrame;

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

                    tick();
                    runTime -= nsPerTick;
                    skippedFrames ++;

                    ticks++;
                }

                framesClock.tick();
                if (framesClock.getRunTimeNs() >= nsPerFrame) {

                    render();
                    display();

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
