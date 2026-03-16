package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.TokenColor;

public abstract class Player {
    private boolean hasSpecialTile;
    private int position;
    private int totalButtons;
    private int totalButtonIncome;
    private QuiltBoard quiltBoard;
    private TokenColor color;

    public Player() {
        this.quiltBoard = new QuiltBoard();
    }

    public QuiltBoard getQuiltBoard() {
        return quiltBoard;
    }

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
}
