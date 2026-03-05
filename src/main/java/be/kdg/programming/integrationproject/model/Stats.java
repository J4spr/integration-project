package be.kdg.programming.integrationproject.model;

public class Stats extends HumanPlayer {
    private int gamesWon;
    private int gamesLost;
    private int longestWinStreak;
    private int totalScore;
    private int totalPlayerTime;

    public int getGamesWon() {
        return this.gamesWon;
    }

    public int getGamesLost() {
        return this.gamesLost;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public void updateStats(int score, boolean won) {
        if (won)
            gamesWon++;
        if (!(won))
            gamesLost++;

    }
}
