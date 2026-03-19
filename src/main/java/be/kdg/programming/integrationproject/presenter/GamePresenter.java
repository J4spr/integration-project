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

    //sub-presenters
    private final QuiltboardPresenter quiltboardPresenter;
    private final PatchStackPresenter patchStackPresenter;
    private final TimeboardPresenter timeboardPresenter;

    //shared selection state used by sub-presenters
    private int selectedPatchId = -1;
    private PatchRotation selectedRotation = PatchRotation.NOROTATION;

    public GamePresenter(Game game, GameView view, MainMenuView mainMenuView) {
        this.game = game;
        this.view = view;
        this.mainMenuView = mainMenuView;

        //sub-presenters are created after this object exists so they can hold a back-reference
        this.quiltboardPresenter = new QuiltboardPresenter(game, view, this);
        this.patchStackPresenter = new PatchStackPresenter(game, view, this);
        this.timeboardPresenter = new TimeboardPresenter(game, view, this);

        //if the CPU is the starting player, let it take its turn immediately before the human can interact
        handleCpuTurn();
        initializeView();
        addEventHandlers();
    }

    //initializes all sub-views, called once at startup and after every move
    public void initializeView() {
        quiltboardPresenter.initializeView();
        patchStackPresenter.initializeView();
        timeboardPresenter.initializeView();
    }

    private void addEventHandlers() {
        view.getBtnPass().setOnAction(e -> {
            //block passing if there are leather patches waiting to be placed
            if (!game.getLeatherPatchQueue().isEmpty()) {
                view.showWarningBanner("Place your leather patch first before passing.");
                return;
            }
            game.pass();
            resetSelection();
            handleCpuTurn();
            initializeView();
            checkGameEnd();
        });

        view.getBtnQuit().setOnAction(e ->
                //show an inline confirmation overlay instead of an external dialog
                view.showConfirmationOverlay(
                        "Are you sure you want to quit to the main menu?",
                        () -> view.getPane().getScene().setRoot(mainMenuView.getPane())
                )
        );
    }

    //lets the CPU take its turn if the current player is a CpuPlayer
    //keeps looping in case the CPU triggers leather patches that also need to be handled
    public void handleCpuTurn() {
        while (game.getCurrentPlayer() instanceof CpuPlayer cpu) {
            if (!game.getLeatherPatchQueue().isEmpty()) {
                placeCpuLeatherPatches();
            }
            if (game.getStatus() == GameStatus.FINISHED) break;
            cpu.decideTurn(game);
            if (game.getStatus() == GameStatus.FINISHED) break;
        }
        initializeView();
    }

    //places all queued leather patches for the CPU at random valid positions
    private void placeCpuLeatherPatches() {
        Random random = new Random();
        while (!game.getLeatherPatchQueue().isEmpty()) {
            boolean placed = false;
            //try up to 100 random positions to find a valid one
            for (int attempt = 0; attempt < 100 && !placed; attempt++) {
                int row = random.nextInt(9);
                int col = random.nextInt(9);
                placed = game.placeLeatherPatch(row, col);
            }
            //break if no valid position found to avoid an infinite loop
            if (!placed) break;
        }
    }

    public void checkGameEnd() {
        if (game.getStatus() == GameStatus.FINISHED) {
            //show the results screen as an overlay instead of an external popup
            ResultsScreenView resultsScreenView = new ResultsScreenView();
            new ResultsScreenPresenter(game, resultsScreenView, mainMenuView, view.getPane());
            view.showResultsScreen(resultsScreenView);
        }
    }

    //resets the patch selection and rotation after a move or pass
    public void resetSelection() {
        selectedPatchId = -1;
        selectedRotation = PatchRotation.NOROTATION;
    }

    //converts a TokenColor enum to a hex color string usable in JavaFX CSS
    public String tokenColorToHex(TokenColor color) {
        if (color == null) return "#aaaaaa";
        return switch (color) {
            case RED -> "#ef5350";
            case GREEN -> "#66bb6a";
            case YELLOW -> "#ffee58";
            case BLUE -> "#42a5f5";
        };
    }

    //shared state getters and setters used by sub-presenters
    public int getSelectedPatchId() {
        return selectedPatchId;
    }

    public void setSelectedPatchId(int selectedPatchId) {
        this.selectedPatchId = selectedPatchId;
    }

    public PatchRotation getSelectedRotation() {
        return selectedRotation;
    }

    public void setSelectedRotation(PatchRotation selectedRotation) {
        this.selectedRotation = selectedRotation;
    }

    //shows a warning banner inside the game screen instead of an external popup
    public void showWarningBanner(String message) {
        view.showWarningBanner(message);
    }
}