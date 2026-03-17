package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

import java.sql.Time;

public class Move {
    private int moveId;
    private Turn turnId;
    private Patch patchId;
    private Time startTime;
    private Time endTime;
    private int specialPatchesCollected;
    private int spacesMoved;
    private int position;
    private PatchRotation rotation;
    private int ButtonsP1;
    private int ButtonsP2;

    public Move(Time startTime, Time endTime){

    }

}
