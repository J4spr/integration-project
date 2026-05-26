package be.kdg.programming.integrationproject.model.Enums;

/**
 * Tracks the structural state execution boundaries of a given game session lifecycle.
 *
 * @author Team 4
 * @version 1.0
 */
public enum GameStatus {
    /** Represents an actively executing game match currently receiving player moves. */
    ACTIVE("active"),

    /** Indicates a completed match where endpoints have been reached and scored. */
    FINISHED("finished"),

    /** Marks a preserved match saved to persistence structures for future resumption. */
    PAUSED("paused");

    /** The lower-case descriptor tag representing game status. */
    private final String status;

    /**
     * Constructs a GameStatus state assignment property item.
     *
     * @param status the string value mapping state definitions
     */
    private GameStatus(String status){
        this.status = status;
    }

    /**
     * Gets the lower-case tracking descriptor label denoting game state status.
     *
     * @return the tracking status text value
     */
    public String getStatus() {
        return this.status;
    }
}