package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.dao.PlayerDao;
import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.PlayerStats;
import be.kdg.programming.integrationproject.view.LeaderBoardView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import javafx.collections.FXCollections;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class LeaderBoardPresenter {
    private final LeaderBoardView view;
    private final PlayerDao playerDao;
    private final MainMenuView mmv;

    public LeaderBoardPresenter(LeaderBoardView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mmv = mainMenuView;
        this.playerDao = new PlayerDao(new DbConnection());
        refreshLeaderboard();
        addHandlers();
    }

    public void refreshLeaderboard() {
        try {
            List<PlayerStats> stats = playerDao.getDetailedLeaderboard();
            view.setStatsData(stats);
        } catch (SQLException e) {
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void addHandlers() {
        // Terug naar hoofdmenu
        if (view.getBtnBack() != null) {
            view.getBtnBack().setOnAction(event -> {
                if (view.getBtnBack().getScene() != null) {
                    view.getBtnBack().getScene().setRoot(mmv.getPane());
                    mmv.getPane().requestLayout();
                }
            });
        }

        // Sorteren op wins (hoog naar laag)
        view.getBtnSortWins().setOnAction(e -> {
            List<PlayerStats> sorted = view.getStatsData().stream()
                    .sorted(Comparator.comparingInt(PlayerStats::getWins).reversed())
                    .toList();
            view.setStatsData(sorted);
            highlightButton(view.getBtnSortWins());
        });

        // Sorteren op games gespeeld (hoog naar laag)
        view.getBtnSortGames().setOnAction(e -> {
            List<PlayerStats> sorted = view.getStatsData().stream()
                    .sorted(Comparator.comparingInt(PlayerStats::getGamesPlayed).reversed())
                    .toList();
            view.setStatsData(sorted);
            highlightButton(view.getBtnSortGames());
        });

        // Sorteren op win% (hoog naar laag)
        view.getBtnSortWinPct().setOnAction(e -> {
            List<PlayerStats> sorted = view.getStatsData().stream()
                    .sorted(Comparator.comparingDouble(PlayerStats::getWinPercentage).reversed())
                    .toList();
            view.setStatsData(sorted);
            highlightButton(view.getBtnSortWinPct());
        });

        // Sorteren op spent (hoog naar laag)
        view.getBtnSortSpent().setOnAction(e -> {
            List<PlayerStats> sorted = view.getStatsData().stream()
                    .sorted(Comparator.comparingInt(PlayerStats::getTotalButtonsSpent).reversed())
                    .toList();
            view.setStatsData(sorted);
            highlightButton(view.getBtnSortSpent());
        });
    }

    // Markeer de actieve sorteerknop oranje, reset de rest
    private void highlightButton(javafx.scene.control.Button active) {
        String defaultStyle = "-fx-background-color: #e0e0e0; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4;";
        String activeStyle  = "-fx-background-color: #f57c00; -fx-text-fill: white; -fx-border-color: #e65100; -fx-border-radius: 4; -fx-background-radius: 4;";

        view.getBtnSortWins().setStyle(defaultStyle);
        view.getBtnSortGames().setStyle(defaultStyle);
        view.getBtnSortWinPct().setStyle(defaultStyle);
        view.getBtnSortSpent().setStyle(defaultStyle);

        active.setStyle(activeStyle);
    }
}