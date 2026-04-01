package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.dao.PlayerDao;
import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Move;
import be.kdg.programming.integrationproject.dao.MoveDao;
import be.kdg.programming.integrationproject.model.Player;
import be.kdg.programming.integrationproject.model.PlayerStats;
import be.kdg.programming.integrationproject.view.LeaderBoardView;
import be.kdg.programming.integrationproject.view.MainMenuView;

import java.sql.SQLException;
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
            // Correctly fetch the List of DTOs
            List<PlayerStats> stats = playerDao.getDetailedLeaderboard();
            view.setStatsData(stats);
        } catch (SQLException e) {
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void addHandlers() {
        if (view.getBtnBack() != null) {
            view.getBtnBack().setOnAction(event -> {
                if (view.getBtnBack().getScene() != null) {
                    view.getBtnBack().getScene().setRoot(mmv.getPane());
                    mmv.getPane().requestLayout();
                }
            });
        }
    }
}