package be.kdg.programming.integrationproject.view.game;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.GameStatus;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import be.kdg.programming.integrationproject.view.quiltboard.QuiltboardPresenter;
import be.kdg.programming.integrationproject.view.resultsScreen.ResultsScreenPresenter;
import be.kdg.programming.integrationproject.view.timeBoard.TimeboardPresenter;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import be.kdg.programming.integrationproject.view.resultsScreen.ResultsScreenView;
import java.util.Random;
import be.kdg.programming.integrationproject.dao.GameDao;

/**
 * Top-level gameplay controller anchoring the MVP game architecture.
 * Manages player actions, synchronizes database states, controls automated
 * CPU workflows, and checks game-end victory parameters.
 *
 * @author Team 4
 * @version 1.0
 */
public class GamePresenter {
    private final Game game;
    private final GameView view;
    private final MainMenuView mainMenuView;

    private final QuiltboardPresenter quiltboardPresenter;
    private final PatchStackPresenter patchStackPresenter;
    private final TimeboardPresenter timeboardPresenter;

    private int selectedPatchId = -1;
    private PatchRotation selectedRotation;

    /**
     * Initializes the core game presenter, binds sub-component controllers,
     * and handles initial turn assignment.
     *
     * @param game         active domain state model tracking session configuration parameters
     * @param view          gameplay staging panel layout node package components
     * @param mainMenuView fall-back parent container menu mapping framework trace hooks
     */
    public GamePresenter(Game game, GameView view, MainMenuView mainMenuView) {
        this.game = game;
        this.view = view;
        this.mainMenuView = mainMenuView;

        this.quiltboardPresenter = new QuiltboardPresenter(this.game, this.view, this);
        this.patchStackPresenter = new PatchStackPresenter(this.game, this.view, this);
        this.timeboardPresenter = new TimeboardPresenter(this.game, this.view, this);

        this.handleCpuTurn();
        this.initializeView();
        this.addEventHandlers();
    }

    /**
     * Synchronizes view metrics across all child presenters to update UI text fields.
     */
    public void initializeView() {
        this.quiltboardPresenter.initializeView();
        this.patchStackPresenter.initializeView();
        this.timeboardPresenter.initializeView();
    }

    /**
     * Configures button listeners for turn progression, layout adjustments,
     * pause actions, and session serialization tasks.
     */
    private void addEventHandlers() {
        this.view.getBtnPass().setOnAction(e -> {
            if (!this.game.getCurrentLeatherPatchQueue().isEmpty()) {
                this.view.showWarningBanner("Place your leather patch first before passing.");
                return;
            }
            this.game.pass();
            this.resetSelection();
            this.notifyLeatherPatchIfNeeded();
            this.initializeView();
            if (this.game.getCurrentLeatherPatchQueue().isEmpty()) {
                this.game.updateCurrentPlayer();
                this.handleCpuTurn();
                this.checkGameEnd();
            }
        });

        this.view.getBtnRotate().setOnAction(e -> {
            if (this.selectedPatchId == -1) {
                this.view.showWarningBanner("Select a patch first to rotate it.");
                return;
            }
            this.selectedRotation = this.selectedRotation.next();
            this.initializeView();
        });

        this.view.getBtnPause().setOnAction(e -> {
            try {
                GameDao dao = new GameDao(new DbConnection());
                dao.savePausedState(game);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            this.view.showConfirmationOverlay(
                    "Game paused. Return to main menu?",
                    () -> this.view.getPane().getScene().setRoot(this.mainMenuView.getPane()));
        });

        this.view.getBtnQuit().setOnAction(e ->
                this.view.showConfirmationOverlay(
                        "Are you sure you want to quit to the main menu?",
                        () -> this.view.getPane().getScene().setRoot(this.mainMenuView.getPane())
                )
        );
    }

    /**
     * Loops turn operations while an automated {@link CpuPlayer} holds control.
     * Coordinates the placement of collected rewards before evaluating standard choices.
     */
    public void handleCpuTurn() {
        while (this.game.getCurrentPlayer() instanceof CpuPlayer cpu) {
            if (!this.game.getLeatherPatchQueue(cpu).isEmpty()) {
                this.placeCpuLeatherPatches();
            }
            if (this.game.getStatus() == GameStatus.FINISHED) break;
            cpu.decideTurn(this.game);
            if (this.game.getStatus() == GameStatus.FINISHED) break;
            this.game.updateCurrentPlayer();
        }
        this.initializeView();
    }

    /**
     * Handles random coordinate lookups for automated CPU 1x1 tile placement tasks.
     */
    private void placeCpuLeatherPatches() {
        Random random = new Random();
        Player cpu = this.game.getCurrentPlayer();
        while (!this.game.getLeatherPatchQueue(cpu).isEmpty()) {
            boolean placed = false;
            for (int attempt = 0; attempt < 100 && !placed; attempt++) {
                int row = random.nextInt(9);
                int col = random.nextInt(9);
                placed = this.game.placeLeatherPatch(cpu, row, col);
            }
            if (!placed) break;
        }
    }

    /**
     * Inspects active collection tracking arrays to trigger interface notifications
     * when a player earns leather patches.
     */
    public void notifyLeatherPatchIfNeeded() {
        if (!this.game.getCurrentLeatherPatchQueue().isEmpty()) {
            int count = this.game.getCurrentLeatherPatchQueue().size();
            String msg = count == 1
                    ? "You collected a leather patch! Place it on your quiltboard."
                    : "You collected " + count + " leather patches! Place them on your quiltboard.";
            this.view.showInfoBanner(msg);
        }
    }

    /**
     * Displays the results view overlay if active models flag a finished status.
     */
    public void checkGameEnd() {
        if (this.game.getStatus() == GameStatus.FINISHED) {
            ResultsScreenView resultsScreenView = new ResultsScreenView();
            new ResultsScreenPresenter(this.game, resultsScreenView, this.mainMenuView, this.view.getPane());
            this.view.showResultsScreen(resultsScreenView);
        }
    }

    /**
     * Resets active structural component parameters back to default values.
     */
    public void resetSelection() {
        this.selectedPatchId = -1;
        this.selectedRotation = PatchRotation.NOROTATION;
    }

    /**
     * Utility converter mapping color enums to standard hexadecimal CSS style strings.
     *
     * @param color structural player enum color indicator code value matching token
     * @return hex color format web styling code string value representation
     */
    public String tokenColorToHex(TokenColor color) {
        if (color == null) return "#aaaaaa";
        return switch (color) {
            case RED -> "#ef5350";
            case GREEN -> "#66bb6a";
            case YELLOW -> "#ffee58";
            case BLUE -> "#42a5f5";
        };
    }

    public int getSelectedPatchId() { return this.selectedPatchId; }
    public void setSelectedPatchId(int selectedPatchId) { this.selectedPatchId = selectedPatchId; }
    public PatchRotation getSelectedRotation() { return this.selectedRotation; }
    public void setSelectedRotation(PatchRotation selectedRotation) { this.selectedRotation = selectedRotation; }
    public void showWarningBanner(String message) { this.view.showWarningBanner(message); }
    public void showInfoBanner(String message) { this.view.showInfoBanner(message); }
}