package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.TokenColor;

/**
 * Abstract base class representing a generic participant in the game.
 * Maintains core state attributes including unique tracking IDs, track progress,
 * button bank balances, button income rates, token colors, and the player's personal grid board.
 *
 * @author Team 4
 * @version 1.0
 */
public abstract class Player {
    /** The unique identification number assigned to this player by the persistence layer. */
    private int playerId;
    /** The linear tracking position index of the player's token on the timeboard. */
    private int position;
    /** The current spendable button currency bank balance of the player. */
    private int totalButtons;
    /** The aggregate button income received whenever a button event position is crossed. */
    private int totalButtonIncome;
    /** The personal matrix tile grid board owned by the player. */
    private Quiltboard quiltBoard;
    /** The assigned visual token color used to identify the player on UI components. */
    private TokenColor color;

    /**
     * Base constructor initializing an independent, empty structural {@link Quiltboard}
     * instance for the player.
     */
    public Player() {
        this.quiltBoard = new Quiltboard();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Quiltboard getQuiltBoard() {
        return quiltBoard;
    }

    /**
     * Abstract position mutation hook implemented by subclasses to handle timeline movement transformations.
     *
     * @param steps the count of spaces or indices to advance forward along the board path
     */
    abstract void updatePosition(int steps);

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTotalButtons() {
        return this.totalButtons;
    }

    public void setTotalButtons(int totalButtons) {
        this.totalButtons = totalButtons;
    }

    public int getTotalButtonIncome() {
        return totalButtonIncome;
    }

    public void setTotalButtonIncome(int totalButtonIncome) {
        this.totalButtonIncome = totalButtonIncome;
    }

    public TokenColor getColor() {
        return this.color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }
}