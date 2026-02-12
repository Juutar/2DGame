package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameCharacter {
    private float[] pos;
    private Image image;

    private void setPos(float[] pos) { this.pos = pos; }
    private void setImage(String image) throws IOException { this.image = ImageIO.read(new File(this.getPath(image))); }
    public float[] getPos() { return this.pos; }
    public int[] getIntPos() { return new int[]{(int) pos[0], (int) pos[1]}; }
    public Image getImage() { return this.image; }

    private String getPath(String name) { return "res/tiles/" + name + ".png"; }


    ///////////////// Movement /////////////////
    private boolean isMoving = false;
    private TileLocation.Direction direction = TileLocation.Direction.WEST;
    private static final float step = 0.03125F; // 1/16

    private boolean isInteger(float x) { return x % 1 == 0; }

    public boolean isMoving() { return isMoving; }

    public void move(TileLocation.Direction direction) {
        this.direction = direction;
        this.isMoving = true;
    }

    public void keepMoving() {
        switch (direction) {
            case WEST -> {
                pos[0] -= step;
                if (isInteger(pos[0])) this.isMoving = false;
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
    }

    public TileLocation.Direction getDirection() { return direction; }
}
