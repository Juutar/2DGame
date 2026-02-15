package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static util.Tile.*;
import static util.TileLocation.getDestination;

public class Level {
    public static final int HEIGHT = 10;
    public static final int WIDTH = 15;
    public static final int TILE_SIZE = 48;
    public static final String[] tile_names = { BRIDGE, BUTTON, CHEST, DEAD_TREE, DOOR, "dragon", CLOUD, SCORCHED, "grass", "princess", TREE, HOLE };

    private int id;
    private String[][] overlays;
    private final Map<String,Image> tiles = new HashMap<>();
    private GameCharacter princess;
    private GameCharacter dragon;
    private Map<int[], int[]> bridges;

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
        this.bridges = new HashMap<>();
        for (Bridge bridge : bridges){
            int[] button = bridge.button;
            int[] hole = bridge.hole;
            overlays[button[0]][button[1]] = BUTTON;
            overlays[hole[0]][hole[1]] = HOLE;
            this.bridges.put(button, hole);
        }

    }

    public int getId() { return id; }
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

    ////////////////// CHARACTERS /////////////////
    private void moveCharacter(GameCharacter character, TileLocation.Direction direction) {
        int[] destination = getDestination(character.getIntPos(), direction);
        if (isWithinBounds(destination)) {
            String tile = overlays[destination[0]][destination[1]];
            if (!isObstacle(tile)) {
                character.move(direction);
            }
        }
    }

    private void isCharacterOnButton(GameCharacter character) {
        int[] pos = character.getIntPos();
        if (overlays[pos[0]][pos[1]].equals(BUTTON)) {
            int[] bridge = bridges.get(pos);
            if (overlays[bridge[0]][bridge[1]].equals(HOLE)) {
                overlays[bridge[0]][bridge[1]] = BRIDGE;
            }
        }
    }

    /////////////////// PRINCESS //////////////////
    public boolean isPrincessMoving() { return princess.isMoving(); }

    public void movePrincess(TileLocation.Direction direction) { moveCharacter(princess, direction); }

    public void keepPrincessMoving() { princess.keepMoving(); }

    public boolean princessDied() {
        int[] pos = princess.getIntPos();
        return overlays[pos[0]][pos[1]].equals(SCORCHED);
    }

    public void resetPrincess() { princess.die(); }

    public void isPrincessOnButton() { isCharacterOnButton(princess); }

    /////////////////// DRAGON //////////////////
    public boolean isDragonMoving() { return dragon.isMoving(); }

    public void moveDragon(TileLocation.Direction direction) { moveCharacter(dragon, direction); }

    public void keepDragonMoving() { dragon.keepMoving(); }

    public void burn() {
        int[] destination = getDestination(dragon.getIntPos(), dragon.getDirection());
        if (Objects.equals(overlays[destination[0]][destination[1]], DEAD_TREE)) {
            //dragon.burn();
            overlays[destination[0]][destination[1]] = "";
        }
    }

    public boolean dragonDied() {
        int[] pos = dragon.getIntPos();
        return overlays[pos[0]][pos[1]].equals(CLOUD);
    }

    public void resetDragon() { dragon.die(); }

    public void isDragonOnButton() { isCharacterOnButton(dragon); }

    ////////////////// BRIDGES /////////////////
    // TODO: understand why this is static (made static on the recommendation of intelliJ. Only way for jackson to parse this
    private static class Bridge { // necessary for json parsing
        public int[] button;
        public int[] hole;
        private void setButton(int[] button) { this.button = button; }
        private void setHole(int[] hole) { this.hole = hole; }
    }
}
