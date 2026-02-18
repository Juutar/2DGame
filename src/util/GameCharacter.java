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
    private int opacity = 10;
    private Image[] fire;

    private GameCharacter() throws IOException {
        fire = new Image[]{
                ImageIO.read(new File(getPath("fire_1"))),
                ImageIO.read(new File(getPath("fire_2")))
        };
    }

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
                String path = getPath(imageFolder + "/" + name);
                images.put(name, ImageIO.read(new File(path)));
            }
        }
        this.direction = Direction.WEST;
        setImage(Steps.CENTER);
    }

    public float[] getPos() { return this.pos; }
    public int[] getIntPos() { return new int[]{(int) pos[0], (int) pos[1]}; }
    public Image getImage() { return this.image; }

    private static String getPath(String name) { return "res/characters/" + name + ".png"; }
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
    public float getOpacity() { return this.opacity / 10.0F; }

    public void die() { pos = STARTING_POS.clone(); }

    //////////////////////// FALLING ////////////////////
    private boolean isFalling = false;

    public boolean isFalling() { return isFalling; }

    public void fall() { isFalling = true; }

    public void keepFalling() {
        opacity--;
        if (opacity == 0) {
            opacity = 10;
            isFalling = false;
            respawn();
        }
    }

    //////////////////////// BURNING ////////////////////
    // how to use it for both burning a tree and a character?
    // in the case of the tree, it should result in the tree disappearing
    // in the case of the character, it should result in the character respawning
    // could pass in a consequence function

    private boolean isBurning = false;
    private boolean hasBurnt;
    private int blinking;
    private int[] firePos;
    private Image fireImage;

    public boolean isBurning() { return isBurning; }
    public boolean hasBurnt() { return hasBurnt; }
    public void resetBurnt() { hasBurnt = false; }
    public int[] getFirePos() { return firePos; }
    public Image getFireImage() { return fireImage; }

    public void burn(int[] pos) {
        firePos = pos;
        blinking = 1;
        isBurning = true;
    }

    public void keepBurning() {
        blinking++;
        if (blinking % 16 == 0) fireImage = fire[0]; //draw fire
        else if (blinking % 16 == 8) fireImage = fire[1]; //don't draw fire
        if (blinking == 64) {
            isBurning = false;
            hasBurnt = true;
        }
    }

    //////////////////////// RESPAWNING ////////////////////
    private boolean isRespawning = false;


    public boolean isRespawning() { return isRespawning; }

    public void respawn() {
        isRespawning = true;
        blinking = 1;
        pos = STARTING_POS.clone();
    }

    public void keepRespawning() {
        blinking++;
        if (blinking % 8 == 0) opacity = 10;
        else if (blinking % 8 == 2) opacity = 0;

        if (blinking == 32) isRespawning = false;
    }
}
