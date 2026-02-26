package util.Story;
import util.ImageLoader;

import javax.swing.*;
import java.util.Objects;

public class DialogueLine {
    private JLabel character;
    private String line;

    private static final JLabel princess = new JLabel(ImageLoader.loadImageIcon("res/Screens/princess.png"));
    private static final JLabel dragon = new JLabel(ImageLoader.loadImageIcon("res/Screens/dragon.png"));
    private static final int charSize = 96;
    private static final int charXPos = 40;
    private static final int charYPos = 340;

    static {
        princess.setBounds(charXPos,charYPos,charSize,charSize);
        dragon.setBounds(charXPos,charYPos,charSize,charSize);
    }

    private void setCharacter(String character) { this.character = (Objects.equals(character, "princess") ? princess : dragon); }
    private void setLine(String line) { this.line = line; }

    public JLabel getCharacter() { return this.character; }
    public String getLine() { return this.line; }
}
