package util.Story;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Storyline {

    private static Image keysCheat;

    static {
        try {
            keysCheat = ImageIO.read(new File("res/Screens/Keys.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: replace with relative levels
    public static void playDialogue(int level) {
        if (level == 6 || level == 3 || level == 0) {
            displayKeys();
        }
    }

    public static void displayKeys() {

    }
}
