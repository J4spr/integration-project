package be.kdg.programming.integrationproject.model;

public class Timeboard {
    private static final int SIZE = 53;
    private boolean leatherPatch1x1;
    private int collectableButtons;

    public int updatePosition(int position) {
        return position;
    }

    public int getSize() {
        return SIZE;
    }

    public boolean hasLeatherPatch() {
        return leatherPatch1x1;
    }

    public int getCollectableButtons() {
        return collectableButtons;
    }
}
