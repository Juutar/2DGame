package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static util.Tile.*;
import static util.TileLocation.getDestination;

public class Level {
    public static final int HEIGHT = 10;
    public static final int WIDTH = 15;
    public static final int TILE_SIZE = 48;
    public static final String[] tile_names = { BRIDGE, BUTTON, CHEST, DEAD_TREE, DOOR, "dragon", CLOUD, SCORCHED, "grass", "princess", TREE, HOLE, "chest_open", KEY, "door_closed" };

    private int id;
    private String[][] overlays;
    private final Map<String,Image> tiles = new HashMap<>();
    private GameCharacter princess;
    private GameCharacter dragon;
    private Bridge[] bridges;
    private boolean hasKey = false;
    private boolean isCompleted = false;

    public Level() throws IOException {
        for (String name : tile_names) {
            tiles.put(name, ImageIO.read(new File(getPath(name))));
        }
    }

    private void setId(int id) { this.id = id; }
    private void setOverlays(String[][] overlays) { this.overlays = overlays; }
    private void setPrincess(GameCharacter princess) { this.princess = princess; }
    private void setDragon(GameCharacter dragon) { this.dragon = dragon; }
    private void setBridges(Bridge[] bridges) {
        this.bridges = bridges;
        for (Bridge bridge : this.bridges){
            setOverlay(bridge.button, BUTTON);
            setOverlay(bridge.hole, HOLE);
        }
    }

    public int getId() { return id; }
    public boolean isCompleted() { return isCompleted; }
    public Image getBackground_tile() { return this.tiles.get("grass"); }
    public String[][] getOverlays() { return overlays; }
    public GameCharacter getPrincess() { return princess; }
    public GameCharacter getDragon() { return dragon; }
    public Image getImage(String tile) { return tiles.get(tile); }

    private boolean isWithinBounds(int[] tile) {
        return tile[0] >= 0 &&
                tile[0] < WIDTH &&
                tile[1] >= 0 &&
                tile[1] < HEIGHT;
    }

    private String getOverlay(int[] pos) { return overlays[pos[0]][pos[1]]; }
    private void setOverlay(int[] pos, String tile) { overlays[pos[0]][pos[1]] = tile; }

    /////////////////// PRINCESS //////////////////
    public boolean princessDied() {
        int[] pos = princess.getIntPos();
        return getOverlay(pos).equals(SCORCHED) ||
                getOverlay(pos).equals(HOLE);
    }

    public void princessAction() {
        int[] destination = getDestination(princess.getIntPos(), princess.getDirection());
        if (getOverlay(destination).equals(CHEST)) {
            openChest(destination);
        } else if (getOverlay(destination).equals(DOOR) && hasKey()) {
            openDoor(destination);
        }
    }

    private void openChest(int[] chest) {
        setOverlay(chest, "chest_open");
        hasKey = true;
    }

    private void openDoor(int[] location) {
        setOverlay(location, "door_closed");
        hasKey = false;
        isCompleted = true;
    }

    ///  will refactor both functions to a Princess class when working on animations
    public boolean hasKey() { return hasKey; }
    public Image getKey() { return tiles.get(KEY);}

    /////////////////// DRAGON //////////////////
    public boolean dragonDied() {
        int[] pos = dragon.getIntPos();
        return getOverlay(pos).equals(CLOUD) ||
                getOverlay(pos).equals(HOLE);
    }

    public void burn() {
        int[] destination = getDestination(dragon.getIntPos(), dragon.getDirection());
        if (Objects.equals(getOverlay(destination), DEAD_TREE)) {
            setOverlay(destination, "");
        }
    }

    ////////////////// CHARACTERS /////////////////
    public boolean isCharacterMoving(GameCharacter character) { return character.isMoving(); }

    public void moveCharacter(GameCharacter character, TileLocation.Direction direction) {
        int[] destination = getDestination(character.getIntPos(), direction);
        if (isWithinBounds(destination)) {
            String tile = getOverlay(destination);
            if (!isObstacle(tile)) {
                character.move(direction);
                didCharacterLeaveButton(character);
            }
        }
    }

    public void keepCharacterMoving(GameCharacter character) { character.keepMoving(); }

    public void resetCharacter(GameCharacter character) { character.die(); }

    public void isCharacterOnButton(GameCharacter character) {
        int[] pos = character.getIntPos();
        if (getOverlay(pos).equals(BUTTON)) {
            int[] bridge = getBridgeOf(pos);
            assert bridge != null;
            if (getOverlay(bridge).equals(HOLE)) {
                setOverlay(bridge, BRIDGE);
            }
        }
    }

    private void didCharacterLeaveButton(GameCharacter character) {
        int[] pos = character.getIntPos();
        if (getOverlay(pos).equals(BUTTON)) {
            int[] bridge = getBridgeOf(pos);
            assert bridge != null;
            setOverlay(bridge, HOLE);
        }
    }

    ////////////////// BRIDGES /////////////////
    // static: does not depend on the outer class. Necessary for json parser
    private static class Bridge { // necessary for json parsing
        public int[] button;
        public int[] hole;
        private void setButton(int[] button) { this.button = button; }
        private void setHole(int[] hole) { this.hole = hole; }
        public boolean isButton(int[] button) { return this.button[0] == button[0] && this.button[1] == button[1]; }
        public int[] getBridge() { return this.hole; }
    }

    private int[] getBridgeOf(int[] button) {
        for (Bridge bridge : bridges) {
            if (bridge.isButton(button)) return bridge.getBridge();
        }
        return null;
    }
}
