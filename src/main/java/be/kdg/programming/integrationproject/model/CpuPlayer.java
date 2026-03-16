package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;

public class CpuPlayer extends Player {
    private Difficulty difficulty;

    public CpuPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    void updatePosition(int steps) {
        this.setPosition(this.getPosition() + steps);
    }

    public void decideTurn(Game game) {
        // implementatie komt later
    }
}