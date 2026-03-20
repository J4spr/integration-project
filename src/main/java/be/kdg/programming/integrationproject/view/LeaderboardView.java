package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LeaderboardView {

    private VBox root;

    private Label lblTotalGames;
    private Label lblAvgDuration;
    private Label lblTopScore;
    private Label lblActivePlayers;

    private TableView<String[]> table;

    private Button btnBack;

    public LeaderboardView() {

        root = new VBox(15);
        root.setPadding(new Insets(20));

        // Top cards
        HBox stats = new HBox(20);

        lblTotalGames = new Label("Total Games: -");
        lblAvgDuration = new Label("Avg Duration: -");
        lblTopScore = new Label("Top Score: -");
        lblActivePlayers = new Label("Active Players: -");

        stats.getChildren().addAll(
                lblTotalGames,
                lblAvgDuration,
                lblTopScore,
                lblActivePlayers
        );

        table = new TableView<>();

        String[] headers = {
                "Rank","Player","Score","Win %","Wins","Avg Turns","Playtime"
        };

        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<String[], String> col =
                    new TableColumn<>(headers[i]);

            col.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(
                            data.getValue()[colIndex]
                    )
            );

            table.getColumns().add(col);
        }

        btnBack = new Button("Back");

        root.getChildren().addAll(
                new Label("Leaderboard"),
                stats,
                table,
                btnBack
        );
    }

    public VBox getPane(){ return root; }
    public TableView<String[]> getTable(){ return table; }
    public Button getBtnBack(){ return btnBack; }

    public void setTotalGames(String v){ lblTotalGames.setText(v); }
    public void setAvgDuration(String v){ lblAvgDuration.setText(v); }
    public void setTopScore(String v){ lblTopScore.setText(v); }
    public void setActivePlayers(String v){ lblActivePlayers.setText(v); }
}