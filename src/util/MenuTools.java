package util;

import javax.swing.*;
import java.awt.*;

public class MenuTools {

    private static final JLabel keysCheat = new JLabel(ImageLoader.loadImageIcon("res/Screens/Keys.png"));
    private static final JLabel gameSaved = new JLabel(ImageLoader.loadImageIcon("res/Screens/GameSaved.png"));

    static {
        keysCheat.setBounds(Theme.BackgroundBounds);
        gameSaved.setBounds(288, 206, 144,72);
    }

    public static JPanel getKeysPanel() {
        JPanel panel = new JPanel();
        panel.add(keysCheat);
        return panel;
    }

    public static JPanel getGameSavedPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0,0,0,100));
        panel.add(gameSaved);
        return panel;
    }
}
