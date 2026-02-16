package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Tile {
    public static final String CLOUD = "cloud";
    public static final String SCORCHED = "scorched";
    public static final String DEAD_TREE = "dead_tree";
    public static final String HOLE = "hole";
    public static final String BRIDGE = "bridge";
    public static final String BUTTON = "button";
    public static final String CHEST = "chest_closed";
    public static final String DOOR = "door_open";
    public static final String TREE = "tree";

    public static String getPath(String name) { return "res/tiles/" + name + ".png"; }

    private static final List<String> OBSTACLES = new ArrayList<>(Arrays.asList(
            DEAD_TREE,
            HOLE,
            CHEST,
            DOOR,
            TREE
    ));

    public static boolean isObstacle(String tile) {
        return OBSTACLES.contains(tile);
    }
}
