package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

import java.util.List;

public class CpuPlayer extends Player {
    private Difficulty difficulty;

    public CpuPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    void updatePosition(int steps) {
        this.setPosition(this.getPosition() + steps);
    }

    public void decideTurn(Game game) {
        List<Patch> availablePatches = game.getPatchStack().getAvailablePatches();
        Patch bestPatch = null;
        int bestRow = -1;
        int bestCol = -1;
        PatchRotation bestRotation = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (Patch patch : availablePatches) {
            if (this.getTotalButtons() < patch.getButtonCost()) continue;

            for (PatchRotation rotation : PatchRotation.values()) {
                patch.setRotation(rotation);

                for (int r = 0; r < Quiltboard.getSize(); r++) {
                    for (int c = 0; c < Quiltboard.getSize(); c++) {
                        // canPlacePatch is defined in your Quiltboard.java
                        if (this.getQuiltBoard().canPlacePatch(patch, r, c)) {
                            double currentScore = calculateMoveScore(game, patch, r, c);

                            if (currentScore > bestScore) {
                                bestScore = currentScore;
                                bestPatch = patch;
                                bestRow = r;
                                bestCol = c;
                                bestRotation = rotation;
                            }
                        }
                    }
                }
            }
        }

        Player otherPlayer = (game.getCurrentPlayer() == game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
        int passButtons = (otherPlayer.getPosition() + 1) - this.getPosition();

        if (bestPatch != null && bestScore >= (double) passButtons) {
            game.buyAndPlacePatch(bestPatch.getPatchID(), bestRow, bestCol, bestRotation);
        } else {
            game.pass();
        }
    }

    private double calculateMoveScore(Game game, Patch patch, int row, int col) {
        double score = 0;

        if (difficulty == Difficulty.EASY) {
            return 100 - patch.getButtonCost();
        }

        score += patch.getButtonIncome() * 3.0;
        score -= patch.getTimeCost() * 1.5;
        score -= patch.getButtonCost() * 0.5;

        if (difficulty == Difficulty.HARD) {
            if (this.getQuiltBoard().hasSevenBySeven()) score += 7.0;

            int futurePos = game.getTimeboard().updatePosition(this.getPosition(), patch.getTimeCost());
            if (futurePos == 26 || futurePos == 32 || futurePos == 38 || futurePos == 44 || futurePos == 50) {
                score += 15.0;
            }
        }

        return score;
    }
}