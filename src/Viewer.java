import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import util.Level;
import util.GameCharacter;
import util.TileMap;


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
 
 * Credits: Kelly Charles (2020)
 */ 
public class Viewer extends JPanel {

	Model gameworld = new Model();

	public Viewer(Model World) {
		this.gameworld=World;
	}

	public Viewer(LayoutManager layout) { super(layout); }

	public Viewer(boolean isDoubleBuffered) { super(isDoubleBuffered); }

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) { super(layout, isDoubleBuffered); }

	public void updateview() { this.repaint(); }

	private static final AlphaComposite acReset = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);

	private static final Image keySlot;
	private static final Image levelSlot;
	private static final int tile_size = TileMap.TILE_SIZE;

	static {
        try {
            keySlot = ImageIO.read(new File("res/Screens/KeySlot.png"));
			levelSlot = ImageIO.read(new File("res/Screens/LevelSlot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawAtmosphere((Graphics2D) g);
		drawLevel(g);
		drawPrincess((Graphics2D) g);
		drawDragon((Graphics2D) g);
		drawSlots(g);
	}

	private void drawBackground(Graphics g) {
		TileMap map = this.gameworld.getLevel().getMap();
		for (int i = 0; i < TileMap.WIDTH; i++) {
			for (int j = 0; j < TileMap.HEIGHT; j++) {
				g.drawImage(map.getBackground_tile(), i*tile_size,j*tile_size, null);
			}
		}
	}

	// Tile map from a 2D array
	private void drawLevel(Graphics g) {
		TileMap map = this.gameworld.getLevel().getMap();
		String[][] tiles = map.getOverlays();
		for (int i = 0; i < TileMap.WIDTH; i++) {
			for (int j = 0; j < TileMap.HEIGHT; j++) {
				if (!Objects.equals(tiles[i][j], "")) {
					g.drawImage(map.getImage(tiles[i][j]), i*tile_size,j*tile_size, null);
				}
			}
		}
	}

	private void drawSlots(Graphics g) {
		Level level = gameworld.getLevel();
		g.drawImage(levelSlot, 1*tile_size, 0*tile_size, null);
		g.drawImage(keySlot, 2*tile_size, 0*tile_size, null);

		if (level.hasKey()) {
			g.drawImage(level.getKey(), 2*tile_size, 0*tile_size, null);
		}

		g.setColor(new Color(68, 37, 52));
		g.setFont(new Font("Courier", Font.BOLD, 30));
		g.drawString(level.getId() + "", 63, 35);
	}

	// Graphics2D needed for setComposite
	private void drawPrincess(Graphics2D g) {
		Level level = gameworld.getLevel();
		GameCharacter princess = level.getPrincess();
		float[] pos = princess.getPos();
		float opacity = princess.getOpacity(); //draw half transparent
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
		g.setComposite(ac);
		g.drawImage(princess.getImage(), (int)(pos[0]*tile_size), (int)(pos[1]*tile_size), null);
		g.setComposite(acReset);
		if (princess.isBurning()) {
			g.drawImage(princess.getFireImage(), (int)(pos[0]*tile_size), (int)(pos[1]*tile_size), null);
		}
	}

	private void drawDragon(Graphics2D g) {
		GameCharacter dragon = gameworld.getLevel().getDragon();
		float[] pos = dragon.getPos();
		float opacity = dragon.getOpacity();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
		g.setComposite(ac);
		g.drawImage(dragon.getImage(), (int)(pos[0]*tile_size), (int)(pos[1]*tile_size), null);
		g.setComposite(acReset);
		if (dragon.isBurning()) {
			int[] firePos = dragon.getFirePos();
			g.drawImage(dragon.getFireImage(), firePos[0]*tile_size, firePos[1]*tile_size, null);
		}
	}

	//not so sure about this, coloring the image also reduces contrast. Maybe for ground only?
	private void drawAtmosphere(Graphics2D g) {
		int width = tile_size*15;
		int height = tile_size*10;
		int levelID = gameworld.getLevelId();
		float opacity = (levelID-1) / 10F;
		//System.out.println(opacity);
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
		g.setComposite(ac);
		g.setColor(new Color(255, 0, 89, 255));
		g.fillRect(0, 0, width, height);
		g.setComposite(acReset);
	}
}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */
