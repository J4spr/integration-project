package be.kdg.programming.integrationproject.model.Enums;

public enum PatchShape {
    LEATHER_PATCH(new boolean[][]{
            {true}
    }),

    SMALL_LINE(new boolean[][]{
            {true, true}
    }),

    SMALL_L(new boolean[][]{
            {false, true},
            {true, true}
    }),

    BIG_LINE(new boolean[][]{
            {true},
            {true},
            {true}
    }),

    Z_SHAPE(new boolean[][]{
            {false, true},
            {true, true},
            {true, false}
    }),

    B_SHAPE(new boolean[][] {
        {true, false},
        {true, true},
        {true, true},
    }),

    WIDE_PLUS(new boolean[][]{
            {false, false, true, false, false},
            {true, true, true, true, true},
            {false, false, true, false, false}
    }),

    CROSS(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {true, true, true},
            {false, true, false}
    }),

    SQUARE(new boolean[][]{
            {true, true},
            {true, true}
    }),

    WIDE_Z_SHAPE(new boolean[][]{
            {true, false},
            {true, true},
            {true, true},
            {false, true}
    }),

    T_SHAPE(new boolean[][]{
            {false, true},
            {true, true},
            {false, true}
    }),

    C_SHAPE(new boolean[][]{
            {true, true},
            {false, true},
            {false, true},
            {true, true}
    }),

    VERY_BIG_LINE(new boolean[][]{
            {true},
            {true},
            {true},
            {true}
    }),

    L_PLUS_ONE(new boolean[][]{
            {true, false},
            {true, false},
            {true, true},
            {true, false}
    }),

    B_PLUS_ONE(new boolean[][]{
            {true, false},
            {true, true},
            {true, true},
            {true, false}
    }),

    UFO_SHAPE(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {true, false, true}
    }),

    TREE_SHAPE(new boolean[][]{
            {false, true, false},
            {false, true, true},
            {true, true, false},
            {false, true, false}
    }),

    BIG_L(new boolean[][]{
            {false, true},
            {false, true},
            {true, true}
    }),

    SMALL_PLUS(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, true, true}
    }),

    H_SHAPE(new boolean[][]{
            {true, false, true},
            {true, true, true},
            {true, false, true}
    }),

    WIDE_CROSS(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {true, true, true},
            {false, true, false}
    }),

    VERY_BIG_L(new boolean[][]{
            {false, true},
            {false, true},
            {false, true},
            {true, true},
    }),

    LONG_T(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {true, true, true}
    }),

    P_SHAPE(new boolean[][]{
            {true, true},
            {true, true},
            {false, true},
            {false, true}
    }),

    LONG_Z_SHAPE(new boolean[][]{
            {true, true, false},
            {false, true, false},
            {false, true, false},
            {false, true, false},
            {false, true, true}
    }),

    VERY_LONG_T(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {false, true, false},
            {true, true, true}
    }),

    STAIRS_SHAPE(new boolean[][]{
            {true, false, false},
            {true, true, false},
            {false, true, true}
    }),

    TALL_Z_SHAPE(new boolean[][]{
            {false, true},
            {false, true},
            {true, true},
            {true, false}
    }),

    KITE_SHAPE(new boolean[][]{
            {false, true, true},
            {false, true, true},
            {true, true, false}
    });

    private final boolean[][] shape;

    PatchShape(boolean[][] shape) {
        this.shape = shape;
    }

    public boolean[][] getShape() {
        return this.shape;
    }
}