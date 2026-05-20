package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

import java.util.List;

/**
 * Represents an AI automated competitor that evaluates optimal geometric
 * placement choices based on its assigned difficulty configurations.
 *
 * @author Team 4
 * @version 1.0
 */
public class CpuPlayer extends Player {
    /** The difficulty settings scaling calculation preferences. */
    private Difficulty difficulty;

    /**
     * Inner structural container representing an appraised prospective match movement decision.
     */
    private static class Move {
        int patchId;
        int row;
        int col;
        PatchRotation rotation;
        double score;

        /**
         * Full constructor for evaluating move structures.
         *
         * @param patchId  id reference of target tile
         * @param row      grid target row
         * @param col      grid target column
         * @param rotation angular orientation option
         * @param score    computed dynamic evaluation rating
         */
        public Move(int patchId, int row, int col, PatchRotation rotation, double score){
            this.patchId = patchId;
            this.row = row;
            this.col = col;
            this.rotation = rotation;
            this.score = score;
        }
    }

    /**
     * Constructs a Computer AI opponent container with an initial difficulty scale.
     *
     * @param difficulty execution behavioral difficulty limits
     */
    public CpuPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    void updatePosition(int steps) {
        this.setPosition(this.getPosition() + steps);
    }

    /**
     * Entry hook interface driving the automated movement engine.
     * Inspects game parameters to select the best patch placement or passively advance.
     *
     * @param game active engine context session configuration target
     */
    public void decideTurn(Game game){
        Move bestMove = findBestMove(game);

        if(bestMove != null){
            game.buyAndPlacePatch(
                    bestMove.patchId,
                    bestMove.row,
                    bestMove.col,
                    bestMove.rotation
            );
        } else {
            game.pass();
        }
    }

    /**
     * Scans currently exposed stack structures across all coordinate vectors
     * and rotational matrices to extract the choice holding the highest scoring value.
     *
     * @param game matching environment tracking metrics
     * @return an optimized calculated Move profile, or {@code null} if skipping is preferred
     */
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

        Player other = (game.getCurrentPlayer() == game.getPlayer1())
                ? game.getPlayer2()
                : game.getPlayer1();

        int passGain = (other.getPosition() + 1) - this.getPosition();

        if(bestPatch != null && bestScore > passGain){
            return new Move(bestPatch.getPatchID(), bestRow, bestCol, bestRotation, bestScore);
        }

        return null;
    }

    /**
     * Evaluation formula weighting tile dimensions, economic revenue impact,
     * purchasing penalties, and board grid centralization choices.
     *
     * @param game  active target match framework instance
     * @param patch prospective target tile instance evaluation choice
     * @param row   board matrix coordinate entry selection index
     * @param col   board matrix coordinate entry selection index
     * @return an abstract preference value score (higher means better)
     */
    private double calculateMoveScore(Game game, Patch patch, int row, int col){
        double score = 0;

        // 1. Size of patch weight
        boolean[][] shape = patch.getRotatedShape();
        int filled = 0;

        for(int r = 0; r < shape.length; r++){
            for(int c = 0; c < shape[r].length; c++){
                if(shape[r][c]) filled++;
            }
        }
        score += filled * 2.5;

        // 2. Button income weight
        score += patch.getButtonIncome() * 3.0;

        // 3. Cost penalty
        score -= patch.getButtonCost() * 1.5;

        // 4. Time penalty
        score -= patch.getTimeCost() * 1.0;

        // 5. Center proximity bias
        int centerDist = Math.abs(4 - row) + Math.abs(4 - col);
        score -= centerDist * 0.3;

        return score;
    }
}