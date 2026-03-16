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
        this.rotation = PatchRotation.THREESIXTY; // standaard geen rotatie
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
}
