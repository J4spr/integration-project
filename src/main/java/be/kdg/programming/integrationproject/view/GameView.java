package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView {
    private VBox root;

    private GridPane boardP1;
    private GridPane boardP2;

    private Button[][] cellsP1;
    private Button[][] cellsP2;

    private HBox patchStore;
    private HBox timeTrack;

    private Label lblTurn;
    private Label lblButtonsP1;
    private Label lblButtonsP2;
    private Label lblLeather;

    private Button btnPass;
    private Button btnRotate;
    private Button btnBack;


    public GameView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.root.setPadding(new Insets(15));

        this.root = new VBox(15);

        this.lblTurn = new Label("Turn");
        this.lblButtonsP1 = new Label("P1 Buttons: ");
        this.lblButtonsP2 = new Label("P2 Buttons: ");
        this.lblLeather = new Label("Leather patches: 0");

        this.btnPass = new Button("PASS");
        this.btnRotate = new Button("ROTATE");

        HBox infoBar = new HBox(20, this.lblTurn, this.lblButtonsP1, this.lblButtonsP2, this.lblLeather, this.btnPass, this.btnRotate);

        root.getChildren().addAll(infoBar, new Label("Player 2 Board"), boardP2, new Label("Patch Store"), patchStore, new Label("Time Track"), timeTrack, new Label("Player 1 Board"), boardP1, btnBack);
        this.boardP2 = new GridPane();
        this.boardP1 = new GridPane();

        this.cellsP1 = new Button[9][9];
        this.cellsP2 = new Button[9][9];

        this.patchStore = new HBox(20);
        this.timeTrack = new HBox(2);
        this.btnBack = new Button("Back");
    }

    private void layoutNodes(){
        createBoard(boardP1, cellsP1);
        createBoard(boardP2, cellsP2);

        this.patchStore.setPadding(new Insets(10));
        this.patchStore.setStyle("-fx-border-color:black;");

        this.timeTrack.setPadding(new Insets(10));

        for (int i = 0; i < 53; i++) {
            Label cell = new Label();
            cell.setPrefSize(20, 20);
            cell.setStyle("-fx-border-color:black;");
            timeTrack.getChildren().add(cell);
        }
    }

    private void createBoard(GridPane board, Button[][] cells) {

        board.setHgap(2);
        board.setVgap(2);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                Button cell = new Button();
                cell.setPrefSize(30, 30);
                cell.setStyle("-fx-background-color: beige;");

                cells[r][c] = cell;
                board.add(cell, c, r);
            }
        }
    }

    public VBox getPane() {
        return root;
    }

    public Button[][] getCellsP1() {
        return cellsP1;
    }

    public Button[][] getCellsP2() {
        return cellsP2;
    }

    public HBox getPatchStore() {
        return patchStore;
    }

    public HBox getTimeTrack() {
        return timeTrack;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public Button getBtnPass() {
        return btnPass;
    }

    public Button getBtnRotate() {
        return btnRotate;
    }

    public void setTurnText(String text) {
        lblTurn.setText(text);
    }

    public void setButtonsP1(int value) {
        lblButtonsP1.setText("P1 Buttons: " + value);
    }

    public void setButtonsP2(int value) {
        lblButtonsP2.setText("P2 Buttons: " + value);
    }

    public void setLeather(int value) {
        lblLeather.setText("Leather patches: " + value);
    }
}