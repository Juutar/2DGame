import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import util.*;
import util.Story.Storyline;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */ 



public class MainWindow {
	private static final JFrame adventureAdvertedFrame = new JFrame("Game");
	private static final JPanel adventureAdverted = new JPanel();
	private static final CardLayout cardLayout = new CardLayout();

	private static Model gameworld = new Model();
	private static Viewer canvas = new Viewer(gameworld);
	private final KeyListener Controller = new Controller();
	private static final Storyline storyline = new Storyline();

	private static boolean startGame = false;

	private static final int TargetFPS = 100;

	private static final String CANVAS = "canvas";
	private static final String KEYS_PANEL = "keysPanel";
	private static final String END_PANEL = "endPanel";
	private static final String STORYLINE = "storyline";
	private static final String MENU = "menu";

	public MainWindow() {
		configureWindow();
		configureGamePanel();
		configureMenuPanel();
		configureEndPanel();
		configureDialoguePanel();
		configureToolsPanel();

		cardLayout.show(adventureAdverted, MENU);

		AudioPlayer.playSoundtrack();

        adventureAdvertedFrame.setVisible(true);
		adventureAdverted.setVisible(true);
	}

	public static void main(String[] args) {
		MainWindow hello = new MainWindow();
		while(true)
		{
			int TimeBetweenFrames =  1000 / TargetFPS;
			long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames;
			while (FrameCheck > System.currentTimeMillis()){}
			if(startGame)
			{
				gameloop();
			}
			UnitTests.CheckFrameRate(System.currentTimeMillis(),FrameCheck, TargetFPS);
		}
	}

	private static void gameloop() {
		gameworld.gamelogic();
		if (storyline.hasDialogue(gameworld.getLevelId())) {
			playDialogue();
		}
		canvas.updateview();
	}

	//////////////////////// UI CONFIGURATION //////////////////////////////

    private void configureWindow() {
		adventureAdvertedFrame.setSize(720, 510); // 720 x 510
		adventureAdvertedFrame.setLocationRelativeTo(null);
		adventureAdvertedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adventureAdvertedFrame.setTitle("Adventure Adverted");
		adventureAdvertedFrame.setLayout(null);

		adventureAdverted.setBounds(Theme.PanelBounds);
		adventureAdverted.setLayout(cardLayout);
		adventureAdvertedFrame.add(adventureAdverted);
    }

	private void configureGamePanel(){
		canvas.setBounds(Theme.PanelBounds);
		canvas.setLayout(null);
		configureSaveButton();
		canvas.addKeyListener(Controller);
		adventureAdverted.add(canvas, CANVAS);
	}

	private void configureMenuPanel() {
		JPanel menu = new JPanel();
		menu.setLayout(null);
		menu.add(configureStartButton());
		menu.add(configureLoadButton());
		menu.add(configureBackground("res/Screens/StartScreen.png"));
		adventureAdverted.add(menu, MENU);
	}

	private void configureEndPanel() {
		JPanel endPanel = new JPanel();
		endPanel.setLayout(null);
		endPanel.add(configureBackToMenuButton());
		endPanel.add(configureBackground("res/Screens/EndScreen.png"));
		adventureAdverted.add(endPanel, END_PANEL);
	}

	private void configureDialoguePanel(){
		storyline.setBounds(Theme.PanelBounds);
		storyline.setLayout(null);
		storyline.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!storyline.isDialogueFinished()) {
					storyline.nextLine();
				} else if (gameworld.isGameComplete()) {
					finishGame();
				} else if (gameworld.isFirstLevel()) {
					displayKeys();
				} else {
					resumeGame();
				}
			}
		});
		adventureAdverted.add(storyline, STORYLINE);
	}

	private void configureToolsPanel(){
		JPanel keysPanel = MenuTools.getKeysPanel();
		keysPanel.setBounds(Theme.PanelBounds);
		keysPanel.setLayout(null);
		keysPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resumeGame();
			}
		});
		adventureAdverted.add(keysPanel, KEYS_PANEL);
	}

	private void configureSaveButton() {
		JPanel gameSavedPanel = MenuTools.getGameSavedPanel();
		gameSavedPanel.setBounds(Theme.PanelBounds);
		gameSavedPanel.setLayout(null);
		gameSavedPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.remove(gameSavedPanel);
				startGame = true;
			}
		});

        JLabel saveButton = new JLabel(new ImageIcon("res/Screens/Save.png"));
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameSave.saveGame(gameworld.getLevelId());
				canvas.add(gameSavedPanel);
				canvas.repaint(); //ensures gameSave is rendered
				startGame = false; // ensures gameSave is added only once and game stops
			}
		});
		saveButton.setBounds(0,0,48,48);
		canvas.add(saveButton);
	}

	private JButton configureStartButton() {
		JButton startButton = makeButton("Start Game");
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) { resumeGame(); }
		});
		startButton.setBounds(270, 300, 200, 40);
		return startButton;
	}

	private JButton configureLoadButton() {
		JButton loadButton = makeButton("Load Game");
		loadButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				int level = GameSave.loadGame();
				if (level >= 0 && level <= gameworld.getNumberOfLevels()) gameworld.setLevel(level);
				resumeGame();
			}
		});
		loadButton.setBounds(270, 350, 200, 40);
		return loadButton;
	}

	private JButton configureBackToMenuButton() {
		JButton backToMenuButton = makeButton("Back to Menu");
		backToMenuButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				gameworld.reset();
				GameSave.saveGame(gameworld.getLevelId()); //wipe save
				backToMenu();
			}
		});
		backToMenuButton.setBounds(270, 300, 200, 40);
		return backToMenuButton;
	}

	private JLabel configureBackground(String filename) {
		File backgroundToLoad = new File(filename);
		try {
			BufferedImage myPicture = ImageIO.read(backgroundToLoad);
			JLabel BackgroundImage = new JLabel(new ImageIcon(myPicture));
			BackgroundImage.setBounds(Theme.BackgroundBounds);
			return BackgroundImage;
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static JButton makeButton(String text) {
		Color backgroundColor = Theme.Yellow;
		Color foregroundColor = Theme.Purple;

		JButton button = new JButton(text);

		button.setBackground(backgroundColor);
		button.setBorder(new LineBorder(foregroundColor, 5));
		button.setForeground(foregroundColor);
		button.setFont(new Font("Courier", Font.BOLD, 20));
		button.setFocusable(false);

		return button;
	}

	//////////////////////// GAME START/END //////////////////////////////

	private void resumeGame() {
		cardLayout.show(adventureAdverted, CANVAS);
		canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
		startGame=true;
	}

	private static void finishGame() {
		startGame = false;
		cardLayout.show(adventureAdverted, END_PANEL);
	}

	private static void playDialogue() {
		startGame = false;
		cardLayout.show(adventureAdverted, STORYLINE);
		storyline.playDialogue(gameworld.getLevelId());
	}

	private static void displayKeys() {
		startGame = false;
		cardLayout.show(adventureAdverted, KEYS_PANEL);
	}

	private static void backToMenu() {
		startGame = false;
		cardLayout.show(adventureAdverted, MENU);
	}
}

/*
 * 
 * 

Hand shake agreement 
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,=+++
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,:::::,=+++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,:++++????+??
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,:,,:,:,,,,,,,,,,,,,,,,,,,,++++++?+++++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,=++?+++++++++++??????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++?+++?++?++++++++++?????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++++++++++++++????+++++++???????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,:===+=++++++++++++++++++++?+++????????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,~=~~~======++++++++++++++++++++++++++????????????????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,::::,,,,,,=~.,,,,,,,+===~~~~~~====++++++++++++++++++++++++++++???????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,~~.~??++~.,~~~~~======~=======++++++++++++++++++++++++++????????????????II
:::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,:=+++??=====~~~~~~====================+++++++++++++++++++++?????????????????III
:::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,++~~~=+=~~~~~~==~~~::::~~==+++++++==++++++++++++++++++++++++++?????????????????IIIII
::::::::::::::::::::::::::::::::::::::::::::::::,:,,,:++++==+??+=======~~~~=~::~~===++=+??++++++++++++++++++++++++?????????????????I?IIIIIII
::::::::::::::::::::::::::::::::::::::::::::::::,,:+????+==??+++++?++====~~~~~:~~~++??+=+++++++++?++++++++++??+???????????????I?IIIIIIII7I77
::::::::::::::::::::::::::::::::::::::::::::,,,,+???????++?+?+++???7?++======~~+=====??+???++++++??+?+++???????????????????IIIIIIIIIIIIIII77
:::::::::::::::::::::::::::::::::::::::,,,,,,=??????IIII7???+?+II$Z77??+++?+=+++++=~==?++?+?++?????????????III?II?IIIIIIIIIIIIIIIIIIIIIIIIII
::::::::::::::::::::::::::::::,,,,,,~=======++++???III7$???+++++Z77ZDZI?????I?777I+~~+=7+?II??????????????IIIIIIIIIIIIIIIIIIIIII??=:,,,,,,,,
::::::::,:,:,,,,,,,:::~==+=++++++++++++=+=+++++++???I7$7I?+~~~I$I??++??I78DDDO$7?++==~I+7I7IIIIIIIIIIIIIIIIII777I?=:,,,,,,,,,,,,,,,,,,,,,,,,
++=++=++++++++++++++?+????+??????????+===+++++????I7$$ZZ$I+=~$7I???++++++===~~==7??++==7II?~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+++++++++++++?+++?++????????????IIIII?I+??I???????I7$ZOOZ7+=~7II?+++?II?I?+++=+=~~~7?++:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+?+++++????????????????I?I??I??IIIIIIII???II7II??I77$ZO8ZZ?~~7I?+==++?O7II??+??+=====.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
?????????????III?II?????I?????IIIII???????II777IIII7$ZOO7?+~+7I?+=~~+???7NNN7II?+=+=++,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
????????????IIIIIIIIII?IIIIIIIIIIII????II?III7I7777$ZZOO7++=$77I???==+++????7ZDN87I??=~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIII?II??IIIIIIIIIIIIIIIIIIIIIIIIIII???+??II7777II7$$OZZI?+$$$$77IIII?????????++=+.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?+++?IIIII7777$$$$$$7$$$$7IIII7I$IIIIII???I+=,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII???????IIIIII77I7777$7$$$II????I??I7Z87IIII?=,,,,,,,,,,,:,,::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777777777777I7I777777777~,,,,,,,+77IIIIIIIIIII7II7$$$Z$?I????III???II?,,,,,,,,,,::,::::::::,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777$77777777777+::::::::::::::,,,,,,,=7IIIII78ZI?II78$7++D7?7O777II??:,,,:,,,::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$$$$$$$$$$$77=:,:::::::::::::::::::::::::::,,7II$,,8ZZI++$8ZZ?+=ZI==IIII,+7:,,,,:::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$I~::::::::::::::::::::::::::::::::::::::::::II+,,,OOO7?$DOZII$I$I7=77?,,,,,,:::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::+ZZ?,$ZZ$77ZZ$?,,,,,::::::::::::::::::::::::::,::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::I$:::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
                                                                                                                             GlassGiant.com
 * 
 * 
 */
