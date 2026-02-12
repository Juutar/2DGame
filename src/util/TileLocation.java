package util;

public class TileLocation {
    public enum Direction { WEST, NORTH, EAST, SOUTH} //TODO: move to game character class
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
