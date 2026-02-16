package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameCharacter {
    private float[] STARTING_POS;
    private float[] pos;
    private Image image;
    private Map<String, Image> images;
    private int stepsTaken;

    private void setStartingPos(float[] startingPos) {
        this.pos = startingPos.clone();
        this.STARTING_POS = startingPos;
    }

    private void setImage(String image) { this.image = images.get(image); }

    private void setImageFolder(String imageFolder) throws IOException {
        images = new HashMap<>();
        String[] directions = new String[]{"north", "south", "east", "west"};
        String[] steps = new String[]{"left", "center", "right"};
        for (String direction : directions) {
            for (String step : steps) {
                String name = getName(direction, step);
                String path = getPath(imageFolder, name);
                images.put(name, ImageIO.read(new File(path)));
            }
        }
        setImage(getName("west", "center"));
    }

    public float[] getPos() { return this.pos; }
    public int[] getIntPos() { return new int[]{(int) pos[0], (int) pos[1]}; }
    public Image getImage() { return this.image; }

    private static String getPath(String folder, String name) { return "res/characters/" + folder + "/" + name + ".png"; }
    private static String getName(String direction, String step) { return direction + "_" + step; }

    ///////////////// Movement /////////////////
    private boolean isMoving = false;
    private TileLocation.Direction direction = TileLocation.Direction.WEST;
    private static final float step = 0.03125F; // 1/16

    private boolean isInteger(float x) { return x % 1 == 0; }

    public boolean isMoving() { return isMoving; }

    public void move(TileLocation.Direction direction) {
        this.direction = direction;
        this.isMoving = true;
        if (this.stepsTaken == 32) {
            this.stepsTaken = 0;

        }
    }

    public void keepMoving() {
        switch (direction) {
            case WEST -> {
                pos[0] -= step;
                if (isInteger(pos[0])){ this.isMoving = false; }
            }
            case NORTH -> {
                pos[1] -= step;
                if (isInteger(pos[1])) this.isMoving = false;
            }
            case EAST -> {
                pos[0] += step;
                if (isInteger(pos[0])) this.isMoving = false;
            }
            case SOUTH -> {
                pos[1] += step;
                if (isInteger(pos[1])) this.isMoving = false;
            }
        }

//        stepsTaken++;
//        if (stepsTaken == 7 || stepsTaken == 15) {
//            setImage(getName(direction.name().toLowerCase(), "center"));
//        } else if (stepsTaken == 3) {
//            setImage(getName(direction.name().toLowerCase(), "left"));
//        } else if (stepsTaken == 11) {
//            setImage(getName(direction.name().toLowerCase(), "right"));
//        }

        stepsTaken++;
        if (stepsTaken == 16 || stepsTaken == 32) {
            setImage(getName(direction.name().toLowerCase(), "center"));
        } else if (stepsTaken == 1) {
            setImage(getName(direction.name().toLowerCase(), "left"));
        } else if (stepsTaken == 17) {
            setImage(getName(direction.name().toLowerCase(), "right"));
        }
    }

    public TileLocation.Direction getDirection() { return direction; }

    /////////////////////// ACTIONS /////////////////////
    public void die() { pos = STARTING_POS.clone(); }
}
