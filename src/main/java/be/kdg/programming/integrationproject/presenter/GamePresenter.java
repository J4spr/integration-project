package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.GameStatus;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.ResultsScreenView;
import java.util.Random;

public class GamePresenter {
    private final Game game;
    private final GameView view;
    private final MainMenuView mainMenuView;

    private final QuiltboardPresenter quiltboardPresenter;
    private final PatchStackPresenter patchStackPresenter;
    private final TimeboardPresenter timeboardPresenter;

    private int selectedPatchId = -1;
    private PatchRotation selectedRotation;

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

    public void initializeView() {
        this.quiltboardPresenter.initializeView();
        this.patchStackPresenter.initializeView();
        this.timeboardPresenter.initializeView();
    }

    private void addEventHandlers() {
        this.view.getBtnPass().setOnAction(e -> {
            if (!this.game.getLeatherPatchQueue().isEmpty()) {
                this.view.showWarningBanner("Place your leather patch first before passing.");
                return;
            }
            this.game.pass();
            this.resetSelection();
            this.handleCpuTurn();
            this.initializeView();
            this.checkGameEnd();
        });

        this.view.getBtnRotate().setOnAction(e -> {
            if (this.selectedPatchId == -1) {
                this.view.showWarningBanner("Select a patch first to rotate it.");
                return;
            }
            // Cycle the rotation state
            this.selectedRotation = this.selectedRotation.next();
            this.initializeView(); // Re-render previews with new rotation
        });

        this.view.getBtnQuit().setOnAction(e ->
                this.view.showConfirmationOverlay(
                        "Are you sure you want to quit to the main menu?",
                        () -> this.view.getPane().getScene().setRoot(this.mainMenuView.getPane())
                )
        );
    }

    public void handleCpuTurn() {
        while (this.game.getCurrentPlayer() instanceof CpuPlayer cpu) {
            if (!this.game.getLeatherPatchQueue().isEmpty()) {
                this.placeCpuLeatherPatches();
            }
            if (this.game.getStatus() == GameStatus.FINISHED) break;
            cpu.decideTurn(this.game);
            if (this.game.getStatus() == GameStatus.FINISHED) break;
        }
        this.initializeView();
    }

    private void placeCpuLeatherPatches() {
        Random random = new Random();
        while (!this.game.getLeatherPatchQueue().isEmpty()) {
            boolean placed = false;
            for (int attempt = 0; attempt < 100 && !placed; attempt++) {
                int row = random.nextInt(9);
                int col = random.nextInt(9);
                placed = this.game.placeLeatherPatch(row, col);
            }
            if (!placed) break;
        }
    }

    public void checkGameEnd() {
        if (this.game.getStatus() == GameStatus.FINISHED) {
            ResultsScreenView resultsScreenView = new ResultsScreenView();
            new ResultsScreenPresenter(this.game, resultsScreenView, this.mainMenuView, this.view.getPane());
            this.view.showResultsScreen(resultsScreenView);
        }
    }

    public void resetSelection() {
        this.selectedPatchId = -1;
        this.selectedRotation = PatchRotation.NOROTATION;
    }

    public String tokenColorToHex(TokenColor color) {
        if (color == null) return "#aaaaaa";
        return switch (color) {
            case RED -> "#ef5350";
            case GREEN -> "#66bb6a";
            case YELLOW -> "#ffee58";
            case BLUE -> "#42a5f5";
        };
    }

    // Getters and Setters
    public int getSelectedPatchId() { return this.selectedPatchId; }
    public void setSelectedPatchId(int selectedPatchId) { this.selectedPatchId = selectedPatchId; }
    public PatchRotation getSelectedRotation() { return this.selectedRotation; }
    public void setSelectedRotation(PatchRotation selectedRotation) { this.selectedRotation = selectedRotation; }
    public void showWarningBanner(String message) { this.view.showWarningBanner(message); }
}