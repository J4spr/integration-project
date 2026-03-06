package be.kdg.programming.integrationproject.model.Enums;

public enum Difficulty {
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
