package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static util.TileLocation.getDestination;

public class Level {

    public static int HEIGHT = 10;
    public static int WIDTH = 15;
    public static int TILE_SIZE = 48;

    private int id;
    private String[][] overlays;
    private Map<String,Image> tiles = new HashMap<>();
    private GameCharacter princess;
    private GameCharacter dragon;

    public Level() throws IOException {
        String[] tile_names = {"bridge", "button", "chest_closed", "dead_tree", "door_open", "dragon", "cloud", "scorched", "grass", "princess", "tree"};
        for (String name : tile_names) {
            tiles.put(name, ImageIO.read(new File(this.getPath(name))));
        }
    }

    public int getId() { return id; }
    public Image getBackground_tile() { return this.tiles.get("grass"); }
    public String[][] getOverlays() { return overlays; }
    public GameCharacter getPrincess() { return princess; }
    public GameCharacter getDragon() { return dragon; }
    public Image getImage(String tile) { return tiles.get(tile); }

    private void setId(int id) { this.id = id; }
    private void setOverlays(String[][] overlays) { this.overlays = overlays; }
    private void setPrincess(GameCharacter princess) { this.princess = princess; }
    private void setDragon(GameCharacter dragon) { this.dragon = dragon; }

    private String getPath(String name) { return "res/tiles/" + name + ".png"; }

    private boolean isWithinBounds(int[] tile) {
        return tile[0] >= 0 &&
                tile[0] < WIDTH &&
                tile[1] >= 0 &&
                tile[1] < HEIGHT;
    }

    /////////////////// PRINCESS //////////////////
    public boolean isPrincessMoving() { return princess.isMoving(); }

    public void movePrincess(TileLocation.Direction direction) {
        int[] destination = getDestination(princess.getIntPos(), direction);
        if (isWithinBounds(destination) && overlays[destination[0]][destination[1]].isBlank()) {
            princess.move(direction);
        }
    }

    public void keepPrincessMoving() { princess.keepMoving(); }

    /////////////////// DRAGON //////////////////
    public boolean isDragonMoving() { return dragon.isMoving(); }

    public void moveDragon(TileLocation.Direction direction) {
        int[] destination = getDestination(dragon.getIntPos(), direction);
        if (isWithinBounds(destination) && overlays[destination[0]][destination[1]].isBlank()) {
            dragon.move(direction);
        }
    }

    public void keepDragonMoving() { dragon.keepMoving(); }
}
