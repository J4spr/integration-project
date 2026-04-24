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
            if (!this.game.getCurrentLeatherPatchQueue().isEmpty()) {
                this.view.showWarningBanner("Place your leather patch first before passing.");
                return;
            }

            Player currentPlayer = this.game.getCurrentPlayer();
            int oldPosition = currentPlayer.getPosition();

            this.game.pass();

            int newPosition = currentPlayer.getPosition();
            int spacesMoved = newPosition - oldPosition;

            try {
                be.kdg.programming.integrationproject.dao.MoveDao moveDao =
                        new be.kdg.programming.integrationproject.dao.MoveDao(new be.kdg.programming.integrationproject.model.DbConnection());

                Move passMove = new Move(
                        0,
                        0,
                        -1, // PatchID (-1 omdat we geen patch plaatsen)
                        new java.sql.Time(System.currentTimeMillis()),
                        new java.sql.Time(System.currentTimeMillis()),
                        0,  // Special patches
                        spacesMoved, // Hoeveel vakjes we net vooruit zijn gesprongen
                        newPosition, // De nieuwe positie
                        0,  // Geen rotatie bij een pass
                        this.game.getPlayer1().getTotalButtons(),
                        this.game.getPlayer2().getTotalButtons()
                );

                moveDao.insert(passMove);

            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Let op: Kon de pass-move niet opslaan in de database.");
            }


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

    private void placeCpuLeatherPatches() {
        Random random = new Random();
        Player cpu = this.game.getCurrentPlayer();
        //use the cpu's own queue so human leather patches are never touched
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

    //checks if the leather patch queue is non-empty after a move and shows the appropriate notification
    //handles the case where multiple leather patches are collected in a single move (e.g. passing multiple positions)
    public void notifyLeatherPatchIfNeeded() {
        if (!this.game.getCurrentLeatherPatchQueue().isEmpty()) {
            int count = this.game.getCurrentLeatherPatchQueue().size();
            String msg = count == 1
                    ? "You collected a leather patch! Place it on your quiltboard."
                    : "You collected " + count + " leather patches! Place them on your quiltboard.";
            this.view.showInfoBanner(msg);
        }
    }

    public void checkGameEnd() {
        if (this.game.getStatus() == GameStatus.FINISHED) {

            try {
                be.kdg.programming.integrationproject.dao.GameDao gameDao =
                        new be.kdg.programming.integrationproject.dao.GameDao(new be.kdg.programming.integrationproject.model.DbConnection());

                gameDao.update(this.game);
                System.out.println("Spel is afgelopen en resultaten zijn succesvol opgeslagen in de database!");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Let op: Kon de eindresultaten niet opslaan.");
            }


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
    public void showInfoBanner(String message) { this.view.showInfoBanner(message); }
}