package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.TokenColor;

public abstract class Player {
    private boolean hasSpecialTile;
    private int position;
    private int buttons;
    private TokenColor color;

    abstract void updatePosition();

    public boolean hasSpecialTile() {
        return this.hasSpecialTile;
    }

    public void setHasSpecialTile(boolean hasSpecialTile) {
        this.hasSpecialTile = hasSpecialTile;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getButtons() {
        return this.buttons;
    }

    public void setButtons(int buttons) {
        this.buttons = buttons;
    }

    public TokenColor getColor() {
        return this.color;
    }
}
