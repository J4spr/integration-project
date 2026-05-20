package be.kdg.programming.integrationproject.model.Enums;

/**
 * Defines the AI difficulty configurations for Computer Players (CPUs).
 *
 * @author Team 4
 * @version 1.0
 */
public enum Difficulty {
    /** Low performance, basic calculation AI logic setting. */
    EASY("easy"),

    /** Standard performance, balanced behavior AI logic setting. */
    MEDIUM("medium"),

    /** High performance, strategic forecasting AI logic setting. */
    HARD("hard");

    /** The string descriptor highlighting the difficulty level. */
    private final String difficulty;

    /**
     * Constructs a Difficulty enum entry with its associated description tag.
     *
     * @param difficulty the label identifying the difficulty setting
     */
    private Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the string configuration description label for the difficulty.
     *
     * @return the difficulty descriptor string
     */
    public String getDifficulty() {
        return this.difficulty;
    }
}