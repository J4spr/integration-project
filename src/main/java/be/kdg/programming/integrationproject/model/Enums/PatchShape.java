package be.kdg.programming.integrationproject.model.Enums;

/**
 * Houses structural, multi-dimensional boolean grid maps defining target dimensions
 * for tile patterns. Crucial for matrix intersection placement verification tasks.
 *
 * @author Team 4
 * @version 1.0
 */
public enum PatchShape {
    /** A basic single cell square block structure component layout configuration. */
    LEATHER_PATCH(new boolean[][]{
            {true}
    }),

    /** A short straight block line structural matrix layout pattern configuration. */
    SMALL_LINE(new boolean[][]{
            {true, true}
    }),

    /** A tight angular corner format component map structure pattern block configuration. */
    SMALL_L(new boolean[][]{
            {false, true},
            {true, true}
    }),

    /** A medium straight line column matrix footprint structure arrangement representation. */
    BIG_LINE(new boolean[][]{
            {true},
            {true},
            {true}
    }),

    /** An interlocking staggered step profile block footprint configuration map blueprint. */
    Z_SHAPE(new boolean[][]{
            {false, true},
            {true, true},
            {true, false}
    }),

    /** A robust blocked multi-row element tracking array design blueprint layout profile. */
    B_SHAPE(new boolean[][] {
            {true, false},
            {true, true},
            {true, true},
    }),

    /** An elongated cross format tracking model structure layout map framework indicator. */
    WIDE_PLUS(new boolean[][]{
            {false, false, true, false, false},
            {true, true, true, true, true},
            {false, false, true, false, false}
    }),

    /** A dense cross matrix configuration schema layer layout trace overview framework. */
    CROSS(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {true, true, true},
            {false, true, false}
    }),

    /** A uniform two-by-two block cell arrangement grid layout framework. */
    SQUARE(new boolean[][]{
            {true, true},
            {true, true}
    }),

    /** An extended step tier diagonal layout trace alignment schematic mapping format. */
    WIDE_Z_SHAPE(new boolean[][]{
            {true, false},
            {true, true},
            {true, true},
            {false, true}
    }),

    /** A basic projecting intersecting block model configuration form structure blueprint. */
    T_SHAPE(new boolean[][]{
            {false, true},
            {true, true},
            {false, true}
    }),

    /** An inward facing channel recess tracking block form structural dimension layout. */
    C_SHAPE(new boolean[][]{
            {true, true},
            {false, true},
            {false, true},
            {true, true}
    }),

    /** An elongated vertical column structure boundary dimension layout mapping trace. */
    VERY_BIG_LINE(new boolean[][]{
            {true},
            {true},
            {true},
            {true}
    }),

    /** An extended corner layout model variant including a single structural cell stub. */
    L_PLUS_ONE(new boolean[][]{
            {true, false},
            {true, false},
            {true, true},
            {true, false}
    }),

    /** A modified configuration matrix shape variant including block element spurs. */
    B_PLUS_ONE(new boolean[][]{
            {true, false},
            {true, true},
            {true, true},
            {true, false}
    }),

    /** A distinct multi-branch layout pattern design map matrix blueprint framework. */
    UFO_SHAPE(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {true, false, true}
    }),

    /** An asymmetric branch pattern template layout design trace arrangement profile. */
    TREE_SHAPE(new boolean[][]{
            {false, true, false},
            {false, true, true},
            {true, true, false},
            {false, true, false}
    }),

    /** A classic standard right-angle corner profile setup format grid design trace. */
    BIG_L(new boolean[][]{
            {false, true},
            {false, true},
            {true, true}
    }),

    /** A tight cross format intersection layout design with corner extensions added. */
    SMALL_PLUS(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, true, true}
    }),

    /** A dual parallel pillar alignment structure bound together by center linkages. */
    H_SHAPE(new boolean[][]{
            {true, false, true},
            {true, true, true},
            {true, false, true}
    }),

    /** An expanded variation format footprint of a centralized cross design grid. */
    WIDE_CROSS(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {true, true, true},
            {false, true, false}
    }),

    /** An elongated corner format grid setup displaying tall aspect scaling parameters. */
    VERY_BIG_L(new boolean[][]{
            {false, true},
            {false, true},
            {false, true},
            {true, true},
    }),

    /** An extended vertical stem layout model setting ending in a base cross bar. */
    LONG_T(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {true, true, true}
    }),

    /** A block head shape element configuration attached down to single-sided track legs. */
    P_SHAPE(new boolean[][]{
            {true, true},
            {true, true},
            {false, true},
            {false, true}
    }),

    /** A highly complex multi-tier step structural layout alignment configuration framework. */
    LONG_Z_SHAPE(new boolean[][]{
            {true, true, false},
            {false, true, false},
            {false, true, false},
            {false, true, false},
            {false, true, true}
    }),

    /** An exceptionally tall intersecting pillar baseline format variant template tracking form. */
    VERY_LONG_T(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {false, true, false},
            {true, true, true}
    }),

    /** A regular incremental multi-level staircase layout pattern configuration mapping matrix. */
    STAIRS_SHAPE(new boolean[][]{
            {true, false, false},
            {true, true, false},
            {false, true, true}
    }),

    /** A tall offset cascading staggered block alignment template footprint setup layout. */
    TALL_Z_SHAPE(new boolean[][]{
            {false, true},
            {false, true},
            {true, true},
            {true, false}
    }),

    /** An offset skewed dual tier structural block tracking configuration template matrix. */
    KITE_SHAPE(new boolean[][]{
            {false, true, true},
            {false, true, true},
            {true, true, false}
    });

    /** The foundational dimensional grid mapping layout template array. */
    private final boolean[][] shape;

    /**
     * Constructs a PatchShape structure map defining core pattern coordinates.
     *
     * @param shape two-dimensional boolean primitive table map representing tile contours
     */
    PatchShape(boolean[][] shape) {
        this.shape = shape;
    }

    /**
     * Gets the underlying component primitive dimension matrix array.
     *
     * @return dimensional multi-array coordinates representing layout profiles
     */
    public boolean[][] getShape() {
        return this.shape;
    }
}