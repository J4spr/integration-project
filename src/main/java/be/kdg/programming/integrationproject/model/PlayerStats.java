package be.kdg.programming.integrationproject.model;

/**
 * Immutable data transfer container mapping structural performance statistics for leaderboards.
 * Captures historical match counts, win scales, spending traits, and computed success rates.
 *
 * @author Team 4
 * @version 1.0
 */
public class PlayerStats {
    private final String username;
    private final int wins;
    private final int gamesPlayed;
    private final double winPercentage;
    private final int totalButtonsSpent;

    /**
     * Constructs a populated statistical overview container profile.
     *
     * @param username          the unique identity tracking label handle of the profile
     * @param wins              the verified count of won matches
     * @param gamesPlayed       the total number of ongoing or finished matches registered
     * @param winPercentage     the calculated percentage ratio showing match victories
     * @param totalButtonsSpent the cumulative tracking metric monitoring historical button scores
     */
    public PlayerStats(String username, int wins, int gamesPlayed, double winPercentage, int totalButtonsSpent) {
        this.username = username;
        this.wins = wins;
        this.gamesPlayed = gamesPlayed;
        this.winPercentage = winPercentage;
        this.totalButtonsSpent = totalButtonsSpent;
    }

    public String getUsername() { return username; }
    public int getWins() { return wins; }
    public int getGamesPlayed() { return gamesPlayed; }
    public double getWinPercentage() { return winPercentage; }
    public int getTotalButtonsSpent() { return totalButtonsSpent; }
}