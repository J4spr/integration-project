package be.kdg.programming.integrationproject.model.Enums;

public enum PatchRotation {
    NINETY(90),
    ONEEIGHTY(180),
    TWOSEVENTY(270),
    THREESIXTY(360);

    private int rotation;
    private PatchRotation(int rotation){
        this.rotation = rotation;
    }

    public int getRotation() {
        return this.rotation;
    }
}
