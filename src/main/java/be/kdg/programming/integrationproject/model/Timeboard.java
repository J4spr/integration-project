package be.kdg.programming.integrationproject.model;

public class Timeboard {
    //the timeboard has 53 positions (0-52), position 52 is the final position
    private static final int SIZE = 53;
    //positions on the timeboard where players collect their button income
    private static final int[] BUTTON_POSITIONS = {5, 11, 17, 23, 29, 35, 41, 47};
    //positions on the timeboard where players receive a free 1x1 leather patch
    private static final int[] LEATHER_PATCH_POSITIONS = {26, 32, 38, 44, 50};

    public int getSize() {
        return SIZE;
    }

    //calculates the new position after moving, capped at the final position
    public int updatePosition(int currentPosition, int timeCost) {
        int newPosition = currentPosition + timeCost;
        if (newPosition >= SIZE) newPosition = SIZE - 1;
        return newPosition;
    }

    //counts how many button positions the player passed
    //between oldPosition and newPosition
    //uses exclusive lower bound and inclusive upper bound to avoid counting the starting position
    public int countButtonPositionsPassed(int oldPosition, int newPosition) {
        int count = 0;
        for (int buttonPosition : BUTTON_POSITIONS) {
            if (buttonPosition > oldPosition && buttonPosition <= newPosition) {
                count++;
            }
        }
        return count;
    }

    //counts how many leather patch positions the player passed
    //between oldPosition and newPosition
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