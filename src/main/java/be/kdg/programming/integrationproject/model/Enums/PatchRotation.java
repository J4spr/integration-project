package be.kdg.programming.integrationproject.model.Enums;

public enum PatchRotation {
    NOROTATION(0),//standaard rotatie van een shape
    NINETY(90),
    ONEEIGHTY(180),
    TWOSEVENTY(270);

    private int rotation;
    private PatchRotation(int rotation){
        this.rotation = rotation;
    }

    public int getRotation() {
        return this.rotation;
    }
}
