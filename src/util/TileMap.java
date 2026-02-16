package util;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TileMap {
    public static final int HEIGHT = 10;
    public static final int WIDTH = 15;
    public static final int TILE_SIZE = 48;

    public static final String CLOUD = "cloud";
    public static final String SCORCHED = "scorched";
    public static final String DEAD_TREE = "dead_tree";
    public static final String HOLE = "hole";
    public static final String BRIDGE = "bridge";
    public static final String BUTTON = "button";
    public static final String CHEST = "chest_closed";
    public static final String DOOR = "door_open";
    public static final String TREE = "tree";
    public static final String KEY = "key";

    public static final String[] TILE_NAMES = {
            BRIDGE,
            BUTTON,
            CHEST,
            DEAD_TREE,
            DOOR,
            CLOUD,
            SCORCHED,
            "grass",
            TREE,
            HOLE,
            "chest_open",
            KEY,
            "door_closed"
    };

    private static final List<String> OBSTACLES = new ArrayList<>(Arrays.asList(DEAD_TREE, CHEST, DOOR, TREE, "chest_open"));

    public static boolean isObstacle(String tile) {
        return OBSTACLES.contains(tile);
    }

    public static String getPath(String name) { return "res/tiles/" + name + ".png"; }

    private String[][] overlays;
    private final Map<String, Image> tiles = new HashMap<>();
    private TileMap.Bridge[] bridges;

    public TileMap() throws IOException {
        for (String name : TILE_NAMES) {
            tiles.put(name, ImageIO.read(new File(getPath(name))));
        }
    }

    public Image getImage(String tile) { return tiles.get(tile); }

    public Image getBackground_tile() { return this.tiles.get("grass"); }

    public boolean isWithinBounds(int[] tile) {
        return tile[0] >= 0 &&
                tile[0] < WIDTH &&
                tile[1] >= 0 &&
                tile[1] < HEIGHT;
    }

    ///////////////////////// OVERLAYS ///////////////////////

    public void setOverlays(String[][] overlays) { this.overlays = overlays; }

    public String[][] getOverlays() { return overlays; }

    public String getOverlay(int[] pos) { return overlays[pos[0]][pos[1]]; }

    public void setOverlay(int[] pos, String tile) { overlays[pos[0]][pos[1]] = tile; }


    ///////////////////////// BRIDGES ///////////////////////

    private void setBridges(Bridge[] bridges) {
        this.bridges = bridges;
        for (Bridge bridge : this.bridges){
            setOverlay(bridge.button, BUTTON);
            setOverlay(bridge.hole, HOLE);
        }
    }

    public void activateBridge(int[] button) {
        if (getOverlay(button).equals(BUTTON)) {
            int[] bridge = getBridgeOf(button);
            assert bridge != null;
            if (getOverlay(bridge).equals(HOLE)) {
                setOverlay(bridge, BRIDGE);
            }
        }
    }

    public void deactivateBridge(int[] button) {
        if (getOverlay(button).equals(BUTTON)) {
            int[] bridge = getBridgeOf(button);
            assert bridge != null;
            setOverlay(bridge, HOLE);
        }
    }

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

    ///////////////// DIRECTIONS //////////////////
    public enum Direction { WEST, NORTH, EAST, SOUTH }
    public static String[] getDirections() {
        return Arrays.stream(Direction.values()).map(x -> x.name().toLowerCase()).toArray(String[]::new);
    }

    private static final int[][] DirectionVector = {{-1,0}, {0,-1},{1,0}, {0,1}};

    private static int[] addDirectionVectors(int[] v1, int[] v2) {
        assert v1.length == 2 && v2.length == 2;
        return new int[]{v1[0] + v2[0], v1[1] + v2[1]};
    }

    public static int[] getDirectionVector(Direction direction) {
        return DirectionVector[direction.ordinal()];
    }

    public static int[] getDestination(int[] pos, Direction direction) {
        return addDirectionVectors(pos, getDirectionVector(direction));
    }
}
