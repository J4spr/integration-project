package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.Enums.PatchShape;

public class Patch {
    private int patchID;
    private PatchShape shape;
    private PatchRotation rotation;
    private int buttonCost;
    private int timeCost;
    private int buttonIncome;

    public Patch(int patchID, PatchShape shape, int buttonCost, int timeCost, int buttonIncome) {
        this.patchID = patchID;
        this.shape = shape;
        this.buttonCost = buttonCost;
        this.timeCost = timeCost;
        this.buttonIncome = buttonIncome;
        this.rotation = PatchRotation.NOROTATION;
    }

    public int getPatchID() {
        return this.patchID;
    }

    public PatchShape getShape() {
        return this.shape;
    }

    public PatchRotation getRotation() {
        return this.rotation;
    }

    public void setRotation(PatchRotation rotation) {
        this.rotation = rotation;
    }

    public int getButtonCost() {
        return this.buttonCost;
    }

    public int getTimeCost() {
        return this.timeCost;
    }

    public int getButtonIncome() {
        return this.buttonIncome;
    }

    public static Patch createLeatherPatch(int patchID) {
        return new Patch(patchID, PatchShape.LEATHER_PATCH, 0, 0, 0);
    }

    //returns the patch shape as a 2D boolean array rotated according to the current rotation
    //the dimensions of the array change when rotating 90 or 270 degrees (rows and cols are swapped)
    public boolean[][] getRotatedShape() {
        return rotateShape(this.rotation);
    }

    //returns the rotated shape for a given rotation without modifying the patch's own rotation
    //used by the presenter for preview purposes only
    public boolean[][] getRotatedShapeFor(PatchRotation targetRotation) {
        return rotateShape(targetRotation);
    }

    //core rotation logic extracted so both getRotatedShape and getRotatedShapeFor can use it
    private boolean[][] rotateShape(PatchRotation targetRotation) {
        boolean[][] original = this.shape.getShape();
        int rows = original.length;
        int cols = original[0].length;

        switch (targetRotation) {
            case NINETY: {
                //rotating 90 degrees clockwise: new dimensions are [cols][rows]
                boolean[][] rotated = new boolean[cols][rows];
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < cols; c++)
                        rotated[c][rows - 1 - r] = original[r][c];
                return rotated;
            }
            case ONEEIGHTY: {
                //rotating 180 degrees: dimensions stay the same, values are mirrored
                boolean[][] rotated = new boolean[rows][cols];
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < cols; c++)
                        rotated[rows - 1 - r][cols - 1 - c] = original[r][c];
                return rotated;
            }
            case TWOSEVENTY: {
                //rotating 270 degrees clockwise: new dimensions are [cols][rows]
                boolean[][] rotated = new boolean[cols][rows];
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < cols; c++)
                        rotated[cols - 1 - c][r] = original[r][c];
                return rotated;
            }
            default:
                //no rotation, return original shape
                return original;
        }
    }
}