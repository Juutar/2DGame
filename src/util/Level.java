package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Level {

    public static int HEIGHT = 10;
    public static int WIDTH = 15;

    private int id;
    private String[][] overlays;
    private Map<String,Image> tiles = new HashMap<>();

    public Level() throws IOException {
        String[] tile_names = {"bridge", "button", "chest_closed", "dead_tree", "door_open", "dragon_left_0", "cloud", "scorched", "grass", "princess_left_0", "tree"};
        for (String name : tile_names) {
            tiles.put(name, ImageIO.read(new File(this.getPath(name))));
        }
    }

    public int getId() { return id; }
    public Image getBackground_tile() { return this.tiles.get("grass"); }
    public String[][] getOverlays() { return overlays; }
    public Image getImage(String tile) { return tiles.get(tile); }

    private void setId(int id) { this.id = id; }
    private void setOverlays(String[][] overlays) { this.overlays = overlays; }

    private String getPath(String name) { return "res/tiles/" + name + ".png"; }
}
