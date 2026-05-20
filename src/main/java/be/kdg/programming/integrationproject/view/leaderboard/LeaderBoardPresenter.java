package be.kdg.programming.integrationproject.view.leaderboard;

import be.kdg.programming.integrationproject.dao.PlayerDao;
import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.PlayerStats;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller class responsible for querying metric logs through data layer objects
 * and updating the leaderboard interface.
 *
 * @author Team 4
 * @version 1.0
 */
public class LeaderBoardPresenter {
    private final LeaderBoardView view;
    private final PlayerDao playerDao;
    private final MainMenuView mmv;

    /**
     * Instantiates a new Leaderboard presenter controller profile wrapper.
     *
     * @param view         target list panel view display framework component layer anchor
     * @param mainMenuView menu interface state tracker handling navigation
     */
    public LeaderBoardPresenter(LeaderBoardView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mmv = mainMenuView;
        this.playerDao = new PlayerDao(new DbConnection());

        refreshLeaderboard();
        addHandlers();
    }

    /**
     * Fetches up-to-date performance history profiles from database views
     * and pushes records directly into active user list frameworks.
     */
    public void refreshLeaderboard() {
        try {
            List<PlayerStats> stats = playerDao.getDetailedLeaderboard();
            view.setStatsData(stats);
        } catch (SQLException e) {
            view.showError("Database error: " + e.getMessage());
        }
    }

    /**
     * Binds navigation listeners to returning dashboard button targets.
     */
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