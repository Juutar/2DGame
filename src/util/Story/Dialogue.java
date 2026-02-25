package util.Story;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Dialogue {
    private ImageIcon backgroundImage;
    private DialogueLine[] lines;
    private boolean hasPlayed = false;

    private void setBackgroundImage(String backgroundImage) {
        try {
            this.backgroundImage = new ImageIcon(ImageIO.read(new File(backgroundImage)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setLines(DialogueLine[] lines) { this.lines = lines; }
    public void setPlayed() { hasPlayed = true; }
    public boolean wasPlayed() { return hasPlayed; }

    public ImageIcon getBackgroundImage() { return backgroundImage; }
}

