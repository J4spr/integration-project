package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import java.util.ArrayList;
import java.util.Random;

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

        Move selectedMove;

        switch (difficulty) {

            case EASY -> selectedMove = findRandomMove(game);

            case MEDIUM -> selectedMove = findBestMove(game);

            case HARD -> {

                Move blockingMove = findBlockingMove(game);

                if (blockingMove != null) {

                    selectedMove = blockingMove;

                } else {

                    selectedMove = findStrategicMove(game);
                }
            }

            default -> selectedMove = findBestMove(game);
        }

        if(selectedMove != null){

            game.buyAndPlacePatch(
                    selectedMove.patchId,
                    selectedMove.row,
                    selectedMove.col,
                    selectedMove.rotation
            );

        } else {

            game.pass();
        }
    }

    private Move findRandomMove(Game game) {

        List<Move> possibleMoves = new ArrayList<>();

        List<Patch> availablePatches = game.getPatchStack().getAvailablePatches();

        for (Patch patch : availablePatches) {

            if (this.getTotalButtons() < patch.getButtonCost()) continue;

            for (PatchRotation rotation : PatchRotation.values()) {

                patch.setRotation(rotation);

                for (int r = 0; r < Quiltboard.getSize(); r++) {

                    for (int c = 0; c < Quiltboard.getSize(); c++) {

                        if (this.getQuiltBoard().canPlacePatch(patch, r, c)) {

                            possibleMoves.add(
                                    new Move(
                                            patch.getPatchID(),
                                            r,
                                            c,
                                            rotation,
                                            0
                                    )
                            );
                        }
                    }
                }
            }
        }

        if (possibleMoves.isEmpty()) {
            return null;
        }

        Random random = new Random();

        return possibleMoves.get(
                random.nextInt(possibleMoves.size())
        );
    }

    private Move findBlockingMove(Game game) {

        Player opponent;

        if (game.getPlayer2() == this) {

            opponent = game.getPlayer1();

        } else {

            opponent = game.getPlayer1();
        }

        int opponentEmptySpaces =
                opponent.getQuiltBoard().countEmptySpaces();

        if (opponentEmptySpaces > 15) {
            return null;
        }

        List<Patch> availablePatches =
                game.getPatchStack().getAvailablePatches();

        Move bestBlockingMove = null;

        double bestBlockingScore = Double.NEGATIVE_INFINITY;

        for (Patch patch : availablePatches) {

            if (this.getTotalButtons() < patch.getButtonCost()) continue;

            for (PatchRotation rotation : PatchRotation.values()) {

                patch.setRotation(rotation);

                for (int r = 0; r < Quiltboard.getSize(); r++) {

                    for (int c = 0; c < Quiltboard.getSize(); c++) {

                        if (this.getQuiltBoard().canPlacePatch(patch, r, c)) {

                            double blockingScore =
                                    calculateBlockingScore(
                                            patch,
                                            opponent
                                    );

                            if (blockingScore > bestBlockingScore) {

                                bestBlockingScore = blockingScore;

                                bestBlockingMove = new Move(
                                        patch.getPatchID(),
                                        r,
                                        c,
                                        rotation,
                                        blockingScore
                                );
                            }
                        }
                    }
                }
            }
        }

        return bestBlockingMove;
    }

    private double calculateBlockingScore(
            Patch patch,
            Player opponent
    ) {

        double score = 0;

        boolean[][] shape = patch.getRotatedShape();

        int size = 0;

        for (int r = 0; r < shape.length; r++) {

            for (int c = 0; c < shape[r].length; c++) {

                if (shape[r][c]) {
                    size++;
                }
            }
        }

        score += size * 4;

        score += patch.getButtonIncome() * 3;

        score -= patch.getButtonCost();

        if (opponent.getQuiltBoard().countEmptySpaces() < 10) {

            score += 15;
        }

        return score;
    }

    private Move findStrategicMove(Game game) {

        List<Patch> availablePatches =
                game.getPatchStack().getAvailablePatches();

        Move bestMove = null;

        double bestScore = Double.NEGATIVE_INFINITY;

        for (Patch patch : availablePatches) {

            if (this.getTotalButtons() < patch.getButtonCost()) continue;

            for (PatchRotation rotation : PatchRotation.values()) {

                patch.setRotation(rotation);

                for (int r = 0; r < Quiltboard.getSize(); r++) {

                    for (int c = 0; c < Quiltboard.getSize(); c++) {

                        if (this.getQuiltBoard().canPlacePatch(patch, r, c)) {

                            double score =
                                    calculateStrategicScore(
                                            patch,
                                            r,
                                            c
                                    );

                            if (score > bestScore) {

                                bestScore = score;

                                bestMove = new Move(
                                        patch.getPatchID(),
                                        r,
                                        c,
                                        rotation,
                                        score
                                );
                            }
                        }
                    }
                }
            }
        }

        return bestMove;
    }

    private double calculateStrategicScore(
            Patch patch,
            int row,
            int col
    ) {

        double score =
                calculateMoveScore(
                        null,
                        patch,
                        row,
                        col
                );

        boolean[][] shape =
                patch.getRotatedShape();

        int touchingSides = 0;

        for (int r = 0; r < shape.length; r++) {

            for (int c = 0; c < shape[r].length; c++) {

                if (!shape[r][c]) continue;

                int boardRow = row + r;
                int boardCol = col + c;

                int[][] directions = {
                        {-1,0},
                        {1,0},
                        {0,-1},
                        {0,1}
                };

                for (int[] dir : directions) {

                    int nr = boardRow + dir[0];
                    int nc = boardCol + dir[1];

                    if (nr >= 0 && nr < 9 &&
                            nc >= 0 && nc < 9) {

                        if (this.getQuiltBoard()
                                .getGrid()[nr][nc]) {

                            touchingSides++;
                        }
                    }
                }
            }
        }

        score += touchingSides * 1.5;

        int emptySpaces =
                this.getQuiltBoard().countEmptySpaces();

        if (emptySpaces < 20) {

            score += 10;
        }

        return score;
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