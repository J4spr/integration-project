package be.kdg.programming.integrationproject.view.quiltboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Custom layout class representing a player's quiltboard.
 * Assembles a text information bar above a 9x9 matrix grid of buttons.
 *
 * @author Team 4
 * @version 1.0
 */
public class QuiltboardView {
    private static final int GRID_SIZE = 9;
    private static final int CELL_SIZE = 35;

    private VBox root;
    private Label lblPlayerName;
    private Label lblButtons;
    private Label lblButtonIncome;
    private Button[][] cells;

    /**
     * Constructs an independent layout panel box and formats node margins.
     *
     * @param playerName  display name handle text label
     * @param borderColor styling hex color string matching the player's token color
     */
    public QuiltboardView(String playerName, String borderColor) {
        this.cells = new Button[GRID_SIZE][GRID_SIZE];
        initialiseNodes(playerName, borderColor);
        layoutNodes(borderColor);
    }

    /**
     * Instantiates metric nodes and titles.
     *
     * @param playerName  display title string
     * @param borderColor tracking style accent hex color code
     */
    private void initialiseNodes(String playerName, String borderColor) {
        root = new VBox(5);
        lblPlayerName = new Label(playerName);
        lblPlayerName.setStyle("-fx-font-weight: bold; -fx-font-size: 13;");
        lblButtons = new Label("Buttons: 0");
        lblButtonIncome = new Label("Income: 0");
    }

    /**
     * Structures layout containers. Organises cell elements within a centralized
     * {@link GridPane} wrapped inside bordered framing blocks.
     *
     * @param borderColor layout frame bounding accent string line
     */
    private void layoutNodes(String borderColor) {
        HBox infoBar = new HBox(15, lblPlayerName, lblButtons, lblButtonIncome);
        infoBar.setAlignment(Pos.CENTER_LEFT);
        infoBar.setPadding(new Insets(5));
        infoBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: " + borderColor + "; -fx-border-width: 0 0 1 0;");

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

    /**
     * Updates text fields detailing active currency profiles.
     *
     * @param playerName   user description string text name handle value
     * @param buttons      current financial currency token balance value
     * @param buttonIncome recurrent asset revenue accumulation factor value
     */
    public void update(String playerName, int buttons, int buttonIncome) {
        lblPlayerName.setText(playerName);
        lblButtons.setText("Buttons: " + buttons);
        lblButtonIncome.setText("Income: " + buttonIncome);
    }

    /**
     * Updates a cell's color to reflect its current occupation state.
     *
     * @param row           horizontal row coordinate element sequence target parameter index
     * @param col           vertical column coordinate element sequence target parameter index
     * @param occupied      boolean flag indicating if the cell contains a patch component segment
     * @param occupiedColor target styling hex color to apply when occupied
     */
    public void setCell(int row, int col, boolean occupied, String occupiedColor) {
        cells[row][col].setStyle(
                occupied
                        ? "-fx-background-color: " + occupiedColor + "; -fx-border-color: #cccccc;"
                        : "-fx-background-color: beige; -fx-border-color: #cccccc;"
        );
    }

    public VBox getPane() { return root; }
    Button[][] getCells() { return cells; }
}