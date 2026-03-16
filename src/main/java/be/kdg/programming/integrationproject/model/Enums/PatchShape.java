package be.kdg.programming.integrationproject.model.Enums;

public enum PatchShape {
    ONE_BY_ONE(new boolean[][]{
            {true}
    }),

    SMALL(new boolean[][]{
            {true, true}
    });
    //first test shape, will add the other 32 shapes later

    private final boolean[][] shape;

    PatchShape(boolean[][] shape) {
        this.shape = shape;
    }

    public boolean[][] getShape() {
        return this.shape;
    }
}