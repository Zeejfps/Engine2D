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
    private Engine engine;
    private Clock engineClock = new Clock();
    private Clock ticksClock = new Clock();
    private Clock framesClock = new Clock();

    private volatile boolean running = false;

    private float fps;

    private static BufferedImage drawImage;
    private static int[] pixels;
    private final ArrayList<GameView> views = new ArrayList<GameView>();

    private int ticks = 0;
    private int frames = 0;

    public GameShell(final int width, final int height, final int scale, final String title, int tps, int fps) {

        this.width = width;
        this.height = height;
        this.scale = scale;
        setFps(fps);

        engine = new Engine(Clock.NS_IN_SEC / tps);

        drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)drawImage.getRaster().getDataBuffer()).getData();

        initDisplay(title);
    }

    public final synchronized void start() {

        if (running) return;

        running = true;
        engineClock.start();
        ticksClock.start();
        framesClock.start();
        new Thread(engine).start();

    }

    public final synchronized void stop() {

        if (!running) return;

        engineClock.stop();
        ticksClock.stop();
        framesClock.stop();
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

    private void tick() {

        if (ticksClock.getRunTimeMs() >= 1000) {

            System.out.println("Ticks: " + ticks + " Frames: " + frames);
            ticksClock.reset();
            ticks = 0;
            frames = 0;

        }

        update();
    }

    private void render() {

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        render();
        Graphics g = bs.getDrawGraphics();
        g.drawImage(drawImage, 0, 0, getWidth(), getHeight(), null);
        g.dispose();

        bs.show();

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
            fps = 9999;
        else
            this.fps = (float)Clock.NS_IN_SEC/(float)fps;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void onStart();

    public abstract void onStop();

    public abstract void update();

    private class Engine implements Runnable {

        private int MAX_FRAME_SKIP = 9;
        private final float nsPerTick;

        public Engine(float nsPerTick) {
            this.nsPerTick = nsPerTick;
        }

        @Override
        public void run() {

            int skippedFrames;
            long runTime = 0;

            onStart();
            while (running) {

                ticksClock.tick();
                runTime += ticksClock.getTimePerTick();

                skippedFrames = 0;
                while (runTime >= nsPerTick && skippedFrames <= MAX_FRAME_SKIP) {

                    tick();
                    runTime -= nsPerTick;
                    skippedFrames ++;

                    ticksClock.reset();
                    ticks++;
                }

                framesClock.tick();
                if (framesClock.getRunTimeNs() >= fps) {

                    render();
                    display();

                    framesClock.reset();
                    frames++;
                }

            }
            onStop();

        }

    }

}
