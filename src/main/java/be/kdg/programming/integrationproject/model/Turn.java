package be.kdg.programming.integrationproject.model;

import java.sql.Time;

/**
 * Database-focused audit mirror tracking individual turn timelines within a match.
 * Links move series logs to a parent game entry.
 *
 * @author Team 4
 * @version 1.0
 */
public class Turn {
    private int turnId;
    private int gameId;
    private Time turnStartTime;
    private Time turnEndTime;

    /**
     * Default constructor for initializing empty turn wrappers.
     */
    public Turn() {}

    /**
     * Full constructor for mapping historical turn metrics retrieved from the database.
     *
     * @param turnId        the unique primary key identifier tracking this turn sequence row
     * @param gameId        the parent reference key linking the turn to its corresponding match
     * @param turnStartTime the timestamp marking when the turn option opened
     * @param turnEndTime   the timestamp marking when the turn closed
     */
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