package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MenuTools extends JPanel {

    private static JLabel keysCheat;

    public MenuTools() {
        try {
            keysCheat = new JLabel(new ImageIcon(ImageIO.read(new File("res/Screens/Keys.png"))));
            keysCheat.setBounds(0,0,720,485);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void displayKeys() {
        removeAll();
        add(keysCheat);
    }
}
