package util.Story;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DialogueLine {
    private JLabel character;
    private String line;

    private static JLabel princess;
    private static JLabel dragon;
    private static int charSize = 96;
    private static int charXPos = 40;
    private static int charYPos = 340;

    static {
        try {
            princess = new JLabel(new ImageIcon(ImageIO.read(new File("res/Screens/princess.png"))));
            dragon = new JLabel(new ImageIcon(ImageIO.read(new File("res/Screens/dragon.png"))));

            princess.setBounds(charXPos,charYPos,charSize,charSize);
            dragon.setBounds(charXPos,charYPos,charSize,charSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCharacter(String character) { this.character = (Objects.equals(character, "princess") ? princess : dragon); }
    private void setLine(String line) { this.line = line; }

    public JLabel getCharacter() { return this.character; }
    public String getLine() { return this.line; }
}
