package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static util.TileMap.*;

public class GameCharacter {
    private enum Steps {LEFT, CENTER, RIGHT};
    private float[] STARTING_POS;
    private float[] pos;
    private Image image;
    private Map<String, Image> images;
    private int stepsTaken;

    private void setStartingPos(float[] startingPos) {
        this.pos = startingPos.clone();
        this.STARTING_POS = startingPos;
    }

    //private void setImage(String image) { this.image = images.get(image); }
    private void setImage(Steps stepEnum) {
        String direction = getDirection().name().toLowerCase();
        String step = stepEnum.name().toLowerCase();
        this.image = images.get(direction + "_" + step);
    }
    private void setImageFolder(String imageFolder) throws IOException {
        images = new HashMap<>();
        for (String direction : getDirections()) {
            for (String step : getSteps()) {
                String name = getName(direction, step);
                String path = getPath(imageFolder, name);
                images.put(name, ImageIO.read(new File(path)));
            }
        }
        this.direction = Direction.WEST;
        setImage(Steps.CENTER);
    }

    public float[] getPos() { return this.pos; }
    public int[] getIntPos() { return new int[]{(int) pos[0], (int) pos[1]}; }
    public Image getImage() { return this.image; }

    private static String getPath(String folder, String name) { return "res/characters/" + folder + "/" + name + ".png"; }
    private static String getName(String direction, String step) { return direction + "_" + step; }
    private static String[] getSteps(){
        return Arrays.stream(Steps.values()).map(x -> x.name().toLowerCase()).toArray(String[]::new);
    }
    ///////////////// Movement /////////////////
    private boolean isMoving = false;
    private Direction direction = TileMap.Direction.WEST;
    private static final float step = 0.03125F; // 1/16

    private boolean isInteger(float x) { return x % 1 == 0; }

    public boolean isMoving() { return isMoving; }

    public Direction getDirection() { return direction; }

    public void setDirection(Direction direction) {
        this.direction = direction;
        setImage(Steps.CENTER);
    }

    public void moveForward() {
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

        stepsTaken++;
        if (stepsTaken == 16 || stepsTaken == 32) {
            setImage(Steps.CENTER);
        } else if (stepsTaken == 1) {
            setImage(Steps.LEFT);
        } else if (stepsTaken == 17) {
            setImage(Steps.RIGHT);
        }
    }

    /////////////////////// ACTIONS /////////////////////
    public void die() { pos = STARTING_POS.clone(); }
}
