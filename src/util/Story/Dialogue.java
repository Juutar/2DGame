package util.Story;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Dialogue extends JPanel{
    private ImageIcon backgroundImage;
    private DialogueLine[] lines;

    public Dialogue() {
        setLayout(null);
        setBounds(0, 0, 720, 528);

        JLabel b = new JLabel(backgroundImage);
        b.setBounds(0,0,720,485);
        add(b);

        lines[0].setLocation(0, getHeight()-lines[0].getHeight());
        add(lines[0]);
    }

    private void setBackgroundImage(String background) {
        try {
            this.backgroundImage = new ImageIcon(ImageIO.read(new File(background)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setLines(DialogueLine[] lines) { this.lines = lines; }
}
