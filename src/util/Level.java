package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Level {
    private int id;
    private Image background_tile;

    public int getId() { return id; }

    public Image getBackground_tile() { return background_tile; }

    private void setId(int id) { this.id = id; }

    private void setBackground(String background) throws IOException {
        this.background_tile = ImageIO.read(new File(background));
    }
}
