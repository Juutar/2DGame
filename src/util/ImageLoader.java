package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Trying to load image: " + path);
            throw new RuntimeException(e);
        }
    }

    public static ImageIcon loadImageIcon(String path) {
        return new ImageIcon(loadImage(path));
    }
}
