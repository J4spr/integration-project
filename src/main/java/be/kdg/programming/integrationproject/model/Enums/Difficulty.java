package be.kdg.programming.integrationproject.model.Enums;

public enum Difficulty {
    RANDOM("random"),
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private String difficulty;

    private Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return this.difficulty;
    }
}