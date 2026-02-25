package util.Story;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Dialogue {
    private JLabel backgroundImage;
    private DialogueLine[] lines;
    private int currentLine = 0;
    private boolean hasPlayed = false;

    private void setBackgroundImage(String backgroundImage) {
        try {
            this.backgroundImage = new JLabel(new ImageIcon(ImageIO.read(new File(backgroundImage))));
            this.backgroundImage.setBounds(0,0,720,485);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setLines(DialogueLine[] lines) { this.lines = lines; }
    public void setPlayed() { hasPlayed = true; }
    public boolean wasPlayed() { return hasPlayed; }

    public JLabel getBackgroundImage() { return backgroundImage; }
    public JLabel getCharacter() { return lines[currentLine].getCharacter(); }
    public String getLine() { return lines[currentLine].getLine(); }

    public boolean hasNextLine() { return currentLine < lines.length-1; }
    public void nextLine() { currentLine++; }
}

