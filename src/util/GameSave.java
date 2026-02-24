package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameSave {
    public static final String FILENAME = "res/save.txt";

    public static int loadGame() {
        try {
            Scanner scanner = new Scanner(new File(FILENAME));
            if (scanner.hasNextInt()) return scanner.nextInt();
            else return -1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void saveGame(int level) {
        try {
            FileWriter writer = new FileWriter(FILENAME);
            writer.write(level);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
