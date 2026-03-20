package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LeaderboardView {

    private VBox root;
    private HBox stats;

    private Label lblTitle;
    private Label lblTotalGames;
    private Label lblAvgDuration;
    private Label lblTopScore;
    private Label lblActivePlayers;

    private TableView<String[]> table;

    private Button btnBack;

    public LeaderboardView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.root = new VBox(15);
        this.stats = new HBox(20);

        this.lblTitle = new Label("Leaderboard");
        this.lblTotalGames = new Label("Total Games: -");
        this.lblAvgDuration = new Label("Avg Duration: -");
        this.lblTopScore = new Label("Top Score: -");
        this.lblActivePlayers = new Label("Active Players: -");

        this.table = new TableView<>();

        String[] headers = {
                "Rank", "Player", "Score", "Win %", "Wins", "Avg Turns", "Playtime"
        };

        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<String[], String> col = new TableColumn<>(headers[i]);

            col.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(
                            data.getValue()[colIndex]
                    )
            );

            this.table.getColumns().add(col);
        }

        this.btnBack = new Button("Back");
    }

    private void layoutNodes() {
        // Configure Container Padding
        this.root.setPadding(new Insets(20));

        // Arrange Stats Row
        this.stats.getChildren().addAll(
                lblTotalGames,
                lblAvgDuration,
                lblTopScore,
                lblActivePlayers
        );

        // Populate Main Root
        this.root.getChildren().addAll(
                lblTitle,
                stats,
                table,
                btnBack
        );
    }

    public VBox getPane() {
        return root;
    }

    public TableView<String[]> getTable() {
        return table;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public void setTotalGames(String v) {
        lblTotalGames.setText(v);
    }

    public void setAvgDuration(String v) {
        lblAvgDuration.setText(v);
    }

    public void setTopScore(String v) {
        lblTopScore.setText(v);
    }

    public void setActivePlayers(String v) {
        lblActivePlayers.setText(v);
    }
}