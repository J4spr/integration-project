package be.kdg.programming.integrationproject.model;

import java.sql.Time;

public class Move {
    private int moveId;

    private int turnId;
    private int patchId;

    private Time moveStartTime;
    private Time moveEndTime;

    private int specialPatchesCollected;
    private int spacesMoved;
    private int position;
    private int rotationDegrees;
    private int buttonsP1;
    private int buttonsP2;

    public Move() {}

    // Full constructor for the DAO to use when fetching from the DB
    public Move(int moveId, int turnId, int patchId, Time moveStartTime, Time moveEndTime,
                int specialPatchesCollected, int spacesMoved, int position,
                int rotationDegrees, int buttonsP1, int buttonsP2) {
        this.moveId = moveId;
        this.turnId = turnId;
        this.patchId = patchId;
        this.moveStartTime = moveStartTime;
        this.moveEndTime = moveEndTime;
        this.specialPatchesCollected = specialPatchesCollected;
        this.spacesMoved = spacesMoved;
        this.position = position;
        this.rotationDegrees = rotationDegrees;
        this.buttonsP1 = buttonsP1;
        this.buttonsP2 = buttonsP2;
    }

    // Getters and Setters for all fields...
    public int getMoveId() { return moveId; }
    public void setMoveId(int moveId) { this.moveId = moveId; }

    // ... repeat for other fields
}