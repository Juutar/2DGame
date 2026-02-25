package util.Story;

import tools.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Storyline extends JPanel{

    private ImageIcon keysCheat;
    Dialogue[] dialogues;


    public Storyline() {
        try {
            keysCheat = new ImageIcon(ImageIO.read(new File("res/Screens/Keys.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        String intro = "res/dialogues/intro.json";
        String middle = "res/dialogues/middle.json";
        String outro = "res/dialogues/outro.json";
        dialogues = new Dialogue[] {
                mapper.readValue(new File(intro), Dialogue.class),
                mapper.readValue(new File(middle), Dialogue.class),
                mapper.readValue(new File(outro), Dialogue.class)
        };
    }

    //TODO: replace with relative levels
    public boolean hasDialogue(int level) {
        Dialogue dialogue = getDialogue(level);
        return dialogue != null && !dialogue.wasPlayed();
    }

    public void playDialogue(int level) {
        System.out.println("Playing dialogue: " + level);
        removeAll();
        Dialogue dialogue = getDialogue(level);
        assert dialogue != null;
        JLabel background = new JLabel(dialogue.getBackgroundImage());
        background.setBounds(0, 0, 720, 485);
        add(background);
        repaint();

        dialogue.setPlayed();
    }

    private Dialogue getDialogue(int level) {
        return switch (level) {
            case 6 -> dialogues[0];
            case 3 -> dialogues[1];
            case 0 -> dialogues[2];
            default -> null;
        };
    }
}
