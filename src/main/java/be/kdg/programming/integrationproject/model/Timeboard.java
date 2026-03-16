package be.kdg.programming.integrationproject.model;

public class Timeboard {
    private static final int SIZE = 53;
    private static final int[] BUTTON_POSITIONS = {5, 11, 17, 23, 29, 35, 41, 47};
    private static final int[] LEATHER_PATCH_POSITIONS = {26, 32, 38, 44, 50};

    public int getSize() {
        return SIZE;
    }

    public int updatePosition(int currentPosition, int timeCost) {
        int newPosition = currentPosition + timeCost;
        if (newPosition >= SIZE) newPosition = SIZE - 1;
        return newPosition;
    }

    public int countButtonPositionsPassed(int oldPosition, int newPosition) {
        int count = 0;
        for (int buttonPosition : BUTTON_POSITIONS) {
            if (buttonPosition > oldPosition && buttonPosition <= newPosition) {
                count++;
            }
        }
        return count;
    }

    public int countLeatherPatchesPassed(int oldPosition, int newPosition) {
        int count = 0;
        for (int leatherPosition : LEATHER_PATCH_POSITIONS) {
            if (leatherPosition > oldPosition && leatherPosition <= newPosition) {
                count++;
            }
        }
        return count;
    }
}