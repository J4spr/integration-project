package be.kdg.programming.integrationproject.model.Enums;

public enum PatchShape {
    ONE_BY_ONE(new boolean[][]{
            {true}
    }),

    SMALL(new boolean[][]{
            {true, true}
    }),

    L_SHAPE(new boolean[][]{
        {true, false},
        {true, false},
        {true, true}
    }),

    T_SHAPE(new boolean[][]{
        {true, true, true},
        {false, true, false},
        {false, true, false}
    }),

    S_SHAPE(new boolean[][]{
        {false, true, true},
        {true, true, false}
    }),

    Z_SHAPE(new boolean[][]{
        {true, true, false},
        {false, true, true}
    }),

    SQUARE(new boolean[][]{
        {true, true},
        {true, true}
    });
    //basic shapes already added, enough for beta. Will later add all other shapes

    private final boolean[][] shape;

    PatchShape(boolean[][] shape) {
        this.shape = shape;
    }

    public boolean[][] getShape() {
        return this.shape;
    }
}