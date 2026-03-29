package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.dao.GameDao;
import be.kdg.programming.integrationproject.dao.PlayerDao;
import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.view.LeaderboardView;
import be.kdg.programming.integrationproject.view.MainMenuView;

import java.sql.SQLException;
import java.util.List;

public class LeaderboardPresenter {

    private final LeaderboardView view;
    private final MainMenuView mainMenuView;
    private final DbConnection conn;
    private final PlayerDao playerDao;
    private final GameDao gameDao;

    public LeaderboardPresenter(
            LeaderboardView view,
            MainMenuView mainMenuView,
            DbConnection conn
    ) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        this.conn = conn;
        this.playerDao = new PlayerDao(conn);
        this.gameDao = new GameDao(conn);


        loadStats();
        loadTable();
        addHandlers();
    }

    private void loadStats() {
        try {
            view.setTotalGames("Total Games: " + gameDao.getTotalGamesCount());
            view.setAvgDuration("Avg Duration: " + gameDao.getAverageDuration());
            view.setTopScore("Top Score: " + gameDao.getTopScore());
            view.setActivePlayers("Players: " + playerDao.getActivePlayerCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        try {
            view.getTable().getItems().setAll(playerDao.getLeaderboardData());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addHandlers() {
        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );
    }
}
