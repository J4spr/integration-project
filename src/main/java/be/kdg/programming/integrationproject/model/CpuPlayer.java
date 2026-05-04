package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

import java.util.List;

public class CpuPlayer extends Player {
    private Difficulty difficulty;
    private static class Move{
        int patchId;
        int row;
        int col;
        PatchRotation rotation;
        double score;

        public Move(int patchId,int row,int col,PatchRotation rotation,double score){
            this.patchId=patchId;
            this.row=row;
            this.col=col;
            this.rotation=rotation;
            this.score=score;
        }
    }

    public CpuPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    void updatePosition(int steps) {
        this.setPosition(this.getPosition() + steps);
    }

    public void decideTurn(Game game){

        Move bestMove = findBestMove(game);

        if(bestMove != null){
            game.buyAndPlacePatch(
                    bestMove.patchId,
                    bestMove.row,
                    bestMove.col,
                    bestMove.rotation
            );
        }
        else{
            game.pass();
        }

    }

    private Move findBestMove(Game game) {
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
            Player other = (game.getCurrentPlayer()==game.getPlayer1())
                    ? game.getPlayer2()
                    : game.getPlayer1();

            int passGain = (other.getPosition()+1) - this.getPosition();
        }

        Player other = (game.getCurrentPlayer()==game.getPlayer1())
                ? game.getPlayer2()
                : game.getPlayer1();

        int passGain = (other.getPosition()+1) - this.getPosition();

        if(bestPatch != null && bestScore > passGain){
            return new Move(bestPatch.getPatchID(), bestRow, bestCol, bestRotation, bestScore
            );
        }

        return null;
    }



    private double calculateMoveScore(Game game, Patch patch, int row, int col){

        double score = 0;

// 1. grootte van patch (belangrijk)
        boolean[][] shape = patch.getRotatedShape();
        int filled = 0;

        for(int r=0;r<shape.length;r++){
            for(int c=0;c<shape[r].length;c++){
                if(shape[r][c]) filled++;
            }
        }
        score += filled * 2.5;

// 2. button income (heel belangrijk)
        score += patch.getButtonIncome() * 3.0;

// 3. kost penalty
        score -= patch.getButtonCost() * 1.5;

// 4. time penalty
        score -= patch.getTimeCost() * 1.0;

// 5. bonus: centrum voorkeur (slim!)
        int centerDist = Math.abs(4 - row) + Math.abs(4 - col);
        score -= centerDist * 0.3;

        return score;
    }
}

