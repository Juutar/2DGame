package util;

import java.awt.*;
import java.util.*;

import static util.TileMap.*;

public class Level {

    private int id;

    private TileMap tileMap;
    private GameCharacter princess;
    private GameCharacter dragon;
    private boolean hasKey = false;
    private float[] doorLocation = null;

    private void setId(int id) { this.id = id; }
    private void setPrincess(GameCharacter princess) { this.princess = princess; }
    private void setDragon(GameCharacter dragon) { this.dragon = dragon; }
    private void setTileMap(TileMap tileMap) { this.tileMap = tileMap; }

    public int getId() { return id; }

    public boolean isComplete() {
        return doorLocation != null &&
                Arrays.equals(princess.getPos(), doorLocation) &&
                Arrays.equals(dragon.getPos(), doorLocation);
    }

    public GameCharacter getPrincess() { return princess; }
    public GameCharacter getDragon() { return dragon; }
    public TileMap getMap() { return this.tileMap; }

    /////////////////// PRINCESS //////////////////
    public boolean princessDiedBurning() {
        int[] pos = princess.getIntPos();
        return tileMap.getOverlay(pos).equals(SCORCHED);
    }

    public boolean princessDiedFalling() {
        int[] pos = princess.getIntPos();
        return tileMap.getOverlay(pos).equals(HOLE);
    }

    public void princessAction() {
        int[] destination = getDestination(princess.getIntPos(), princess.getDirection());
        if (tileMap.getOverlay(destination).equals(CHEST)) {
            openChest(destination);
            AudioPlayer.playSoundEffect(AudioPlayer.Effect.CHEST);
        } else if (tileMap.getOverlay(destination).equals(DOOR) && hasKey()) {
            openDoor(destination);
            AudioPlayer.playSoundEffect(AudioPlayer.Effect.DOOR);
        }
    }

    private void openChest(int[] chest) {
        tileMap.setOverlay(chest, "chest_open");
        hasKey = true;
    }

    private void openDoor(int[] location) {
        tileMap.setOverlay(location, "door_closed");
        hasKey = false;
        doorLocation = new float[]{(float) location[0], (float) location[1]};
    }

    ///  will refactor both functions to a Princess class when working on animations
    public boolean hasKey() { return hasKey; }
    public Image getKey() { return tileMap.getImage(KEY);}

    /////////////////// DRAGON //////////////////
    public boolean dragonDied() {
        int[] pos = dragon.getIntPos();
        return tileMap.getOverlay(pos).equals(CLOUD) ||
                tileMap.getOverlay(pos).equals(HOLE);
    }

    public void burnTree() {
        int[] destination = getDestination(dragon.getIntPos(), dragon.getDirection());
        if (Objects.equals(tileMap.getOverlay(destination), DEAD_TREE)) {
            tileMap.setOverlay(destination, "");
        }
    }

    ////////////////// CHARACTERS /////////////////

    public void moveCharacter(GameCharacter character, Direction direction) {
        character.setDirection(direction);
        int[] destination = getDestination(character.getIntPos(), direction);
        if (tileMap.isWithinBounds(destination)) {
            String tile = tileMap.getOverlay(destination);
            if (!isObstacle(tile)) {
                character.moveForward();
                didCharacterLeaveButton(character.getIntPos());
            }
        }
    }

    public void isCharacterOnButton(GameCharacter character) { tileMap.activateBridge(character.getIntPos()); }

    private void didCharacterLeaveButton(int[] button) { tileMap.deactivateBridge(button); }

}
