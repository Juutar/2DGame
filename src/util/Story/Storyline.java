package util.Story;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Storyline extends JPanel{

    private ImageIcon keysCheat;
    private boolean level0Played = false;
    private boolean level3Played = false;
    private boolean level6Played = false;


    public Storyline() {
        try {
            keysCheat = new ImageIcon(ImageIO.read(new File("res/Screens/Keys.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: replace with relative levels
    public boolean hasDialogue(int level) {
        return level == 6 && !level6Played ||
                level == 3 && !level3Played ||
                level == 0 && !level0Played;
    }

    public void playDialogue() {
        JLabel background = new JLabel(keysCheat);
        background.setBounds(0, 0, 720, 485);

        add(background);
        if (!level6Played) level6Played = true;
        else if (!level3Played) level3Played = true;
        else if (!level0Played) level0Played = true;
    }
}
