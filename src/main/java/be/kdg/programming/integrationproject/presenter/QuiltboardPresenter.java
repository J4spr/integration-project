package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.view.GameView;

public class QuiltboardPresenter {

    private final Game game;
    private final GameView view;
    //reference to the game presenter to access shared state and trigger post-move logic
    private final GamePresenter gamePresenter;

    public QuiltboardPresenter(Game game, GameView view, GamePresenter gamePresenter) {
        this.game = game;
        this.view = view;
        this.gamePresenter = gamePresenter;
        addEventHandlers();
    }

    private void addEventHandlers() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int row = r;
                int col = c;

                //player 1 board only responds when it is player 1's turn
                view.getQuiltboardViewP1().getCells()[r][c].setOnAction(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer1()) {
                        handleBoardClick(row, col);
                    }
                });

                //player 2 board only responds when it is player 2's turn
                view.getQuiltboardViewP2().getCells()[r][c].setOnAction(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer2()) {
                        handleBoardClick(row, col);
                    }
                });

                //hover preview on player 1 board
                view.getQuiltboardViewP1().getCells()[r][c].setOnMouseEntered(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer1()) {
                        showHoverPreview(row, col, true);
                    }
                });
                view.getQuiltboardViewP1().getCells()[r][c].setOnMouseExited(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer1()) {
                        clearHoverPreview(true);
                    }
                });

                //hover preview on player 2 board
                view.getQuiltboardViewP2().getCells()[r][c].setOnMouseEntered(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer2()) {
                        showHoverPreview(row, col, false);
                    }
                });
                view.getQuiltboardViewP2().getCells()[r][c].setOnMouseExited(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer2()) {
                        clearHoverPreview(false);
                    }
                });
            }
        }
    }

    //shows a preview of the selected patch on the quiltboard at the given position
    private void showHoverPreview(int row, int col, boolean isP1) {
        //no patch selected, nothing to preview
        if (gamePresenter.getSelectedPatchId() == -1) return;

        Patch patch = game.getPatchStack().getPatch(gamePresenter.getSelectedPatchId());
        if (patch == null) return;

        //apply the current rotation for the preview
        patch.setRotation(gamePresenter.getSelectedRotation());
        boolean[][] shape = patch.getRotatedShape();
        boolean[][] grid = isP1
                ? game.getPlayer1().getQuiltBoard().getGrid()
                : game.getPlayer2().getQuiltBoard().getGrid();

        //check if placement is valid to show green or red preview
        boolean canPlace = isP1
                ? game.getPlayer1().getQuiltBoard().canPlacePatch(patch, row, col)
                : game.getPlayer2().getQuiltBoard().canPlacePatch(patch, row, col);

        //green if valid placement, red if invalid
        String previewColor = canPlace ? "#a5d6a7" : "#ef9a9a";

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c]) {
                    int targetRow = row + r;
                    int targetCol = col + c;
                    //only color cells within bounds
                    if (targetRow >= 0 && targetRow < 9 && targetCol >= 0 && targetCol < 9) {
                        String cellStyle = "-fx-background-color: " + previewColor + "; -fx-border-color: #cccccc;";
                        if (isP1) {
                            view.getQuiltboardViewP1().getCells()[targetRow][targetCol].setStyle(cellStyle);
                        } else {
                            view.getQuiltboardViewP2().getCells()[targetRow][targetCol].setStyle(cellStyle);
                        }
                    }
                }
            }
        }
    }

    //resets all cells back to their current grid state after hover
    private void clearHoverPreview(boolean isP1) {
        boolean[][] grid = isP1
                ? game.getPlayer1().getQuiltBoard().getGrid()
                : game.getPlayer2().getQuiltBoard().getGrid();

        String occupiedColor = isP1
                ? gamePresenter.tokenColorToHex(game.getPlayer1().getColor())
                : gamePresenter.tokenColorToHex(game.getPlayer2().getColor());

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (isP1) {
                    view.getQuiltboardViewP1().setCell(r, c, grid[r][c], occupiedColor);
                } else {
                    view.getQuiltboardViewP2().setCell(r, c, grid[r][c], occupiedColor);
                }
            }
        }
    }

    private void handleBoardClick(int row, int col) {
        //if there are leather patches in the queue, the player must place one first
        if (!game.getLeatherPatchQueue().isEmpty()) {
            boolean placed = game.placeLeatherPatch(row, col);
            if (!placed) {
                gamePresenter.showWarningBanner("Invalid placement. Try a different position.");
                return;
            }
            gamePresenter.initializeView();
            //if the queue is now empty, continue with the normal game flow
            if (game.getLeatherPatchQueue().isEmpty()) {
                gamePresenter.handleCpuTurn();
                gamePresenter.checkGameEnd();
            }
            return;
        }

        //no patch selected, do nothing
        if (gamePresenter.getSelectedPatchId() == -1) {
            gamePresenter.showWarningBanner("Select a patch first.");
            return;
        }

        boolean success = game.buyAndPlacePatch(
                gamePresenter.getSelectedPatchId(),
                row,
                col,
                gamePresenter.getSelectedRotation()
        );

        if (!success) {
            gamePresenter.showWarningBanner("Invalid move. Check if you have enough buttons or if the patch fits.");
            return;
        }

        gamePresenter.resetSelection();
        gamePresenter.initializeView();
        gamePresenter.handleCpuTurn();
        gamePresenter.checkGameEnd();
    }

    //initializes both quiltboard views based on the current game state
    public void initializeView() {
        HumanPlayer p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();

        view.getQuiltboardViewP1().update(
                p1.getName(),
                p1.getTotalButtons(),
                p1.getTotalButtonIncome()
        );
        view.getQuiltboardViewP2().update(
                p2 instanceof CpuPlayer ? "CPU" : ((HumanPlayer) p2).getName(),
                p2.getTotalButtons(),
                p2.getTotalButtonIncome()
        );

        boolean[][] gridP1 = p1.getQuiltBoard().getGrid();
        boolean[][] gridP2 = p2.getQuiltBoard().getGrid();

        String colorP1 = gamePresenter.tokenColorToHex(p1.getColor());
        String colorP2 = gamePresenter.tokenColorToHex(p2.getColor());

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                view.getQuiltboardViewP1().setCell(r, c, gridP1[r][c], colorP1);
                view.getQuiltboardViewP2().setCell(r, c, gridP2[r][c], colorP2);
            }
        }
    }
}