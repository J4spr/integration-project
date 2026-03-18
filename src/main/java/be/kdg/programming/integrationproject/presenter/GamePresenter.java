package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.List;

public class GamePresenter {

    private final Game game;
    private final GameView view;
    private final MainMenuView mainMenuView;

    private int selectedPatchId = -1;
    private PatchRotation selectedRotation = PatchRotation.NOROTATION;

    public GamePresenter(Game game, GameView view, MainMenuView mainMenuView) {

        this.game = game;
        this.view = view;
        this.mainMenuView = mainMenuView;

        renderAll();
        addBoardHandlers();
        addEventHandlers();
    }

    private void renderAll() {
        renderBoards();
        renderPatchStore();
        renderInfo();
        renderTimeTrack();
    }

    private void renderBoards() {

        boolean[][] gridP1 =
                game.getPlayer1().getQuiltBoard().getGrid();

        boolean[][] gridP2 =
                game.getPlayer2().getQuiltBoard().getGrid();

        Button[][] cellsP1 = view.getCellsP1();
        Button[][] cellsP2 = view.getCellsP2();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                cellsP1[r][c].setStyle(
                        gridP1[r][c] ?
                                "-fx-background-color: lightblue;" :
                                "-fx-background-color: beige;"
                );

                cellsP2[r][c].setStyle(
                        gridP2[r][c] ?
                                "-fx-background-color: lightgreen;" :
                                "-fx-background-color: beige;"
                );
            }
        }
    }

    private void renderPatchStore() {

        view.getPatchStore().getChildren().clear();

        List<Patch> patches =
                game.getPatchStack().getAvailablePatches();

        for (Patch patch : patches) {

            GridPane visual = new GridPane();

            boolean[][] shape = patch.getRotatedShape();

            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {

                    if (shape[r][c]) {
                        Button part = new Button();
                        part.setPrefSize(18,18);
                        part.setMouseTransparent(true);
                        visual.add(part,c,r);
                    }
                }
            }

            visual.setOnMouseClicked(e ->
                    selectedPatchId = patch.getPatchID()
            );

            view.getPatchStore().getChildren().add(visual);
        }
    }

    private void renderInfo() {

        Player current = game.getCurrentPlayer();

        view.setTurnText("Turn: Player " + current.getPlayerId());

        view.setButtonsP1(game.getPlayer1().getTotalButtons());
        view.setButtonsP2(game.getPlayer2().getTotalButtons());

        view.setLeather(game.getLeatherPatchQueue().size());
    }

    private void renderTimeTrack() {

        HBox track = view.getTimeTrack();

        int p1 = game.getPlayer1().getPosition();
        int p2 = game.getPlayer2().getPosition();

        for (int i = 0; i < track.getChildren().size(); i++) {

            Label cell = (Label) track.getChildren().get(i);

            cell.setText("");

            if (i == p1 && i == p2) {
                cell.setText("B");
            } else if (i == p1) {
                cell.setText("1");
            } else if (i == p2) {
                cell.setText("2");
            }
        }
    }

    private void addBoardHandlers() {

        Button[][] cellsP1 = view.getCellsP1();
        Button[][] cellsP2 = view.getCellsP2();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                int row = r;
                int col = c;

                cellsP1[r][c].setOnAction(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer1())
                        handleBoardClick(row,col);
                });

                cellsP2[r][c].setOnAction(e -> {
                    if (game.getCurrentPlayer() == game.getPlayer2())
                        handleBoardClick(row,col);
                });
            }
        }
    }

    private void handleBoardClick(int row, int col) {

        if (selectedPatchId == -1) return;

        boolean success =
                game.buyAndPlacePatch(
                        selectedPatchId,
                        row,
                        col,
                        selectedRotation
                );

        if (!success) {
            showError();
            return;
        }

        selectedPatchId = -1;

        cpuTurnIfNeeded();

        renderAll();

        checkGameEnd();
    }

    private void cpuTurnIfNeeded() {

        if (game.getCurrentPlayer() instanceof CpuPlayer cpu) {

            cpu.decideTurn(game);

            renderAll();

            checkGameEnd();
        }
    }

    private void checkGameEnd() {

        if (game.getStatus() == be.kdg.programming.integrationproject.model.Enums.GameStatus.FINISHED) {

            Stage popup = new Stage();

            VBox box = new VBox(10);

            Label msg = new Label(
                    "Winner: Player " +
                            game.getWinner().getPlayerId()
            );

            box.getChildren().add(msg);

            popup.setScene(new Scene(box,200,100));
            popup.show();
        }
    }

    private void showError() {

        Stage popup = new Stage();

        VBox box = new VBox(10);
        Label msg = new Label("Invalid move");

        box.getChildren().add(msg);

        popup.setScene(new Scene(box,150,80));
        popup.show();

        PauseTransition delay =
                new PauseTransition(Duration.seconds(2));

        delay.setOnFinished(e -> popup.close());
        delay.play();
    }

    private void addEventHandlers() {

        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene()
                        .setRoot(mainMenuView.getPane())
        );

        view.getBtnPass().setOnAction(e -> {
            game.pass();
            cpuTurnIfNeeded();
            renderAll();
            checkGameEnd();
        });

        view.getBtnRotate().setOnAction(e -> {

            switch (selectedRotation) {
                case NOROTATION -> selectedRotation = PatchRotation.NINETY;
                case NINETY -> selectedRotation = PatchRotation.ONEEIGHTY;
                case ONEEIGHTY -> selectedRotation = PatchRotation.TWOSEVENTY;
                case TWOSEVENTY -> selectedRotation = PatchRotation.NOROTATION;
            }
        });
    }
}