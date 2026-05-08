package be.kdg.programming.integrationproject.view.resultsScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class ResultsScreenView {
    private StackPane root;
    private Label lblWinner;
    private Label lblScoreP1;
    private Label lblScoreP2;
    private Label lblSpecialTile;
    private Button btnMainMenu;

    public ResultsScreenView() {
        initialiseNodes();
        layoutNodes();
    }

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

    //updates all labels with the final game results
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

    public StackPane getPane() {
        return root;
    }

    public Button getBtnMainMenu() {
        return btnMainMenu;
    }
}