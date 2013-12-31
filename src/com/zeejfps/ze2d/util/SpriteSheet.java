package com.zeejfps.ze2d.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Zeejfps on 12/28/13.
 */
public class SpriteSheet {

    public final int[] pixels;
    public final int width, height, tileSize;

    private SpriteSheet(BufferedImage image, int tileSize) {

        this.width = image.getWidth();
        this.height = image.getHeight();
        this.tileSize = tileSize;

        pixels = image.getRGB(0, 0, width, height, null, 0, width);

    }

    public static SpriteSheet createSheet(URL imagePath, int tileSize) {

        BufferedImage image;
        try {
            image = ImageIO.read(imagePath);
        } catch (IOException e) {
            System.err.println("Could not load image!");
            e.printStackTrace();
            image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        }

        return new SpriteSheet(image, tileSize);
    }

    public static SpriteSheet createSheet(File imagePath, int tileSize) {

        try {
            return createSheet(imagePath.toURI().toURL(), tileSize);
        } catch (MalformedURLException e) {
            System.err.println("Unable to load spritesheet!");
            e.printStackTrace();
        }
        return null;
    }

    public static SpriteSheet createSheet(String path, String fileName, int tileSize) {

        if (!path.endsWith("/") && !path.endsWith("\\"))
            path += "/";

        File image = new File(path + fileName);
        return createSheet(image, tileSize);
    }

}
