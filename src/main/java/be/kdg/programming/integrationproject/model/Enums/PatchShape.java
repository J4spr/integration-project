package be.kdg.programming.integrationproject.model.Enums;

public enum PatchShape {
    SMALL(new boolean[][]{
            {true, true}
    });

    private final boolean[][] shape;

    PatchShape(boolean[][] shape) {
        this.shape = shape;
    }

    public boolean[][] getShape() {
        return this.shape;
    }
}