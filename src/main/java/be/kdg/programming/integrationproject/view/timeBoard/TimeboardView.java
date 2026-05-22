package be.kdg.programming.integrationproject.view.timeBoard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * View class that draws the spiraling timeline track components for Patchwork board spaces.
 * <p>
 * Generates an indexed grid tracking custom milestone triggers, including specific
 * coordinate pairs for button income intervals and leather patch reward spaces.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class TimeboardView {

    /** The maximum limit value of grid components drawn onto the board layout canvas. */
    private static final int TOTAL_CELLS = 53;
    /** Coordinate arrays mapping intervals where players trigger economic updates. */
    private static final int[] BUTTON_POSITIONS = {5, 11, 17, 23, 29, 35, 41, 47};
    /** Coordinate arrays mapping milestones where players receive leather patch tile pieces. */
    private static final int[] LEATHER_POSITIONS = {26, 32, 38, 44, 50};

    /**
     * Pre-calculated spiral index offsets mapping cells onto a 2D grid matrix.
     * Each sub-element forms a strict pair corresponding to: {@code [column, row]}.
     */
    private static final int[][] SPIRAL_COORDS = {
            {0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}, {8, 0},
            {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6},
            {7, 6}, {6, 6}, {5, 6}, {4, 6}, {3, 6}, {2, 6}, {1, 6}, {0, 6},
            {0, 5}, {0, 4}, {0, 3}, {0, 2}, {0, 1},
            {1, 1}, {2, 1}, {3, 1}, {4, 1}, {5, 1}, {6, 1}, {7, 1},
            {7, 2}, {7, 3}, {7, 4}, {7, 5},
            {6, 5}, {5, 5}, {4, 5}, {3, 5}, {2, 5}, {1, 5},
            {1, 4}, {1, 3}, {1, 2},
            {2, 2}, {3, 2}, {4, 2}, {5, 2}, {6, 2},
    };

    /** Visual root wrapper vertically indexing title parameters alongside grid components. */
    private VBox root;
    /** Node arrays referencing individual layout cells. */
    private Label[] cells;

    /**
     * Initializes a new instance of {@code TimeboardView}, preparing layout tracks.
     */
    public TimeboardView() {
        cells = new Label[TOTAL_CELLS];
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Instantiates structural wrapper variables.
     */
    private void initialiseNodes() {
        root = new VBox(5);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Generates a title bar header panel and maps individual coordinate locations
     * onto an active grid.
     */
    private void layoutNodes() {
        //title label centered above the grid
        Label lblTitle = new Label("Timeboard");
        lblTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        //title bar similar to the quiltboard info bar
        HBox titleBar = new HBox(lblTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setPadding(new Insets(5));
        titleBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #aaaaaa; -fx-border-width: 0 0 1 0;");

        GridPane grid = new GridPane();
        grid.setHgap(3);
        grid.setVgap(3);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setStyle("-fx-background-color: white;");

        for (int i = 0; i < TOTAL_CELLS; i++) {
            Label cell = new Label(i == 0 ? "S" : String.valueOf(i));
            cell.setPrefSize(38, 38);
            cell.setAlignment(Pos.CENTER);
            cell.setStyle(getDefaultStyle(i));
            cells[i] = cell;
            //place each cell at its hardcoded spiral coordinate
            grid.add(cell, SPIRAL_COORDS[i][0], SPIRAL_COORDS[i][1]);
        }

        //wrap title and grid in a white bordered box matching the quiltboard style
        VBox wrapper = new VBox(0, titleBar, grid);
        wrapper.setStyle(
                "-fx-border-color: #aaaaaa;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-color: white;" +
                        "-fx-background-radius: 6;"
        );

        root.getChildren().add(wrapper);
    }

    /**
     * Selects and returns the base CSS styling rule configuration string text for cells
     * based on their track properties.
     *
     * @param position the numeric cell index position on the track
     * @return a CSS style configuration text string mapping custom backgrounds
     * for buttons, leather milestones, or default tracks
     */
    private String getDefaultStyle(int position) {
        if (isButtonPosition(position)) {
            //yellow background for button income positions
            return "-fx-background-color: #fff176; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 10;";
        } else if (isLeatherPosition(position)) {
            //brown background for leather patch positions
            return "-fx-background-color: #bcaaa4; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 10;";
        } else {
            return "-fx-background-color: #e0e0e0; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 10;";
        }
    }

    /**
     * Wipes out custom player layout colors across track variables
     * and maps updated position markers.
     * <p>
     * Overlaps are automatically managed; cells containing both players output {@code "B"},
     * while single occupations show the respective player numbers.
     * </p>
     *
     * @param posP1   the numeric milestone index of player 1
     * @param posP2   the numeric milestone index of player 2
     * @param colorP1 the hex color string applied to player 1's position marker
     * @param colorP2 the hex color string applied to player 2's position marker
     */
    public void update(int posP1, int posP2, String colorP1, String colorP2) {
        for (int i = 0; i < TOTAL_CELLS; i++) {
            cells[i].setStyle(getDefaultStyle(i));
            cells[i].setText(i == 0 ? "S" : String.valueOf(i));
        }
        //if both players are on the same position, show "B" (both)
        if (posP1 == posP2) {
            cells[posP1].setStyle("-fx-background-color: #ce93d8; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 10;");
            cells[posP1].setText("B");
        } else {
            cells[posP1].setStyle("-fx-background-color: " + colorP1 + "; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 10;");
            cells[posP1].setText("1");
            cells[posP2].setStyle("-fx-background-color: " + colorP2 + "; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 10;");
            cells[posP2].setText("2");
        }
    }

    /**
     * Checks if a specified track position matches a button economic milestone.
     *
     * @param position the numeric track cell index
     * @return {@code true} if the position triggers a button income calculation,
     * otherwise {@code false}
     */
    private boolean isButtonPosition(int position) {
        for (int bp : BUTTON_POSITIONS) if (bp == position) return true;
        return false;
    }

    /**
     * Checks if a specified track position matches a leather patch award milestone.
     *
     * @param position the numeric track cell index
     * @return {@code true} if the position triggers a leather patch collection,
     * otherwise {@code false}
     */
    private boolean isLeatherPosition(int position) {
        for (int lp : LEATHER_POSITIONS) if (lp == position) return true;
        return false;
    }

    /**
     * Returns the visual panel holding the timeboard structure components.
     *
     * @return the parent vertical layout component {@code VBox} configuration reference
     */
    public VBox getPane() {
        return root;
    }
}