package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.Enums.PatchShape;

/**
 * Domain component tracking parameters for individual game tiles, including costs,
 * geometry blueprints, income generation, and structural rotation matrices.
 *
 * @author Team 4
 * @version 1.0
 */
public class Patch {
    private int patchID;
    private PatchShape shape;
    private PatchRotation rotation;
    private int buttonCost;
    private int timeCost;
    private int buttonIncome;

    /**
     * Constructs a standard patch tile instance.
     *
     * @param patchID      unique identity sequence integer value
     * @param shape        the geometric pattern blueprint classification mapping
     * @param buttonCost   purchase currency threshold requirement value
     * @param timeCost     linear progression timeline tracking steps addition
     * @param buttonIncome repetitive resource addition asset score value factor
     */
    public Patch(int patchID, PatchShape shape, int buttonCost, int timeCost, int buttonIncome) {
        this.patchID = patchID;
        this.shape = shape;
        this.buttonCost = buttonCost;
        this.timeCost = timeCost;
        this.buttonIncome = buttonIncome;
        this.rotation = PatchRotation.NOROTATION;
    }

    public int getPatchID() { return this.patchID; }
    public PatchShape getShape() { return this.shape; }
    public PatchRotation getRotation() { return this.rotation; }
    public void setRotation(PatchRotation rotation) { this.rotation = rotation; }
    public int getButtonCost() { return this.buttonCost; }
    public int getTimeCost() { return this.timeCost; }
    public int getButtonIncome() { return this.buttonIncome; }

    /**
     * Factory method creating a specialized single-cell leather patch.
     *
     * @param patchID designated tracking identifier code index sequence reference
     * @return a premium custom single cell asset {@link Patch} model component configuration
     */
    public static Patch createLeatherPatch(int patchID) {
        return new Patch(999, PatchShape.LEATHER_PATCH, 0, 0, 0);
    }

    /**
     * Rotates the multi-dimensional boolean grid array to reflect the patch's current rotation state.
     * Swaps rows and columns during 90-degree or 270-degree adjustments.
     *
     * @return a transformed two-dimensional boolean matrix grid matching active settings
     */
    public boolean[][] getRotatedShape() {
        return rotateShape(this.rotation);
    }

    /**
     * Generates a preview matrix for a target orientation without altering the patch's internal state.
     * Primarily used by presenter layers for placement previews.
     *
     * @param targetRotation the prospective transform direction layer option configuration to test
     * @return a temporary independent blueprint multi-array mirroring the evaluated choice layout parameters
     */
    public boolean[][] getRotatedShapeFor(PatchRotation targetRotation) {
        return rotateShape(targetRotation);
    }

    /**
     * Core mapping engine transforming raw boolean matrices through
     * index transpose steps based on selected rotation angles.
     *
     * @param targetRotation the active angle transformation matrix mapping parameter layout choice
     * @return updated target nested array grid structures representing shifted layout limits
     */
    private boolean[][] rotateShape(PatchRotation targetRotation) {
        boolean[][] original = this.shape.getShape();
        int rows = original.length;
        int cols = original[0].length;

        switch (targetRotation) {
            case NINETY: {
                boolean[][] rotated = new boolean[cols][rows];
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < cols; c++)
                        rotated[c][rows - 1 - r] = original[r][c];
                return rotated;
            }
            case ONEEIGHTY: {
                boolean[][] rotated = new boolean[rows][cols];
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < cols; c++)
                        rotated[rows - 1 - r][cols - 1 - c] = original[r][c];
                return rotated;
            }
            case TWOSEVENTY: {
                boolean[][] rotated = new boolean[cols][rows];
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < cols; c++)
                        rotated[cols - 1 - c][r] = original[r][c];
                return rotated;
            }
            default:
                return original;
        }
    }
}