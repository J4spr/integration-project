package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.Enums.PatchShape;

/**
 * Factory builder mapping standard ruleset values (costs, times, and rewards)
 * to shape types to initialize a full game stack.
 *
 * @author Team 4
 * @version 1.0
 */
public class PatchStackBuilder {

    /**
     * Generates, links, pre-rotates duplicates, and shuffles all standard patches
     * to return a populated stack structure.
     * Note: Single-cell leather patches are excluded from initialization.
     *
     * @return a randomized, fully populated {@link PatchStack} instance configuration
     */
    public static PatchStack build() {
        PatchStack stack = new PatchStack();
        int id = 1;

        // SMALL_LINE
        stack.addPatch(new Patch(id++, PatchShape.SMALL_LINE, 2, 1, 0));
        // SMALL_L
        stack.addPatch(new Patch(id++, PatchShape.SMALL_L, 1, 3, 0));
        stack.addPatch(new Patch(id++, PatchShape.SMALL_L, 3, 1, 0));
        // BIG_LINE
        stack.addPatch(new Patch(id++, PatchShape.BIG_LINE, 2, 2, 0));
        // Z_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.Z_SHAPE, 3, 2, 1));

        Patch zShapeRotated = new Patch(id++, PatchShape.Z_SHAPE, 3, 2, 1);
        zShapeRotated.setRotation(PatchRotation.NINETY);
        stack.addPatch(zShapeRotated);

        // B_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.B_SHAPE, 2, 2, 0));
        // WIDE_PLUS
        stack.addPatch(new Patch(id++, PatchShape.WIDE_PLUS, 1, 4, 1));
        // CROSS
        stack.addPatch(new Patch(id++, PatchShape.CROSS, 0, 3, 1));
        // SQUARE
        stack.addPatch(new Patch(id++, PatchShape.SQUARE, 6, 5, 2));
        // WIDE_Z_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.WIDE_Z_SHAPE, 4, 2, 0));
        // T_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.T_SHAPE, 2, 2, 0));
        // C_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.C_SHAPE, 1, 2, 0));

        Patch cShapeRotated = new Patch(id++, PatchShape.C_SHAPE, 7, 1, 1);
        cShapeRotated.setRotation(PatchRotation.NINETY);
        stack.addPatch(cShapeRotated);

        // VERY_BIG_LINE
        stack.addPatch(new Patch(id++, PatchShape.VERY_BIG_LINE, 3, 3, 1));

        Patch veryBigLineRotated = new Patch(id++, PatchShape.VERY_BIG_LINE, 7, 1, 1);
        veryBigLineRotated.setRotation(PatchRotation.NINETY);
        stack.addPatch(veryBigLineRotated);

        // L_PLUS_ONE
        stack.addPatch(new Patch(id++, PatchShape.L_PLUS_ONE, 3, 4, 1));
        // B_PLUS_ONE
        stack.addPatch(new Patch(id++, PatchShape.B_PLUS_ONE, 7, 4, 2));
        // UFO_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.UFO_SHAPE, 3, 6, 2));
        // TREE_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.TREE_SHAPE, 2, 1, 0));
        // BIG_L
        stack.addPatch(new Patch(id++, PatchShape.BIG_L, 4, 6, 2));

        Patch bigLMirrored = new Patch(id++, PatchShape.BIG_L, 4, 2, 1);
        bigLMirrored.setRotation(PatchRotation.ONEEIGHTY);
        stack.addPatch(bigLMirrored);

        // SMALL_PLUS
        stack.addPatch(new Patch(id++, PatchShape.SMALL_PLUS, 5, 4, 2));
        // H_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.H_SHAPE, 2, 3, 0));
        // WIDE_CROSS
        stack.addPatch(new Patch(id++, PatchShape.WIDE_CROSS, 5, 3, 1));
        // VERY_BIG_L
        stack.addPatch(new Patch(id++, PatchShape.VERY_BIG_L, 10, 3, 2));
        // LONG_T
        stack.addPatch(new Patch(id++, PatchShape.LONG_T, 5, 5, 2));
        // P_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.P_SHAPE, 10, 5, 3));
        // LONG_Z_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.LONG_Z_SHAPE, 1, 2, 0));
        // VERY_LONG_T
        stack.addPatch(new Patch(id++, PatchShape.VERY_LONG_T, 7, 2, 2));
        // STAIRS_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.STAIRS_SHAPE, 10, 4, 3));
        // TALL_Z_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.TALL_Z_SHAPE, 2, 3, 1));
        // KITE_SHAPE
        stack.addPatch(new Patch(id++, PatchShape.KITE_SHAPE, 8, 6, 3));

        stack.shuffle();
        return stack;
    }
}