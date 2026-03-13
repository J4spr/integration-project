package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GamePresenter {

    private final GameView view;
    private final MainMenuView mainMenuView;

    private int currentPlayer = 1;

    private int[][] selectedPatch = null;

    private int[][] boardState = new int[9][9];

    public GamePresenter(GameView view, MainMenuView mainMenuView) {

        this.view = view;
        this.mainMenuView = mainMenuView;

        createPatchStore();
        addBoardHandlers();
        addEventHandlers();
    }
    private void showPlacementError() {

        Stage popup = new Stage();

        VBox box = new VBox(10);
        box.setStyle("-fx-background-color: #ffdddd; -fx-padding: 15;");
        box.setPrefWidth(220);

        Label msg = new Label("Invalid patch placement!");
        Button close = new Button("X");

        close.setOnAction(e -> popup.close());

        box.getChildren().addAll(close, msg);

        Scene scene = new Scene(box);
        popup.setScene(scene);
        popup.setAlwaysOnTop(true);
        popup.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> popup.close());
        delay.play();
    }
    private void createPatchStore() {

        int[][] patch1 = {
                {1,1},
                {1,0}
        };

        int[][] patch2 = {
                {1,1,1}
        };

        int[][] patch3 = {
                {1},
                {1},
                {1}
        };

        addPatchButton(patch1);
        addPatchButton(patch2);
        addPatchButton(patch3);
    }

    private void addPatchButton(int[][] shape) {

        GridPane patchVisual = new GridPane();

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {

                if (shape[r][c] == 1) {

                    Button part = new Button();
                    part.setPrefSize(20, 20);
                    part.setStyle("-fx-background-color: orange;");
                    part.setMouseTransparent(true);

                    patchVisual.add(part, c, r);
                }
            }
        }

        patchVisual.setOnMouseClicked(e -> {
            selectedPatch = shape;
            System.out.println("Patch selected");
        });

        view.getPatchStore().getChildren().add(patchVisual);
    }

    private void addBoardHandlers() {

        Button[][] cells = view.getCells();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                int row = r;
                int col = c;

                cells[r][c].setOnAction(e -> tryPlacePatch(row, col));
            }
        }
    }

    private void tryPlacePatch(int startRow, int startCol) {

        if (selectedPatch == null) return;

        if (!isValidPlacement(startRow, startCol)) {
            showPlacementError();
            return;
        }

        placePatch(startRow, startCol);

        switchTurn();
    }

    private boolean isValidPlacement(int startRow, int startCol) {

        for (int r = 0; r < selectedPatch.length; r++) {
            for (int c = 0; c < selectedPatch[r].length; c++) {

                if (selectedPatch[r][c] == 1) {

                    int boardR = startRow + r;
                    int boardC = startCol + c;

                    if (boardR >= 9 || boardC >= 9) return false;

                    if (boardState[boardR][boardC] != 0) return false;
                }
            }
        }

        return true;
    }

    private void placePatch(int startRow, int startCol) {

        for (int r = 0; r < selectedPatch.length; r++) {
            for (int c = 0; c < selectedPatch[r].length; c++) {

                if (selectedPatch[r][c] == 1) {

                    int boardR = startRow + r;
                    int boardC = startCol + c;

                    boardState[boardR][boardC] = currentPlayer;

                    view.colorCell(boardR, boardC, currentPlayer);
                }
            }
        }
    }

    private void switchTurn() {

        currentPlayer = (currentPlayer == 1) ? 2 : 1;

        view.setTurnText("Turn: Player " + currentPlayer);

        selectedPatch = null;
    }

    private void addEventHandlers() {

        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );

    }
}