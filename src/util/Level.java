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
    private Princess princess;

    public Level() throws IOException {
        String[] tile_names = {"bridge", "button", "chest_closed", "dead_tree", "door_open", "dragon_left_0", "cloud", "scorched", "grass", "princess", "tree"};
        for (String name : tile_names) {
            tiles.put(name, ImageIO.read(new File(this.getPath(name))));
        }
    }

    public int getId() { return id; }
    public Image getBackground_tile() { return this.tiles.get("grass"); }
    public String[][] getOverlays() { return overlays; }
    public Princess getPrincess() { return princess; }
    public Image getImage(String tile) { return tiles.get(tile); }

    private void setId(int id) { this.id = id; }
    private void setOverlays(String[][] overlays) { this.overlays = overlays; }
    private void setPrincess(Princess princess) { this.princess = princess; }

    private String getPath(String name) { return "res/tiles/" + name + ".png"; }

    /////////////////// PRINCESS //////////////////
    public enum Direction { WEST, NORTH, EAST, SOUTH} //TODO: move to game character class
    public static int[][] DirectionVector = {{-1,0}, {0,-1},{1,0}, {0,1}};
    public boolean isPrincessMoving() { return princess.isMoving(); }

    public void movePrincess(Direction direction) {
        // get destination tile position
        // check that the tile has no overlays
        int[] destination = add2DVectors(princess.getIntPos(), getDirectionVector(direction));
        if (!isOutOfBounds(destination) && overlays[destination[0]][destination[1]].isBlank()) {
            princess.move(direction);
        }
    }

    public void keepPrincessMoving() {
        princess.keepMoving();
    }
    
    private int[] add2DVectors(int[] v1, int[] v2) {
        assert v1.length == 2 && v2.length == 2;
        return new int[]{v1[0] + v2[0], v1[1] + v2[1]};
    }
    
    private int[] getDirectionVector(Direction direction) {
        return DirectionVector[direction.ordinal()];
    }

    private boolean isOutOfBounds(int[] tile) {
        return tile[0] < 0 ||
                tile[0] >= WIDTH ||
                tile[1] < 0 ||
                tile[1] >= HEIGHT;
    }

}
