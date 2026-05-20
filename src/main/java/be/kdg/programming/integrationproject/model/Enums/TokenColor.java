package be.kdg.programming.integrationproject.model.Enums;

/**
 * Represents the available token colors for players in the game.
 * Used to visually distinguish player positions and assets.
 *
 * @author Team 4
 * @version 1.0
 */
public enum TokenColor {
    /** Red token color identifier. */
    RED("red"),

    /** Green token color identifier. */
    GREEN("green"),

    /** Yellow token color identifier. */
    YELLOW("yellow"),

    /** Blue token color identifier. */
    BLUE("blue");

    /** The lowercase string representation of the color name. */
    private final String color;

    /**
     * Constructs a TokenColor enum element with its assigned string value.
     *
     * @param color the string literal name of the color
     */
    private TokenColor(String color){
        this.color = color;
    }

    /**
     * Gets the lowercase string literal value of the token color.
     *
     * @return the color string representation
     */
    public String getColor() {
        return this.color;
    }
}