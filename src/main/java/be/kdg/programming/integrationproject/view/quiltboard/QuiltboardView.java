package be.kdg.programming.integrationproject.view.quiltboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class QuiltboardView {
    private static final int GRID_SIZE = 9;
    //smaller cell size so the board fits on 1920x1080
    private static final int CELL_SIZE = 35;

    private VBox root;
    private Label lblPlayerName;
    private Label lblButtons;
    private Label lblButtonIncome;
    private Button[][] cells;

    public QuiltboardView(String playerName, String borderColor) {
        this.cells = new Button[GRID_SIZE][GRID_SIZE];
        initialiseNodes(playerName, borderColor);
        layoutNodes(borderColor);
    }

    private void initialiseNodes(String playerName, String borderColor) {
        root = new VBox(5);

        lblPlayerName = new Label(playerName);
        lblPlayerName.setStyle("-fx-font-weight: bold; -fx-font-size: 13;");

        lblButtons = new Label("Buttons: 0");
        lblButtonIncome = new Label("Income: 0");
    }

    private void layoutNodes(String borderColor) {
        //player info bar above the grid
        HBox infoBar = new HBox(15, lblPlayerName, lblButtons, lblButtonIncome);
        infoBar.setAlignment(Pos.CENTER_LEFT);
        infoBar.setPadding(new Insets(5));
        infoBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: " + borderColor + "; -fx-border-width: 0 0 1 0;");

        //9x9 grid of buttons representing the quiltboard
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(5));
        grid.setStyle("-fx-background-color: white;");

        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                Button cell = new Button();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                cell.setMinSize(CELL_SIZE, CELL_SIZE);
                cell.setMaxSize(CELL_SIZE, CELL_SIZE);
                cell.setStyle("-fx-background-color: beige; -fx-border-color: #cccccc;");
                cells[r][c] = cell;
                grid.add(cell, c, r);
            }
        }

        //wrap everything in a bordered box with the player's color as accent and a white background
        VBox wrapper = new VBox(0, infoBar, grid);
        wrapper.setStyle(
                "-fx-border-color: " + borderColor + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-color: white;" +
                        "-fx-background-radius: 6;"
        );

        root.getChildren().add(wrapper);
    }

    //updates the player info labels, called by the presenter on every render
    public void update(String playerName, int buttons, int buttonIncome) {
        lblPlayerName.setText(playerName);
        lblButtons.setText("Buttons: " + buttons);
        lblButtonIncome.setText("Income: " + buttonIncome);
    }

    //marks a cell as occupied or empty, called by the presenter on every render
    public void setCell(int row, int col, boolean occupied, String occupiedColor) {
        cells[row][col].setStyle(
                occupied
                        ? "-fx-background-color: " + occupiedColor + "; -fx-border-color: #cccccc;"
                        : "-fx-background-color: beige; -fx-border-color: #cccccc;"
        );
    }

    public VBox getPane() {
        return root;
    }

    Button[][] getCells() {
        return cells;
    }
}