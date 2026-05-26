package be.kdg.programming.integrationproject.model.Enums;

/**
 * Tracks directional grid transformations and angular orientation states (in degrees)
 * for tile components. Provides cycle transitions for orientation shifts.
 *
 * @author Team 4
 * @version 1.0
 */
public enum PatchRotation {
    /** The default, baseline layout position at 0 degrees orientation. */
    NOROTATION(0),

    /** A quarter-turn clockwise layout modification at 90 degrees orientation. */
    NINETY(90),

    /** A half-turn transformation variation at 180 degrees orientation. */
    ONEEIGHTY(180),

    /** A three-quarters turn clockwise orientation layout at 270 degrees orientation. */
    TWOSEVENTY(270);

    /** The concrete numerical measurement indicating rotation scale. */
    private final int rotation;

    /**
     * Constructs a rotation identifier mapping integer angles.
     *
     * @param rotation concrete geometry angle reference in degrees
     */
    PatchRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets the concrete angular representation scale of the tracking state.
     *
     * @return directional transformation length in degrees
     */
    public int getRotation() {
        return this.rotation;
    }

    /**
     * Chronologically cycles to the adjacent incremental configuration step.
     * Simulates continuous 90-degree rightward movements, wrapping back cleanly
     * to zero from maximum configurations.
     *
     * @return the next sequential operational orientation angle state
     */
    public PatchRotation next() {
        int rotation = this.getRotation();
        PatchRotation nextRotation = NOROTATION;
        switch (rotation) {
            case 0 -> {
                nextRotation = NINETY;
            }
            case 90 -> {
                nextRotation = ONEEIGHTY;
            }
            case 180 -> {
                nextRotation = TWOSEVENTY;
            }
            case 270 -> {
                nextRotation = NOROTATION;
            }
        }
        return nextRotation;
    }
}