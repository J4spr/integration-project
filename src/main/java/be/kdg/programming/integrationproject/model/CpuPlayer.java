package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;

public class CpuPlayer extends Player {
    private Difficulty difficulty;

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    void updatePosition(int steps) {
        // implementatie komt later
    }

    public void decideTurn(Game game) {
        // implementatie komt later
    }
}