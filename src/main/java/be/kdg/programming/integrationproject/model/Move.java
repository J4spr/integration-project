package be.kdg.programming.integrationproject.model;

import java.sql.Time;

/**
 * Audit record entity container capturing structural step metrics
 * for individual player actions. Primarily used for database syncing.
 *
 * @author Team 4
 * @version 1.0
 */
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

    /**
     * Default constructor for initializing empty move records.
     */
    public Move() {}

    /**
     * Comprehensive constructor for rebuilding historical entries from the database.
     *
     * @param moveId                  unique primary move serial index identifier code
     * @param turnId                  associated reference grouping turn entity index logs
     * @param patchId                 the target tile structural segment identity key
     * @param moveStartTime           temporal stamp identifying choice start boundaries
     * @param moveEndTime             temporal stamp tracking step termination benchmarks
     * @param specialPatchesCollected tracking amount logging specialized single patch captures
     * @param spacesMoved             linear tracking increment metric step scaling index
     * @param position                the position on the track layout timeline
     * @param rotationDegrees         angular tracking metric parameter value choice logs
     * @param buttonsP1               score balance of player 1 at this step boundary
     * @param buttonsP2               score balance of player 2 at this step boundary
     */
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

    public int getMoveId() { return moveId; }
    public void setMoveId(int moveId) { this.moveId = moveId; }
    public int getTurnId() { return turnId; }
    public void setTurnId(int turnId) { this.turnId = turnId; }
    public int getPatchId() { return patchId; }
    public void setPatchId(int patchId) { this.patchId = patchId; }
    public Time getMoveStartTime() { return moveStartTime; }
    public void setMoveStartTime(Time moveStartTime) { this.moveStartTime = moveStartTime; }
    public Time getMoveEndTime() { return moveEndTime; }
    public void setMoveEndTime(Time moveEndTime) { this.moveEndTime = moveEndTime; }
    public int getSpecialPatchesCollected() { return specialPatchesCollected; }
    public void setSpecialPatchesCollected(int specialPatchesCollected) { this.specialPatchesCollected = specialPatchesCollected; }
    public int getSpacesMoved() { return spacesMoved; }
    public void setSpacesMoved(int spacesMoved) { this.spacesMoved = spacesMoved; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public int getRotationDegrees() { return rotationDegrees; }
    public void setRotationDegrees(int rotationDegrees) { this.rotationDegrees = rotationDegrees; }
    public int getButtonsP1() { return buttonsP1; }
    public void setButtonsP1(int buttonsP1) { this.buttonsP1 = buttonsP1; }
    public int getButtonsP2() { return buttonsP2; }
    public void setButtonsP2(int buttonsP2) { this.buttonsP2 = buttonsP2; }
}