package be.kdg.programming.integrationproject.model.Enums;

public enum PatchRotation {
    NOROTATION(0),//standaard rotatie van een shape
    NINETY(90),
    ONEEIGHTY(180),
    TWOSEVENTY(270);

    private int rotation;

    PatchRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return this.rotation;
    }

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
