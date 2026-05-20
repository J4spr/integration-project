package be.kdg.programming.integrationproject.view.resultsScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Represents the visual interface for the final game results screen.
 * <p>
 * This view displays the winner, individual player scores, and special achievements
 * (such as special tile bonuses) inside an overlay card layout.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class ResultsScreenView {
    /** The root container wrapper using a semi-transparent background overlay. */
    private StackPane root;
    /** Label to display the name of the winning player. */
    private Label lblWinner;
    /** Label displaying the total score for Player 1. */
    private Label lblScoreP1;
    /** Label displaying the total score for Player 2. */
    private Label lblScoreP2;
    /** Label indicating who earned the special tile bonus, if applicable. */
    private Label lblSpecialTile;
    /** Button used to return to the application's main menu. */
    private Button btnMainMenu;

    /**
     * Constructs a new {@code ResultsScreenView}, initializing JavaFX nodes
     * and setting up their alignment and layouts.
     */
    public ResultsScreenView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Instantiates the UI components and applies base CSS styling to the labels and root.
     */
    private void initialiseNodes() {
        root = new StackPane();
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");

        lblWinner = new Label();
        lblWinner.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: black;");

        lblScoreP1 = new Label();
        lblScoreP1.setStyle("-fx-font-size: 15; -fx-text-fill: black;");

        lblScoreP2 = new Label();
        lblScoreP2.setStyle("-fx-font-size: 15; -fx-text-fill: black;");

        lblSpecialTile = new Label();
        lblSpecialTile.setStyle("-fx-font-size: 12; -fx-text-fill: black;");

        btnMainMenu = new Button("Back to Main Menu");
        btnMainMenu.setPrefWidth(200);
        btnMainMenu.setPrefHeight(35);
        btnMainMenu.setMinWidth(200);
        btnMainMenu.setMaxWidth(200);
        btnMainMenu.setMinHeight(35);
        btnMainMenu.setMaxHeight(35);
    }

    /**
     * Organizes the initialized nodes inside a centered content box container
     * with fixed dimensional bounds to avoid clipping.
     */
    private void layoutNodes() {
        VBox contentBox = new VBox(15,
                lblWinner,
                lblScoreP1,
                lblScoreP2,
                lblSpecialTile,
                btnMainMenu
        );
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        //fixed size so the box never touches the screen edges
        contentBox.setMinWidth(450);
        contentBox.setMaxWidth(450);
        contentBox.setMinHeight(230);
        contentBox.setMaxHeight(230);
        contentBox.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 12;" +
                        "-fx-background-radius: 12;"
        );
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(contentBox);
    }

    /**
     * Updates the text properties of all result metrics with the calculated final game states.
     *
     * @param winnerName       the name of the victorious player
     * @param scoreP1          the numeric point total of player 1
     * @param nameP1           the display name of player 1
     * @param scoreP2          the numeric point total of player 2
     * @param nameP2           the display name of player 2
     * @param specialTileOwner the name of the player who claimed the special tile bonus,
     * or {@code null} if unclaimed
     */
    public void setResults(String winnerName, int scoreP1, String nameP1, int scoreP2, String nameP2, String specialTileOwner) {
        lblWinner.setText(winnerName + " wins!");
        lblScoreP1.setText(nameP1 + ": " + scoreP1 + " points");
        lblScoreP2.setText(nameP2 + ": " + scoreP2 + " points");
        if (specialTileOwner != null) {
            lblSpecialTile.setText("Special tile bonus: " + specialTileOwner);
        } else {
            lblSpecialTile.setText("");
        }
    }

    /**
     * Returns the root container panel of the results screen view.
     *
     * @return the {@code StackPane} serving as the root container
     */
    public StackPane getPane() {
        return root;
    }

    /**
     * Returns the main menu navigation button.
     *
     * @return the {@code Button} instance for shifting to the main menu
     */
    Button getBtnMainMenu() {
        return btnMainMenu;
    }
}