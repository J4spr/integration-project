package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView {

    private VBox root;
    private GridPane boardGrid;
    private HBox patchStore;

    private Label lblTurn;

    private Button btnBack;

    private Button[][] cells;

    public GameView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {

        root = new VBox(20);
        root.setPadding(new Insets(20));

        lblTurn = new Label("Turn: Player 1");

        boardGrid = new GridPane();
        boardGrid.setHgap(3);
        boardGrid.setVgap(3);

        cells = new Button[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                Button cell = new Button();
                cell.setPrefSize(40, 40);
                cell.setStyle("-fx-background-color: beige;");

                cells[r][c] = cell;
                boardGrid.add(cell, c, r);
            }
        }

        patchStore = new HBox(20);

        btnBack = new Button("Back");
    }

    private void layoutNodes() {

        root.getChildren().addAll(
                lblTurn,
                boardGrid,
                new Label("Patch Store"),
                patchStore,
                btnBack
        );
    }

    public VBox getPane() {
        return root;
    }

    public Button[][] getCells() {
        return cells;
    }

    public HBox getPatchStore() {
        return patchStore;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public void setTurnText(String text) {
        lblTurn.setText(text);
    }

    public void colorCell(int r, int c, int player) {

        if (player == 1) {
            cells[r][c].setStyle("-fx-background-color: lightblue;");
        } else {
            cells[r][c].setStyle("-fx-background-color: lightgreen;");
        }
    }
}