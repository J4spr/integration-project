package be.kdg.programming.integrationproject.model;

import java.sql.Time;

public class Turn {
    private int turnId;
    private int gameId;
    private Time turnStartTime;
    private Time turnEndTime;

    public Turn() {}

    public Turn(int turnId, int gameId, Time turnStartTime, Time turnEndTime) {
        this.turnId = turnId;
        this.gameId = gameId;
        this.turnStartTime = turnStartTime;
        this.turnEndTime = turnEndTime;
    }

    public int getTurnId() { return turnId; }
    public void setTurnId(int turnId) { this.turnId = turnId; }

    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public Time getTurnStartTime() { return turnStartTime; }
    public void setTurnStartTime(Time turnStartTime) { this.turnStartTime = turnStartTime; }

    public Time getTurnEndTime() { return turnEndTime; }
    public void setTurnEndTime(Time turnEndTime) { this.turnEndTime = turnEndTime; }
}