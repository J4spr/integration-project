package be.kdg.programming.integrationproject.model;

public class PlayerStats {
    private final String username;
    private final int wins;
    private final int gamesPlayed;
    private final double winPercentage;
    private final int totalButtonsSpent;

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