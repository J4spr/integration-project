package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.TokenColor;

public abstract class Player {
    private int playerId;
    private int position;
    private int totalButtons;
    private int totalButtonIncome;
    private Quiltboard quiltBoard;
    private TokenColor color;

    //constructor
    public Player() {
        this.quiltBoard = new Quiltboard();
    }

    //getters & setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Quiltboard getQuiltBoard() {
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

    public void setColor(TokenColor color) {
        this.color = color;
    }
}