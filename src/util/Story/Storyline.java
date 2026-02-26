package util.Story;
import util.ImageLoader;
import util.Theme;
import tools.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;

public class Storyline extends JPanel{

    private Dialogue[] dialogues;
    private int currentDialogue;
    private JLabel textBox;

    public Storyline() {
        textBox = new JLabel(ImageLoader.loadImageIcon("res/Screens/TextBanner.png"));
        textBox.setBounds(0, 305, 720, 180);

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

    public boolean hasDialogue(int level) {
        int index = getDialogue(level);
        return isValidDialogue(index) && !dialogues[index].wasPlayed();
    }

    public boolean isDialogueFinished() { return !dialogues[currentDialogue].hasNextLine(); }

    public void nextLine() {
        dialogues[currentDialogue].nextLine();
        displayLine();
    }

    public void playDialogue(int level) {
        currentDialogue = getDialogue(level);
        assert isValidDialogue(currentDialogue);
        dialogues[currentDialogue].setPlayed();
        displayLine();
    }

    private void displayLine() {
        removeAll();
        Dialogue dialogue = dialogues[currentDialogue];
        add(dialogue.getCharacter());
        add(getText(dialogue));
        add(textBox); //displayed on top if added before
        add(dialogue.getBackgroundImage());

        repaint();
    }

    public JTextArea getText(Dialogue dialogue) {
        JTextArea text = new JTextArea(dialogue.getLine());
        text.setBounds(156,350,500,100);
        text.setBackground(Theme.Yellow);
        text.setForeground(Theme.Purple);
        text.setFont(Theme.GameFont);
        text.setEditable(false);
        text.setLineWrap(true);
        return text;
    }

    private int getDialogue(int level) {
        return switch (level) {
            case 6 -> 0;
            case 3 -> 1;
            case 0 -> 2;
            default -> -1;
        };
    }

    private boolean isValidDialogue(int index) { return index >= 0 && index < dialogues.length; }
}
