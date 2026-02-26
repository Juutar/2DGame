package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuTools {

    private static JLabel keysCheat;
    private static JLabel gameSaved;

    static {
        try {
            keysCheat = new JLabel(new ImageIcon(ImageIO.read(new File("res/Screens/Keys.png"))));
            keysCheat.setBounds(0,0,720,485);
            gameSaved = new JLabel(new ImageIcon(ImageIO.read(new File("res/Screens/GameSaved.png"))));
            gameSaved.setBounds(288, 206, 144,72);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JPanel getKeysPanel() {
        JPanel panel = new JPanel();
        panel.add(keysCheat);
        return panel;
    }

    public static JPanel getGameSavedPanel() {
        System.out.println("called twice");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0,0,0,100));
        panel.add(gameSaved);
        return panel;
    }
}
